package com.bobabrewery.service.impl;

import com.alibaba.fastjson.JSON;
import com.bobabrewery.common.PageParam;
import com.bobabrewery.common.Result;
import com.bobabrewery.common.exceptin.CommonException;
import com.bobabrewery.common.util.Page;
import com.bobabrewery.config.AsyncConfig;
import com.bobabrewery.domain.param.CreateINOOrder;
import com.bobabrewery.domain.param.CreateMoveOrder;
import com.bobabrewery.domain.resp.SignMintResult;
import com.bobabrewery.enums.ReCode;
import com.bobabrewery.enums.Web3Net;
import com.bobabrewery.repo.common.domain.model.MoveRefund;
import com.bobabrewery.repo.common.domain.model.Nft;
import com.bobabrewery.repo.common.mapper.MoveRefundMapper;
import com.bobabrewery.repo.common.mapper.NftMapper;
import com.bobabrewery.repo.common.mapper.SaleTokenIdMapper;
import com.bobabrewery.service.EncodeService;
import com.bobabrewery.service.NFTSerivce;
import com.bobabrewery.util.CredentialsUtils;
import com.bobabrewery.util.IREC20;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthCall;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.utils.Convert;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * @author PailieXiangLong
 */
@Slf4j
@Service
public class NFTServiceImpl implements NFTSerivce {

    @Resource
    private EncodeService encodeService;

    @Resource
    private NftMapper nftMapper;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private CredentialsUtils credentialsUtils;

    @Value("${spring.profiles.active}")
    private String env;

    private final static ContractGasProvider contractGasProvider = new DefaultGasProvider();

    @Override
    public void callbackSR(String walletAddress, Integer[] tokenIds, String txId, Integer status) {
        // 购买后清除缓存
        if (status != 0) {
            String format = String.format(NEW_USER_BUY_CURRENT, walletAddress, tokenIds.length);
            redisTemplate.delete(format);
        }
        for (Integer tokenId : tokenIds) {
            saleTokenIdMapper.callBackTxId(txId, tokenId, walletAddress, status);
        }
    }

    @Resource
    private SaleTokenIdMapper saleTokenIdMapper;


    public static final BigInteger priceU = Convert.toWei("100", Convert.Unit.ETHER).toBigInteger();

    @Override
    public Result<SignMintResult> createSacredRealm(CreateINOOrder order) throws ExecutionException, InterruptedException {
        // 选token
        List<Long> tokenIds = selectTokenId(order);
        Integer amount = order.getAmount();
        String walletAddress = order.getWalletAddress();
        String contractAddress = order.getContractAddress();
        if (tokenIds.size() < amount) {
            throw new CommonException(ReCode.SALE_NOT_ENOUGH);
        }
        // 原价
        BigInteger price = priceU;
        // BSC bre balance
        IREC20 bsc = IREC20.load(BSC_BRE_CONTRACT_ADDRESS, Web3Net.MAIN_BSC.getWeb3j(), credentialsUtils.getCredentials(), contractGasProvider);
        BigInteger bigInteger = bsc.balanceOf(walletAddress).sendAsync().get();
        BigDecimal bre1000 = Convert.toWei("1000", Convert.Unit.ETHER);
        if (bigInteger.compareTo(bre1000.toBigInteger()) >= 0) {
            //价格打折
            price = new BigDecimal(price).multiply(new BigDecimal("0.9")).toBigInteger();
        }
        List<BigInteger> collect = tokenIds.stream().map(BigInteger::valueOf).collect(Collectors.toList());
        String sign = getSign(walletAddress, collect, price, contractAddress);
        SignMintResult signMintResult = new SignMintResult();
        signMintResult.setAmount(amount);
        signMintResult.setSign(sign);
        signMintResult.setActualPrice(price);
        signMintResult.setTokenIds(tokenIds);
        return Result.ok(signMintResult);
    }


    public static final BigInteger priceEternalWorld = Convert.toWei("10", Convert.Unit.ETHER).toBigInteger();
    public static final BigInteger priceEternalWorldOFF = Convert.toWei("9", Convert.Unit.ETHER).toBigInteger();

    @Override
    public Result<SignMintResult> createEternalWorld(CreateINOOrder order) throws ExecutionException, InterruptedException {
        throw new CommonException(ReCode.PROJECT_END_SALE);
        /*String walletAddress = order.getWalletAddress();
        Integer amount = order.getAmount();
        String contractAddress = order.getContractAddress();
        BigInteger price = priceEternalWorld;
        // BSC bre balance
        IREC20 bsc = IREC20.load(BSC_BRE_CONTRACT_ADDRESS, Web3Net.MAIN_BSC.getWeb3j(), credentialsUtils.getCredentials(), contractGasProvider);
        BigInteger bigInteger = bsc.balanceOf(walletAddress).sendAsync().get();
        BigDecimal bre500 = Convert.toWei("500", Convert.Unit.ETHER);
        if (bigInteger.compareTo(bre500.toBigInteger()) >= 0) {
            //价格打折
            price = priceEternalWorldOFF;
        }
        String sign = getSign(walletAddress, price, BigInteger.valueOf(amount), contractAddress);
        SignMintResult signMintResult = new SignMintResult();
        signMintResult.setAmount(amount);
        signMintResult.setSign(sign);
        signMintResult.setActualPrice(price);
        return Result.ok(signMintResult);*/
    }


    public static final String NEW_USER_BUY_CURRENT = "new_user_%s_buy_current_%d";
    public static final String NEW_TOKEN_SALED = "new_token_saled_%d";

    private List<Long> selectTokenId(CreateINOOrder order) {
        String walletAddress = order.getWalletAddress();
        Integer amount = order.getAmount();
        // 有缓存取缓存
        String o = (String) redisTemplate.opsForValue().get(String.format(NEW_USER_BUY_CURRENT, walletAddress, amount));
        if (o != null) {
            return JSON.parseArray(o, Long.class);
        }
        // 全部可购买的tokenId
        List<Long> tokenIdList = saleTokenIdMapper.findByStatusLimit1(amount, 0L);

        // 过滤暂时锁定的tokenId
        List<Long> filter = tokenIdList.stream().filter(aLong -> {
            Object o1 = redisTemplate.opsForValue().get(String.format(NEW_TOKEN_SALED, aLong));
            if (o1 != null && (boolean) o1) {
                return false;
            } else {
                return true;
            }
        }).collect(Collectors.toList());
        List<Long> selected = new ArrayList<>(filter);
        Long max = Collections.max(tokenIdList);
        // 数量不够进行补充
        if (filter.size() < amount) {
            selected.addAll(saleTokenIdMapper.findByStatusLimit1(amount - filter.size(), max));
        }
        // 缓存本次购买数据3分钟
        redisTemplate.opsForValue().set(String.format(NEW_USER_BUY_CURRENT, walletAddress, amount), JSON.toJSONString(selected), Duration.ofMinutes(3));
        // 将tokenId暂时锁定3m
        for (Long aLong : selected) {
            redisTemplate.opsForValue().set(String.format(NEW_TOKEN_SALED, aLong), true, Duration.ofMinutes(3));
        }
        return new ArrayList<>(new HashSet<>(selected));
    }


    @Override
    public Page<Nft> list(PageParam pageParam) {
        Nft nft = new Nft();
        return new Page<>(pageParam,
                nftMapper.count(nft),
                nftMapper.list(pageParam, nft)
        );
    }

    @Override
    public Nft findById(Integer id) {
        Nft byId = nftMapper.findById(id);
        return byId;
    }

    @Resource
    private MoveRefundMapper moveRefundMapper;

    @Override
    public Result<SignMintResult> createMoveRefund(CreateMoveOrder order) throws ExecutionException, InterruptedException {

        String walletAddress = order.getWalletAddress();
        // query csv data tokenIds

        List<MoveRefund> moveRefunds = moveRefundMapper.findByWalletAddress(walletAddress);

        if (moveRefunds.size() > 0) {
            List<Integer> ownerIds = new ArrayList<>();

            for (MoveRefund moveRefund : moveRefunds) {
                String s = moveNFTownerOf(moveRefund.getTokenId().longValue());
                if (s.equalsIgnoreCase(walletAddress)) {
                    ownerIds.add(moveRefund.getTokenId());
                }
            }

            if (ownerIds.size() > 0) {
                List<BigInteger> collect = ownerIds.stream().map(BigInteger::valueOf).collect(Collectors.toList());
                Integer price = moveRefunds.get(0).getPrice();
                BigDecimal originalPrice = Convert.toWei(price.toString(), Convert.Unit.ETHER);
                BigDecimal refund = originalPrice.multiply(new BigDecimal("0.8"));
                String sign = getSign(walletAddress, collect, refund.toBigInteger(), order.getContractAddress());
                SignMintResult result = new SignMintResult();
                result.setSign(sign);
                result.setAmount(ownerIds.size());
                result.setTokenIds(ownerIds.stream().map(Long::valueOf).collect(Collectors.toList()));
                result.setActualPrice(refund.toBigInteger());
                return Result.ok(result);
            } else {
                return Result.fail(ReCode.NOT_NFT_OWNER);
            }

        } else {
            return Result.fail(ReCode.FAILED);
        }


    }

    @Override
    public Result<String> moveCanRefund(String walletAddress) {
        int exist = moveRefundMapper.exist(walletAddress);
        if (exist > 0) {
            return Result.ok();
        } else {
            return Result.fail(ReCode.FAILED);
        }
    }

    private static final String BOBA_BRE_CONTRACT_ADDRESS = "0x3A93bD0fA10050d206370eeA53276542A105C885";
    private static final String BSC_BRE_CONTRACT_ADDRESS = "0x9eBBEB7f6b842377714EAdD987CaA4510205107A";

    @Async(AsyncConfig.POOL_NAME)
    public Future<Boolean> breBalance(String walletAddress) throws Exception {
        IREC20 boba = IREC20.load(BOBA_BRE_CONTRACT_ADDRESS, Web3Net.MAIN_BOBA.getWeb3j(), credentialsUtils.getCredentials(), contractGasProvider);
        IREC20 bsc = IREC20.load(BSC_BRE_CONTRACT_ADDRESS, Web3Net.MAIN_BSC.getWeb3j(), credentialsUtils.getCredentials(), contractGasProvider);
        BigInteger bobaBalance = boba.balanceOf(walletAddress).send();
        BigInteger bscBalance = bsc.balanceOf(walletAddress).send();
        return new AsyncResult<>(bobaBalance.compareTo(BigInteger.ZERO) > 0 || bscBalance.compareTo(BigInteger.ZERO) > 0);
    }

    private String getSign(String userAddress, BigInteger price, BigInteger amount, String contractAddress) {
        String builder = "0x" +
                TypeEncoder.encodePacked(new Address(userAddress)) +
                TypeEncoder.encodePacked(new Uint256(price)) +
                TypeEncoder.encodePacked(new Uint256(amount)) +
                TypeEncoder.encodePacked(new Address(contractAddress));
        return encodeService.sign(builder);
    }


    private String getSign(String userAddress, List<BigInteger> tokenIds, BigInteger price, String contractAddress) {
        DynamicArray<Uint256> uint256DynamicArray = new DynamicArray<>(Uint256.class, tokenIds.stream().map(Uint256::new).collect(Collectors.toList()));
        String builder = "0x" +
                TypeEncoder.encodePacked(new Address(userAddress)) +
                TypeEncoder.encodePacked(uint256DynamicArray) +
                TypeEncoder.encodePacked(new Uint256(price)) +
                TypeEncoder.encodePacked(new Address(contractAddress));
        log.info("builder:{}", builder);
        return encodeService.sign(builder);
    }


    private String moveNFTownerOf(long tokenId) throws ExecutionException, InterruptedException {
        Function function = new Function(
                "ownerOf",
                Arrays.asList(new Uint256(tokenId)),
                Arrays.asList(new TypeReference<Address>() {
                })
        );
        String encode = FunctionEncoder.encode(function);

        Web3j web3j = Web3Net.TEST_RINKEBY.getWeb3j();
        String contractAddress = "0x4CaF447fB47De34ad9957D5E4010D77C6c9c22BE";
        if (env.equals("prod")) {
            web3j = Web3Net.MAIN_BSC.getWeb3j();
            contractAddress = "0xE9c41103649B047fAaAc059b575827A03D8e6A67";
        }
        Transaction ethCallTransaction = Transaction.createEthCallTransaction(
                "0x0000000000000000000000000000000000000000",
                contractAddress,
                encode);
        EthCall send = web3j.ethCall(ethCallTransaction, DefaultBlockParameterName.LATEST).sendAsync().get();
        List<Type> decode = FunctionReturnDecoder.decode(send.getValue(), function.getOutputParameters());
        if (decode.size() > 0) {
            Address type = (Address) decode.get(0);
            return type.getValue();
        } else {
            return "s";
        }

    }

    public static void main(String[] args) throws IOException {
        
    }
}
