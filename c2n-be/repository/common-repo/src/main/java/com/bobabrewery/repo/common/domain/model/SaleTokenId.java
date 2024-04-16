package com.bobabrewery.repo.common.domain.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @TableName brewery_sale_token_ids
 */
@Data
public class SaleTokenId implements Serializable {
    /**
     * ID
     */
    private Long id;

    /**
     * 
     */
    private Long tokenId;

    private Integer status;

    private String txId;

    private String walletAddress;

    private static final long serialVersionUID = 1L;
}