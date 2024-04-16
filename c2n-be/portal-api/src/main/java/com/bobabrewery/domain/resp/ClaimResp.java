package com.bobabrewery.domain.resp;

import lombok.Data;

import java.math.BigInteger;

/**
 * @author PailieXiangLong
 */
@Data
public class ClaimResp {

    /**
     * 签名
     */
    private String sign;

    /**
     * 金额
     */
    private BigInteger amount;
}
