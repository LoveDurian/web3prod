package com.bobabrewery.repo.common.domain.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @TableName brewery_nft_move_order
 */
@Data
public class NftMoveOrder implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * tokenId
     */
    private Long tokenid;

    /**
     * status 0-未使用 1-锁定 2-已经卖出
     */
    private Integer status;

    /**
     * txId
     */
    private String txid;

    private static final long serialVersionUID = 1L;
}