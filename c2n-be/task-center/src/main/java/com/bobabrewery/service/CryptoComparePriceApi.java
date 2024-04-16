package com.bobabrewery.service;

import com.bobabrewery.domain.CryptoPrice;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * min-api.cryptocompare.com
 *
 * @author PailieXiangLong
 */
@FeignClient(name = "cryptocompare", url = "https://min-api.cryptocompare.com")
public interface CryptoComparePriceApi {
    /**
     * 获取加密货币的美元价格
     *
     * @param symbol
     * @return
     */
    @GetMapping("/data/price?tsyms=USD")
    CryptoPrice price(@RequestParam("fsym") String symbol);
}

