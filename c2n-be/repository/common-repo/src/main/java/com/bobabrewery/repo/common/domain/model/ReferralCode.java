package com.bobabrewery.repo.common.domain.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 项目推荐码
 *
 * @TableName brewery_referral_code
 */
@Data
public class ReferralCode implements Serializable {
    /**
     * 邀请人用户钱包地址
     */
    private String walletAddress;

    /**
     * 项目ID
     */
    private Integer pid;

    /**
     * 邀请码 md5(wallet_address+"_"+pid)
     */
    private String referralCode;

    /**
     * 参与人钱包地址
     */
    private String participant;

    /**
     * 0-邀请绑定 1-邀请成功
     */
    private Integer status;

    private static final long serialVersionUID = 1L;
}