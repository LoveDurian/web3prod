package com.bobabrewery.domain.param;

import lombok.Data;

/**
 * @author PailieXiangLong
 */
@Data
public class UserRegisterProject {
    /**
     * 用户钱包地址
     */
    private String accountId;
    /**
     * 项目ID
     */
    private Integer productId;
    /**
     * 推荐码
     */
    private String referralCode;
}
