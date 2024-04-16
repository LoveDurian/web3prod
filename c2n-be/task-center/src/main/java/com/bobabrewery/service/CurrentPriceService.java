package com.bobabrewery.service;

import java.math.BigDecimal;

/**
 * @author PailieXiangLong
 */
public interface CurrentPriceService {


    BigDecimal getPrice(String tokenName) throws Exception;

}
