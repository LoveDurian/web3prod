package com.bobabrewery.repo.common.domain.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @TableName brewery_airdrop
 */
@Data
public class Airdrop implements Serializable {
    /**
     * ID
     */
    private Integer id;

    /**
     * 项目ID
     */
    private Integer pid;

    /**
     * 钱包地址
     */
    private String walletAddress;

    /**
     * 参与项目获得的Airdrop数量 单位Wei
     */
    private Long amount;

    private static final long serialVersionUID = 1L;
}