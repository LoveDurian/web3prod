package com.bobabrewery.service;

public interface SafePalActivityService {
    /**
     * 记录参加SafePal的WHO活动的用户
     *
     * @param accountId
     * @param contactAddress
     */
    void logAccountId(String accountId, String contactAddress, String ip);
}
