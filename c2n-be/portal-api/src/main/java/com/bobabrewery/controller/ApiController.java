package com.bobabrewery.controller;

import com.bobabrewery.common.Result;
import com.bobabrewery.enums.ReCode;
import com.bobabrewery.repo.common.domain.model.UserInfo;
import com.bobabrewery.repo.common.domain.model.WhoStakedLog;
import com.bobabrewery.repo.common.mapper.NFTOrderMapper;
import com.bobabrewery.repo.common.mapper.UserInfoDao;
import com.bobabrewery.repo.common.mapper.WhoStakedLogMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 对外API接口
 *
 * @author orange
 */
@Slf4j
@RestController
@RequestMapping("/api/v1")
public class ApiController {

    @Resource
    private UserInfoDao userInfoDao;

    @Resource
    private WhoStakedLogMapper whoStakedLogMapper;

    @Resource
    private NFTOrderMapper orderMapper;


    /**
     * 获取该钱包地址的注册状态
     *
     * @param walletAddress 钱包地址
     * @return
     */
    @GetMapping("/info/{walletAddress}")
    public Result<Map<String, Object>> queryUserCreateInfo(@PathVariable("walletAddress") String walletAddress) {
        UserInfo userInfo = userInfoDao.findByAccountId(walletAddress);
        Map<String, Object> map = new HashMap<>(2);
        if (userInfo != null) {
            if (userInfo.getLoginEmail() != null && userInfo.getTgId() != null && userInfo.getUserAddress() != null) {
                map.put("status", true);
                map.put("registerTime", userInfo.getCreateTime());
            } else {
                map.put("status", false);
            }
        } else {
            map.put("status", false);
        }
        return Result.ok(map);
    }

    /**
     * 查询钱包是否new_stake成功的信息
     *
     * @param walletAddress 钱包地址
     * @return
     */
    @GetMapping("/who/staked/{walletAddress}")
    public Result<WhoStakedLog> queryNewWalletAddressStaked(@PathVariable("walletAddress") String walletAddress) {
        WhoStakedLog log = whoStakedLogMapper.selectByWalletAddress(walletAddress);
        if (log != null) {
            return Result.ok(log);
        } else {
            return Result.fail(ReCode.DATA_NOT_FOUND);
        }
    }

    /**
     * 查询该钱包地址
     *
     * @param walletAddress
     * @return
     */
    @PostMapping("/cyberpop")
    public Result<Long> queryNftBy(@RequestParam String walletAddress) {
        log.info("[api_request]{}", walletAddress);
        Long amount = orderMapper.findByWalletAddress(walletAddress);
        return Result.ok(amount);
    }
}
