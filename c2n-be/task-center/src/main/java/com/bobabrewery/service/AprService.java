package com.bobabrewery.service;

import java.math.BigDecimal;

public interface AprService {

    /**
     * 1. lp total supply : https://bscscan.com/token/0x2a50b9D094f3aceA0Ddd7E6077dD6dadA51ac36d `totalSupply`
     * <p>
     * 2. total liquidity = (bre price * bre locked) * 2
     * <p>
     * bre price: https://api.pancakeswap.info/api/v2/tokens/0x9eBBEB7f6b842377714EAdD987CaA4510205107A
     * <p>
     * bre locked: https://bscscan.com/token/0x2a50b9D094f3aceA0Ddd7E6077dD6dadA51ac36d `getReserves`
     * <p>
     * lp price = total liquidity / lp total supply
     *
     * @param breweryPrice
     * @return
     * @throws Exception
     */
    BigDecimal priceInLPTokenBSC(BigDecimal breweryPrice) throws Exception;

    BigDecimal priceInLPTokenBoba(BigDecimal breweryPrice) throws Exception;

    BigDecimal getBreweryPrice();

    BigDecimal getBSCTotalDeposits() throws Exception;

    BigDecimal getBobaTotalDeposits() throws Exception;

    //BSC   APR = 3.15*BRE价格/ (质押的LP总量*LPT价格) * 3600*24*365
    //Boba   APR = 0.35*BRE价格/ (质押的LP总量*LPT价格) * 3600*24*365
}
