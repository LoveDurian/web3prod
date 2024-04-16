package com.bobabrewery.partner.api.fallback;

import com.alibaba.fastjson.JSONObject;
import com.bobabrewery.partner.api.CoinGeckoApi;

/**
 * @author PailieXiangLong
 */
public class CoinGeckoApiFallBack implements CoinGeckoApi {
    @Override
    public JSONObject price(String ids, String vsCurrencies) {
        return null;
    }

    @Override
    public String coinList(boolean in) {
        return null;
    }
}