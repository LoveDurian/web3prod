package com.bobabrewery.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @TableName brewery_bridge_order
 */
@Data
public class BridgeOrder implements Serializable {
    /**
     * 订单ID
     */
    private String orderId;

    /**
     * 用户钱包地址
     */
    private String accountId;

    /**
     * 订单状态 1.订单创建  2 已绑定txid   3 已入金  4 提现处理中  5 提现处理失败  6 提现处理成功   7 订单处理失败
     */
    private Integer status;

    /**
     *
     */
    private String depositCoin;

    /**
     *
     */
    private String depositTxid;

    /**
     *
     */
    private Integer depositNetwork;

    /**
     *
     */
    private String depositAddr;

    /**
     * 接收token数
     */
    private String depositAmount;

    /**
     *
     */
    private String spReceiveAddr;

    /**
     *
     */
    private String spReceiveMemo;

    /**
     *
     */
    private String receiveCoin;

    /**
     *
     */
    private Integer receiveNetwork;

    /**
     *
     */
    private String receiveAddr;

    /**
     *
     */
    private String receiveMemo;

    /**
     * 发送token数
     */
    private String receiveAmount;

    /**
     *
     */
    private String receivetTxid;

    /**
     * 手续费
     */
    private String receiveFee;

    /**
     *
     */
    private String txFeeRate;

    /**
     *
     */
    private String refundAddr;

    /**
     *
     */
    private String refundMemo;

    /**
     *
     */
    private String refundStatus;

    /**
     *
     */
    private Integer refundTxid;

    /**
     *
     */
    private Long createTime;

    /**
     *
     */
    private Long updateTime;

    private static final long serialVersionUID = 1L;
}