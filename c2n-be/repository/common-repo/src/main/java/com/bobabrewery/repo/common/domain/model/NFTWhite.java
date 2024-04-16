package com.bobabrewery.repo.common.domain.model;

import lombok.Data;

import java.io.Serializable;

/**
 * NFT白名单
 *
 * @TableName brewery_nft_white
 */
@Data
public class NFTWhite implements Serializable {
    /**
     * ID
     */
    private Integer id;

    /**
     * 钱包地址
     */
    private String walletAddress;

    /**
     * 限制次数
     */
    private Integer amount;

    /**
     * 使用的优惠码ID
     */
    private Integer usageCodeId;

    /**
     * 0-未购买,1-购买成功
     */
    private Integer status;

    private static final long serialVersionUID = 1L;
}