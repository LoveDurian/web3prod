package com.bobabrewery.service;

import com.bobabrewery.common.Result;

public interface StakeService {

    /**
     * stake
     *
     * @return
     */
    Result<Boolean> stake(String walletAddress, String contractAddress, String amount, Integer type, Integer chainId);

    /**
     * withdraw
     *
     * @return
     */
    Result<Boolean> withdraw(String walletAddress, String contractAddress, String amount, Integer type, Integer chainId);

    /**
     * 检查是否skate过
     *
     * @param walletAddress
     * @return
     */
    boolean checkStake(String walletAddress) throws Exception;
}
