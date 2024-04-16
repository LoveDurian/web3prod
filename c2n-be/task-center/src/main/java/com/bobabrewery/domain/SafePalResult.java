package com.bobabrewery.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author PailieXiangLong
 */
@NoArgsConstructor
@Data
public class SafePalResult {

    /**
     * code
     */
    @JsonProperty("code")
    private Integer code;
    /**
     * msg
     */
    @JsonProperty("msg")
    private String msg;
    /**
     * data
     */
    @JsonProperty("data")
    private DataDTO data;

    /**
     * Item
     */
    @NoArgsConstructor
    @Data
    public static class DataDTO {
        /**
         * orderId
         */
        @JsonProperty("order_id")
        private String orderId;
        /**
         * accountId
         */
        @JsonProperty("account_id")
        private String accountId;
        /**
         * 订单状态1.订单创建2已绑定txid3已入金4提现处理中5提现处理失败6提现处理成功7订单处理失败
         */
        @JsonProperty("status")
        private Integer status;
        /**
         * depositCoin
         */
        @JsonProperty("deposit_coin")
        private String depositCoin;
        /**
         * depositTxid
         */
        @JsonProperty("deposit_txid")
        private String depositTxid;
        /**
         * depositNetwork
         */
        @JsonProperty("deposit_network")
        private String depositNetwork;
        /**
         * depositAddr
         */
        @JsonProperty("deposit_addr")
        private String depositAddr;
        /**
         * 接收token数
         */
        @JsonProperty("deposit_amount")
        private String depositAmount;
        /**
         * spReceiveAddr
         */
        @JsonProperty("sp_receive_addr")
        private String spReceiveAddr;
        /**
         * spReceiveMemo
         */
        @JsonProperty("sp_receive_memo")
        private String spReceiveMemo;
        /**
         * receiveCoin
         */
        @JsonProperty("receive_coin")
        private String receiveCoin;
        /**
         * receiveNetwork
         */
        @JsonProperty("receive_network")
        private String receiveNetwork;
        /**
         * receiveAddr
         */
        @JsonProperty("receive_addr")
        private String receiveAddr;
        /**
         * receiveMemo
         */
        @JsonProperty("receive_memo")
        private String receiveMemo;
        /**
         * 发送token数
         */
        @JsonProperty("receive_amount")
        private String receiveAmount;
        /**
         * receivetTxid
         */
        @JsonProperty("receivet_txid")
        private String receivetTxid;
        /**
         * 手续费
         */
        @JsonProperty("receive_fee")
        private String receiveFee;
        /**
         * txFeeRate
         */
        @JsonProperty("tx_fee_rate")
        private String txFeeRate;
        /**
         * refundAddr
         */
        @JsonProperty("refund_addr")
        private String refundAddr;
        /**
         * refundMemo
         */
        @JsonProperty("refund_memo")
        private String refundMemo;
        /**
         * refundStatus
         */
        @JsonProperty("refund_status")
        private Integer refundStatus;
        /**
         * refundTxid
         */
        @JsonProperty("refund_txid")
        private String refundTxid;
        /**
         * createTime
         */
        @JsonProperty("create_time")
        private Long createTime;
        /**
         * updateTime
         */
        @JsonProperty("update_time")
        private Long updateTime;
    }
}
