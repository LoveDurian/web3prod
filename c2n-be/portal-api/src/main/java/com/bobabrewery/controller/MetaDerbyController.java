package com.bobabrewery.controller;


import com.bobabrewery.common.Result;
import com.bobabrewery.common.domain.WalletAddress;
import com.bobabrewery.common.exceptin.CommonException;
import com.bobabrewery.constant.Constant;
import com.bobabrewery.domain.MetaDerbyUserInfoParam;
import com.bobabrewery.domain.param.CreateClaim;
import com.bobabrewery.domain.param.TicketParam;
import com.bobabrewery.domain.resp.ClaimResp;
import com.bobabrewery.enums.ReCode;
import com.bobabrewery.partner.api.MetaDerbyApi;
import com.bobabrewery.partner.resp.MetaDerbyHorsesInfoResp;
import com.bobabrewery.partner.resp.MetaDerbyRaceResp;
import com.bobabrewery.partner.resp.MetaDerbyUserInfoResp;
import com.bobabrewery.repo.metaderby.mapper.MetaDerbyUserMapper;
import com.bobabrewery.service.EncodeService;
import com.bobabrewery.service.MetaDerbyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.web3j.abi.TypeEncoder;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.generated.Uint256;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * 赛马相关接口
 *
 * @author orange
 */
@Slf4j
@RestController
@RequestMapping("/derby")
public class MetaDerbyController {

    @Resource
    private MetaDerbyApi metaderbyApi;

    @Resource
    private MetaDerbyService metaDerbyServiceImpl;

    @Resource
    private MetaDerbyUserMapper metaDerbyUserMapper;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;


    /**
     * metaderby封装race接口
     *
     * @param raceId
     * @return
     */
    @GetMapping("/race")
    Result<MetaDerbyRaceResp.DataDTO> metaderbyRate(@RequestParam Integer raceId) {
        MetaDerbyRaceResp race = metaderbyApi.race(raceId);
        MetaDerbyRaceResp.DataDTO data = race.getData();
        return Result.ok(data);
    }

    /**
     * 检查是否是10U白名单
     *
     * @return
     */
    @PostMapping("/white")
    Result<String> white(@Validated WalletAddress walletAddress) {
        boolean b = metaDerbyServiceImpl.existWhite(walletAddress.getWalletAddress());
        if (b) {
            return Result.ok();
        }
        return Result.fail(ReCode.NOT_WHITE_LIST);
    }

    /**
     * 取使用10U总额 换算成Bre 给到前端
     *
     * @return
     */
    @GetMapping("/count")
    Result<String> count10U() throws ExecutionException, InterruptedException {
        BigDecimal bigInteger = metaDerbyServiceImpl.countAll10U().get();
        return Result.ok(bigInteger.toPlainString());
    }


    /**
     * 确认(更新)使用10U
     *
     * @param param
     * @return
     */
    @PostMapping("/confirm_ticket")
    Result<String> confirm(@Validated TicketParam param) {
        boolean b = metaDerbyServiceImpl.confirmTicket(param);
        if (b) {
            return Result.ok();
        } else {
            return Result.fail(ReCode.DATA_NOT_FOUND);
        }
    }

    /**
     * 用户当前使用的10U马匹ID接口
     *
     * @param param
     * @return
     */
    @GetMapping("/{walletAddress}/info")
    Result<MetaDerbyUserInfoResp> userInfo(@Validated MetaDerbyUserInfoParam param) {
        MetaDerbyUserInfoResp metaDerbyUserInfoResp = metaDerbyServiceImpl.userInfo(param);
        return Result.ok(metaDerbyUserInfoResp);
    }

    /**
     * 全部马的赔率信息
     *
     * @return
     */
    @GetMapping("/horses/rates")
    Result<List<MetaDerbyHorsesInfoResp>> rates() {
        List<MetaDerbyHorsesInfoResp> metaDerbyHorsesInfoResps = metaDerbyServiceImpl.allHorsesInfo();
        return Result.ok(metaDerbyHorsesInfoResps);
    }

    /**
     * claim 签名
     *
     * @param claim
     * @return
     */
    @PostMapping("/claim")
    Result<ClaimResp> clam(@Validated CreateClaim claim) {
        Boolean aBoolean = redisTemplate.hasKey(Constant.METADERBY_SIGN_DISABLE);
        if (Boolean.TRUE.equals(aBoolean)) {
            return Result.fail(ReCode.CLAM_END);
        }
        BigInteger estimate = metaDerbyUserMapper.findByWalletAddress(claim.getWalletAddress());
        if (estimate != null) {
            String sign = getSign(claim.getWalletAddress(), estimate, claim.getContractAddress());
            ClaimResp resp = new ClaimResp();
            resp.setAmount(estimate);
            resp.setSign(sign);
            return Result.ok(resp);
        } else {
            throw new CommonException(ReCode.DATA_NOT_FOUND);
        }
    }

    @Resource
    private EncodeService encodeServiceImpl;

    private String getSign(String userAddress, BigInteger amount, String contractAddress) {
        String builder = "0x" +
                TypeEncoder.encodePacked(new Address(userAddress)) +
                TypeEncoder.encodePacked(new Uint256(amount)) +
                TypeEncoder.encodePacked(new Address(contractAddress));
        log.info("builder:{}", builder);
        return encodeServiceImpl.sign(builder);
    }

}
