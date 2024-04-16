package com.bobabrewery.job;

import com.alibaba.fastjson.JSON;
import com.bobabrewery.common.constant.EnvConstant;
import com.bobabrewery.enums.Web3Net;
import com.bobabrewery.partner.api.MetaDerbyApi;
import com.bobabrewery.partner.constant.MetaDerbyConstant;
import com.bobabrewery.partner.resp.MetaDerbyHorsesInfoResp;
import com.bobabrewery.partner.resp.MetaDerbyRoomResp;
import com.bobabrewery.repo.metaderby.mapper.MetaDerbyWhiteMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthCall;
import org.web3j.utils.Convert;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * @author PailieXiangLong
 */
@Slf4j
@Component
public class MetaDerbyJobs {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Value(EnvConstant.ENV_VALUE)
    private String env;

    @Resource
    private MetaDerbyApi metaderbyApi;

    public static String contract;

    @Resource
    private MetaDerbyWhiteMapper metaDerbyWhiteMapper;

    public static final BigInteger A10U_BRE_WEI = Convert.toWei("5000", Convert.Unit.ETHER).toBigInteger();

    public static final String CURRENT_ROOM_ID = "meta-derby-current-room-id";
    /**
     * 算出所有马匹的赔率
     */
    @Scheduled(cron = "0 0/3 * * * ?")
    public void allHorses() throws ExecutionException, InterruptedException {
        contract = "0x10B01B14D2Ce953F52F35a2e46e8AeC8D88A7355";
        if (EnvConstant.PROD.equals(env)) {
            contract = "0x74392DDaB778C96365688c2E9251e30368338522";
        }
        Integer roomId = (Integer) redisTemplate.opsForValue().get(CURRENT_ROOM_ID);
        if (roomId == null) {
            return;
        }
        log.info("pull room:{} env:{} contract: {}", roomId, env, contract);
        MetaDerbyRoomResp room = metaderbyApi.room(roomId);
        List<MetaDerbyRoomResp.DataDTO.RoomHorsesDTO> roomHorses = room.getData().getRoomHorses();
        // 总质押量
        BigInteger allHorsesStake = totalStakes();
        redisTemplate.opsForValue().set(MetaDerbyConstant.TOTAL_STAKE_ALL_HORSES, allHorsesStake);
        int count = metaDerbyWhiteMapper.count();
        allHorsesStake = allHorsesStake.add(A10U_BRE_WEI.multiply(BigInteger.valueOf(count)));
        List<MetaDerbyHorsesInfoResp> resultList = new ArrayList<>();
        for (MetaDerbyRoomResp.DataDTO.RoomHorsesDTO roomHors : roomHorses) {
            Integer horseId = roomHors.getHorseId();
            BigInteger currentHorsesStake = totalStakeById(horseId);
            int i = metaDerbyWhiteMapper.countByHorsesId(horseId);
            // 某个马的总质押量
            redisTemplate.opsForValue().set(String.format(MetaDerbyConstant.TOTAL_STAKE_ONE_HORSES, horseId), currentHorsesStake);
            MetaDerbyHorsesInfoResp result = new MetaDerbyHorsesInfoResp();
            // 当前马匹赔率 = 所有马匹总金额 / 当前马匹总金额
            if (allHorsesStake.compareTo(BigInteger.ZERO) > 0) {
                if (currentHorsesStake.compareTo(BigInteger.ZERO) == 0) {
                    result.setOdds(BigDecimal.valueOf(0));
                } else {
                    currentHorsesStake = currentHorsesStake.add(A10U_BRE_WEI.multiply(BigInteger.valueOf(i)));
                    BigDecimal divide = new BigDecimal(allHorsesStake).divide(new BigDecimal(currentHorsesStake), 3, RoundingMode.HALF_UP).multiply(new BigDecimal(0.8));
                    result.setOdds(divide);
                }
            } else {
                result.setOdds(BigDecimal.ZERO);
            }
            result.setId(horseId);
            resultList.add(result);
        }
        String s = JSON.toJSONString(resultList);
        redisTemplate.opsForValue().set(MetaDerbyConstant.ALL_HORSES_RATE, s);
        log.info("calc horses rate: {}", s);
    }


    /**
     * 全部马的总金额
     *
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public BigInteger totalStakes() throws ExecutionException, InterruptedException {
        Function function = new Function(
                "totalStakes",
                Collections.emptyList(),
                Arrays.asList(new TypeReference<Uint256>() {
                })
        );

        String encode = FunctionEncoder.encode(function);
        Web3j load = load();

        Transaction ethCallTransaction = Transaction.createEthCallTransaction(
                "0x0000000000000000000000000000000000000000", contract, encode);
        EthCall send = load.ethCall(ethCallTransaction, DefaultBlockParameterName.LATEST).sendAsync().get();
        List<Type> decode = FunctionReturnDecoder.decode(send.getValue(), function.getOutputParameters());
        if (decode.size() > 0) {
            Uint256 uint256 = (Uint256) decode.get(0);
            return uint256.getValue();
        }
        return BigInteger.ZERO;
    }


    /**
     * 每匹马的质押总金额
     *
     * @param horsesId
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public BigInteger totalStakeById(Integer horsesId) throws ExecutionException, InterruptedException {
        Function function = new Function(
                "totalStakeById",
                Arrays.asList(new Uint256(horsesId)),
                Arrays.asList(new TypeReference<Uint256>() {
                })
        );

        String encode = FunctionEncoder.encode(function);
        Web3j load = load();

        Transaction ethCallTransaction = Transaction.createEthCallTransaction(
                "0x0000000000000000000000000000000000000000",
                contract,
                encode);
        EthCall send = load.ethCall(ethCallTransaction, DefaultBlockParameterName.LATEST).sendAsync().get();
        List<Type> decode = FunctionReturnDecoder.decode(send.getValue(), function.getOutputParameters());
        if (decode.size() > 0) {
            Uint256 uint256 = (Uint256) decode.get(0);
            return uint256.getValue();
        }
        return BigInteger.ZERO;

    }

    public BigInteger totalStakeByIdStr(String horsesId) {
        return BigInteger.ZERO;
    }

    public Web3j load() {
        Web3j web3j = Web3Net.TEST_RINKEBY.getWeb3j();
        if (EnvConstant.PROD.equals(env)) {
            web3j = Web3Net.MAIN_BSC.getWeb3j();
        }
        return web3j;
    }


    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Function function = new Function(
            "totalStakes",
            Collections.emptyList(),
            Arrays.asList(new TypeReference<Uint256>() {
            })
        );

        String encode = FunctionEncoder.encode(function);
        Web3j load = Web3Net.TEST_RINKEBY.getWeb3j();

        Transaction ethCallTransaction = Transaction.createEthCallTransaction(
            "0x0000000000000000000000000000000000000000", "0x10B01B14D2Ce953F52F35a2e46e8AeC8D88A7355", encode);
        EthCall send = load.ethCall(ethCallTransaction, DefaultBlockParameterName.LATEST).sendAsync().get();
        List<Type> decode = FunctionReturnDecoder.decode(send.getValue(), function.getOutputParameters());
        if (decode.size() > 0) {
            Uint256 uint256 = (Uint256) decode.get(0);
            System.out.println(uint256.getValue());
         }
    }

}
