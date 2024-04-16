package com.bobabrewery.service;

/**
 * @author PailieXiangLong
 */
public interface ReferralCodeService {

    /**
     * 生成推荐Code
     * 若该钱包地址不存在referral_code表中 新增并返回code
     * 若存在直接返回code
     *
     * @param walletAddress 邀请人钱包地址
     * @param pid           项目id
     * @return 邀请Code
     */
    String generateReferralCode(String walletAddress, Integer pid);

    /**
     * 绑定推荐
     *
     * @param referralCode 推荐码
     * @param participant  被邀请人的钱包地址
     * @return
     */
    Boolean bindReferralCode(String referralCode, String participant);

    /**
     * 推荐成功
     *
     * @param referralCode 推荐码
     * @param participant  被邀请人的钱包地址
     * @return
     */
    Boolean referralSuccess(String referralCode, String participant);


    /**
     * 统计推荐码推荐成功的人数
     *
     * @param referralCode
     * @return
     */
    Long countReferralSuccess(String referralCode);

}
