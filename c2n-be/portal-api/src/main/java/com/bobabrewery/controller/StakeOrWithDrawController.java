package com.bobabrewery.controller;

import com.bobabrewery.common.Result;
import com.bobabrewery.service.StakeService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.Digits;

/**
 * Stake & WithDraw
 *
 * @author orange
 */
@RestController
@RequestMapping("/save")
public class StakeOrWithDrawController {


    @Resource
    private StakeService stakeServiceImpl;

    /**
     * 记录用户Stake相关信息
     *
     * @param walletAddress 钱包地址
     * @param amount        数量
     * @param type          0 -bre 1 -eth
     * @param chainId       chainId
     * @return
     */
    @PostMapping("/staking")
    public Result<Boolean> saveStaked(@RequestParam String walletAddress,
                                      @RequestParam String contractAddress,
                                      @RequestParam @Digits(message = "must number", integer = 15, fraction = 2) String amount,
                                      @RequestParam Integer type,
                                      @RequestParam Integer chainId) {
        return stakeServiceImpl.stake(walletAddress, contractAddress, amount, type, chainId);
    }

    /**
     * 记录用户withdraw操作的相关信息
     *
     * @param walletAddress 钱包地址
     * @param amount        数量
     * @param type          0 -bre 1 -eth
     * @param chainId       chainId
     * @return
     * @apiNote 当未staking过的用户withdraw时禁止withdraw
     */
    @PostMapping("/withdraw")
    public Result<Boolean> saveWithDraw(@RequestParam String walletAddress,
                                        @RequestParam String contractAddress,
                                        @RequestParam @Digits(message = "must number", integer = 15, fraction = 2) String amount,
                                        @RequestParam Integer type, @RequestParam Integer chainId) {
        return stakeServiceImpl.withdraw(walletAddress, contractAddress, amount, type, chainId);
    }
}
