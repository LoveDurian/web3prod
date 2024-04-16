package com.bobabrewery.partner;

import com.alibaba.fastjson.JSONObject;
import com.bobabrewery.partner.api.CoinGeckoApi;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @author PailieXiangLong
 */
@Slf4j
@SpringBootTest
public class CoinGeckoApiTest {

    @Resource
    private CoinGeckoApi coinGeckoApi;


    @BeforeEach
    public void proxy() {
        System.setProperty("proxyHost", "127.0.0.1");
        System.setProperty("proxyPort", "8889");
    }


    @Test
    public void CoinGeckoApiTest1() {
        JSONObject usd = coinGeckoApi.price("boba-brewery", "usd");
        log.info("{}", usd);
    }


    @Test
    public void CoinGeckoApiTest2() {
        String usd = coinGeckoApi.coinList(true);
        log.info("{}", usd);
    }

}
