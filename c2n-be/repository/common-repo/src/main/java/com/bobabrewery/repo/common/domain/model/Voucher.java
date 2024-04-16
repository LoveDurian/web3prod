package com.bobabrewery.repo.common.domain.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @TableName brewery_voucher
 */
@Data
public class Voucher implements Serializable {
    /**
     * ID
     */
    private Integer id;

    /**
     * Voucher名单地址
     */
    private String walletAddress;

    /**
     * 使用状态 0-未使用 1-已经使用
     */
    private Integer status;

    private static final long serialVersionUID = 1L;
}