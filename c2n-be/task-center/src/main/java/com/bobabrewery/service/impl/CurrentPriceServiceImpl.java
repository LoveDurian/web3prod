package com.bobabrewery.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.bobabrewery.enums.CoinGeckoEnum;
import com.bobabrewery.service.CurrentPriceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;

/**
 * @author PailieXiangLong
 */
@Service
@Slf4j
public class CurrentPriceServiceImpl implements CurrentPriceService {


    public static final WebClient WEB_CLIENT = WebClient.builder().baseUrl("https://api.coingecko.com/").build();

    @Override
    public BigDecimal getPrice(String tokenName) {
        CoinGeckoEnum coinGeckoEnum = CoinGeckoEnum.get(tokenName);
        if (coinGeckoEnum != null) {
            JSONObject block = WEB_CLIENT.get().uri(uriBuilder -> uriBuilder.path("/api/v3/simple/price")
                            .queryParam("vs_currencies", "usd")
                            .queryParam("ids", coinGeckoEnum.getApiId())
                            .build())
                    .retrieve()
                    .bodyToMono(JSONObject.class).block();
            if (block != null) {
                JSONObject jsonObject = block.getJSONObject(coinGeckoEnum.getApiId());
                return jsonObject.getBigDecimal("usd");
            }
        }
        return BigDecimal.ZERO;
    }
}
