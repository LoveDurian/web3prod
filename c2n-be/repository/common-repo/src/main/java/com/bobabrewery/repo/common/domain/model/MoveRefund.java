package com.bobabrewery.repo.common.domain.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @TableName brewery_move_refund
 */
@Data
public class MoveRefund implements Serializable {
    /**
     * ID
     */
    private Integer id;

    /**
     * walletAddress
     */
    private String walletAddress;

    /**
     * tokenId
     */
    private Integer tokenId;

    /**
     * price
     */
    private Integer price;

    private static final long serialVersionUID = 1L;
}