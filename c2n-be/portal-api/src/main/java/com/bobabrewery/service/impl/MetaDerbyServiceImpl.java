package com.bobabrewery.service.impl;

import com.alibaba.fastjson.JSON;
import com.bobabrewery.domain.MetaDerbyUserInfoParam;
import com.bobabrewery.domain.param.TicketParam;
import com.bobabrewery.partner.constant.MetaDerbyConstant;
import com.bobabrewery.partner.resp.MetaDerbyHorsesInfoResp;
import com.bobabrewery.partner.resp.MetaDerbyUserInfoResp;
import com.bobabrewery.repo.metaderby.mapper.MetaDerbyWhiteMapper;
import com.bobabrewery.service.MetaDerbyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.web3j.utils.Convert;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.List;
import java.util.concurrent.Future;

/**
 * @author PailieXiangLong
 */
@Slf4j
@Service
public class MetaDerbyServiceImpl implements MetaDerbyService {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private MetaDerbyWhiteMapper metaDerbyWhiteMapper;


    @Override
    public boolean existWhite(String walletAddress) {
        Integer byWalletAddress = metaDerbyWhiteMapper.findByWalletAddress(walletAddress);
        return byWalletAddress != null;
    }

    @Override
    public Future<BigDecimal> countAll10U() {
        int count = metaDerbyWhiteMapper.count();
        BigDecimal divide = BigDecimal.TEN.multiply(new BigDecimal(count)).divide(new BigDecimal("0.002"), 0, RoundingMode.HALF_UP);
        BigDecimal bigDecimal = Convert.toWei(divide, Convert.Unit.ETHER);
        return new AsyncResult<>(bigDecimal);
    }

    @Override
    public boolean confirmTicket(TicketParam ticketParam) {
        int update = metaDerbyWhiteMapper.update(ticketParam.getWalletAddress(), ticketParam.getHorsesId());
        return update > 0;
    }

    @Override
    public Integer currentTicket(String walletAddress) {
        return metaDerbyWhiteMapper.findByWalletAddress(walletAddress);
    }

    @Override
    public List<MetaDerbyHorsesInfoResp> allHorsesInfo() {
        redisTemplate.convertAndSend("metderby_topic", "message");
        String allHorsesInfo1 = (String) redisTemplate.opsForValue().get(MetaDerbyConstant.ALL_HORSES_RATE);
        return JSON.parseArray(allHorsesInfo1, MetaDerbyHorsesInfoResp.class);
    }

    public static final BigDecimal _10UbreWei = Convert.toWei(new BigDecimal(5000), Convert.Unit.ETHER);

    @Override
    public MetaDerbyUserInfoResp userInfo(MetaDerbyUserInfoParam param) {
        BigInteger staked = param.getStaked();
        Integer horsesId = param.getHorsesId();
        String walletAddress = param.getWalletAddress();

        Integer userHorsesId = metaDerbyWhiteMapper.findByWalletAddress(walletAddress);

        log.info("walletAddress: {}, userHorsesId:{}", walletAddress, userHorsesId);

        MetaDerbyUserInfoResp result = new MetaDerbyUserInfoResp();

        MetaDerbyUserInfoResp.HorsesInfo horsesInfo = new MetaDerbyUserInfoResp.HorsesInfo();
        horsesInfo.setId(horsesId);


        // 全部马总质押数量
        BigInteger totalStakeAll = (BigInteger) redisTemplate.opsForValue().get(MetaDerbyConstant.TOTAL_STAKE_ALL_HORSES);
        log.info("全部马总质押数量:{}", Convert.fromWei(new BigDecimal(totalStakeAll), Convert.Unit.ETHER));

        // 使用10U的人数
        int count = metaDerbyWhiteMapper.count();

        BigDecimal totalBre10U = _10UbreWei.multiply(new BigDecimal(count));

        log.info("全部使用10U的BRE:{}", Convert.fromWei(totalBre10U, Convert.Unit.ETHER));
        //  全部使用10U的BRE + 全部马总质押数量
        BigDecimal all = totalBre10U.add(new BigDecimal(totalStakeAll));
        log.info("全部使用10U的BRE + 全部马总质押数量:{}", Convert.fromWei(all, Convert.Unit.ETHER));

        // * 80%
        BigDecimal totalAll = all.multiply(new BigDecimal("0.8"));
        log.info(" * 80%{}", Convert.fromWei(totalAll, Convert.Unit.ETHER));

        // 某个马的总质押
        BigInteger someOne = (BigInteger) redisTemplate.opsForValue().get(String.format(MetaDerbyConstant.TOTAL_STAKE_ONE_HORSES, horsesId));

        log.info("horsesId:{}.马的总质押:{}", Convert.fromWei(new BigDecimal(someOne != null ? someOne : BigInteger.ZERO), Convert.Unit.ETHER));

        // 某个马的10U
        int i = metaDerbyWhiteMapper.countByHorsesId(horsesId);
        BigDecimal horsesBre = _10UbreWei.multiply(new BigDecimal(i));

        log.info("horsesId:{}.马的10U:{}",i,Convert.fromWei(horsesBre,Convert.Unit.ETHER));

        // 是白名单+10U
        if (userHorsesId!=null && userHorsesId.equals(horsesId)) {
            staked = staked.add(_10UbreWei.toBigInteger());
        }

        BigDecimal add = horsesBre.add(new BigDecimal(someOne != null ? someOne : BigInteger.ZERO));

        if (add.compareTo(BigDecimal.ZERO) <= 0) {
            add = BigDecimal.ONE;
        }

        // 用户当前马匹质押量(带10U) / 当前马的总质押(带10U) * 全部马匹的总额(带全部10U) * 80% = 预估赢得的额度
        BigDecimal bigDecimal = Convert.fromWei(String.valueOf(staked), Convert.Unit.ETHER);
        log.info("staked:{},add:{},totalAll:{}", bigDecimal, add, totalAll);
        BigDecimal estimate = new BigDecimal(staked).divide(add, 2, RoundingMode.HALF_UP).multiply(totalAll);

        horsesInfo.setEstimate(estimate.toPlainString());
        horsesInfo.setPledge(staked.toString());

        result.setHorsesInfo(horsesInfo);
        result.setCurrentTicket(userHorsesId);
        return result;
    }


}
