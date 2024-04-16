package com.bobabrewery.domain.resp;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author PailieXiangLong
 */
@NoArgsConstructor
@Data
public class CreateBridgeOrderResult {

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
     * DataDTO
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
         * status
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
         * depositAmount
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
         * receiveAmount
         */
        @JsonProperty("receive_amount")
        private String receiveAmount;
        /**
         * receiveFee
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
         * createTime
         */
        @JsonProperty("create_time")
        private Integer createTime;
        /**
         * updateTime
         */
        @JsonProperty("update_time")
        private Integer updateTime;
    }
}
