package com.bobabrewery.controller;

import com.bobabrewery.common.Result;
import com.bobabrewery.service.AirDropService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * AirDrop
 *
 * @author PailieXiangLong
 */
@RestController
@RequestMapping("/airdrop")
public class AirDropController {

    @Resource
    private AirDropService airDropService;

    /**
     * AirDrop签名
     *
     * @param walletAddress   钱包地址
     * @param projectId       项目ID
     * @param contractAddress 合约地址
     * @return
     */
    @PostMapping("/sign")
    Result<String> airDrop(@RequestParam String walletAddress, @RequestParam Integer projectId, @RequestParam String contractAddress) {
        return airDropService.airDrop(walletAddress, projectId, contractAddress);
    }

    /**
     * 用户AirDrop得到的金额
     *
     * @param walletAddress 钱包地址
     * @param projectId     项目ID
     * @return
     */
    @PostMapping("/amount")
    Result<Long> userAriDropAmount(@RequestParam String walletAddress, @RequestBody @RequestParam Integer projectId) {
        return airDropService.userAriDropAmount(walletAddress, projectId);
    }
}
