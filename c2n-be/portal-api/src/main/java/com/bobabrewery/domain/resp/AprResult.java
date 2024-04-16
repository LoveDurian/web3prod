package com.bobabrewery.domain.resp;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author PailieXiangLong
 */
@Data
public class AprResult {
    private BigDecimal priceInLP;
    private BigDecimal apr;
}
