package com.bobabrewery.domain.resp;

import lombok.Data;

import java.math.BigInteger;
import java.util.List;

/**
 * @author PailieXiangLong
 */
@Data
public class SignMintResult {

    /**
     * 订单ID
     */
    private String orderId;

    /**
     * 购买数量
     */
    private Integer amount;

    /**
     * 实际价格
     */
    private BigInteger actualPrice;
    /**
     * 签名
     */
    private String sign;

    /**
     * tokenIds
     */
    private List<Long> tokenIds;
}
