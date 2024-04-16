package com.bobabrewery.domain.resp;

import lombok.Data;

import java.util.List;

/**
 * @author PailieXiangLong
 */
@Data
public class SignMoveResult {

    /**
     * 订单ID
     */
    private String orderId;

    /**
     * 购买数量
     */
    private Integer amount;

    /**
     * tokenIds
     */
    private List<Long> tokenIds;
    /**
     * 签名
     */
    private String sign;
}
