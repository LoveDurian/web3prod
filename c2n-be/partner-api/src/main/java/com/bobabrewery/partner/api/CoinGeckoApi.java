package com.bobabrewery.partner.api;

import com.alibaba.fastjson.JSONObject;
import com.bobabrewery.partner.api.fallback.CoinGeckoApiFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author PailieXiangLong
 */
@FeignClient(name = "coingecko", url = "https://api.coingecko.com", path = "/api/v3/", fallback = CoinGeckoApiFallBack.class)
public interface CoinGeckoApi {

    /**
     * 通过CoinGeckoApi查询币种价格
     *
     * @param ids
     * @param vsCurrencies
     * @return
     */
    @GetMapping("/simple/price")
    JSONObject price(@RequestParam(value = "ids", defaultValue = "boba-brewery") String ids,
                     @RequestParam(value = "vs_currencies", defaultValue = "usd") String vsCurrencies);


    /**
     * @return
     */
    @GetMapping("/coins/list")
    String coinList(@RequestParam(value = "include_platform", defaultValue = "true") boolean in);


}
