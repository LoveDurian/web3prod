package com.bobabrewery.repo.user.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 用户信息表
 * @TableName brewery_user
 */
@Data
public class User implements Serializable {
    /**
     * 用户ID
     */
    private Long id;

    /**
     * 用户的钱包地址
     */
    private String walletAddress;

    /**
     * 邮箱地址
     */
    private String email;

    /**
     * 登录时签名的UUID
     */
    private String code;

    /**
     * 注册IP
     */
    private String registerIp;

    /**
     * 链接钱包地址时的IP
     */
    private String loginIp;

    /**
     * telegram名称
     */
    private String telegramName;

    /**
     * telegramID
     */
    private Long telegramId;

    /**
     * 用户所在地区
     */
    private String region;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    private static final long serialVersionUID = 1L;
}