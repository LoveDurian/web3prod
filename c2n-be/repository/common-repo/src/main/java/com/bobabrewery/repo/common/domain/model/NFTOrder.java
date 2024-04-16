package com.bobabrewery.repo.common.domain.model;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Medieval订单信息
 *
 * @TableName brewery_medieval_order
 */
@Data
public class NFTOrder implements Serializable {
    /**
     * orderID
     */
    private String orderId;

    /**
     * 项目ID
     */
    private Integer pid;

    /**
     * 钱包地址
     */
    private String walletAddress;

    /**
     * 使用的优惠码ID
     */
    private Integer usageCodeId;

    /**
     * 购买数量
     */
    private Integer amount;

    /**
     * 实际成交价格
     */
    private Long actualPrice;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 0-未支付,1-支付成功
     */
    private Integer status;

    private static final long serialVersionUID = 1L;
}