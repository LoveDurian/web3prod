package com.bobabrewery.domain.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @author PailieXiangLong
 */
@NoArgsConstructor
@Data
public class CreateBridgeOrder {


    /**
     * 用户id
     */
    @JsonProperty("account_id")
    private String accountId;
    /**
     * depositCoin
     */
    @JsonProperty("deposit_coin")
    @NotBlank
    private String depositCoin;
    /**
     * receiveCoin
     */
    @JsonProperty("receive_coin")
    @NotBlank
    private String receiveCoin;
    /**
     * 存入数量
     */
    @JsonProperty("deposit_amount")
    @NotBlank
    private String depositAmount;
    /**
     * 接收代币地址
     */
    @JsonProperty("receive_addr")
    private String receiveAddr;
    /**
     * receiveMemo
     */
    @JsonProperty("receive_memo")
    private String receiveMemo = "";
    /**
     * 退款地址，暂时只支持同receive_addr
     */
    @JsonProperty("refund_addr")
    private String refundAddr;
    /**
     * refundMemo
     */
    @JsonProperty("refund_memo")
    private String refundMemo = "";
    /**
     * 订单ID，唯一
     */
    @JsonProperty("client_order")
    private String clientOrder;
}
