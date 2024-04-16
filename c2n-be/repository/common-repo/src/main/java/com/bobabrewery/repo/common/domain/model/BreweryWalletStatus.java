package com.bobabrewery.repo.common.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @TableName brewery_wallet_status
 */
@Data
public class BreweryWalletStatus implements Serializable {
    /**
     *
     */
    @JsonIgnore
    private Long id;

    /**
     * 类型 0 bre 1 eth
     */
    @JsonIgnore
    private Integer type;

    /**
     * 创建时间
     */
    @JsonIgnore
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 用户钱包地址
     */
    private String walletAddress;

    /**
     * statking时累加withdraw时累减
     */
    private BigDecimal amount;

    /**
     * 钱包地址是否已经withdraw过
     */
    @JsonIgnore
    private Integer withdrawn;

    /**
     * 该钱包地址是否已经staking过 0 否 1 是
     */
    private Integer staked;

    /**
     * 链ID
     */
    private Integer chainId;

    /**
     * 合约地址
     */
    private String contractAddress;

    private static final long serialVersionUID = 1L;
}