package com.bobabrewery.service;

import com.bobabrewery.domain.PancakeSwapPrice;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author PailieXiangLong
 */
@FeignClient(name = "pancakeswap", url = "https://api.pancakeswap.info")
public interface PancakeSwapPriceApi {

    /***
     * brewery 的价格
     * @return
     */
    @GetMapping("/api/v2/tokens/0x9eBBEB7f6b842377714EAdD987CaA4510205107A")
    PancakeSwapPrice breweryPrice();
}

