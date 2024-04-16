package com.bobabrewery.service;

import com.bobabrewery.common.Result;

/**
 * @author PailieXiangLong
 */
public interface AirDropService {
    /**
     * AirDrop的签名
     *
     * @param walletAddress
     * @param projectId
     * @param contractAddress
     * @return
     */
    Result<String> airDrop(String walletAddress, Integer projectId, String contractAddress);

    /**
     * 用户获得的Amount
     *
     * @param walletAddress
     * @param projectId
     * @return
     */
    Result<Long> userAriDropAmount(String walletAddress, Integer projectId);


}
