package com.bobabrewery.service.impl;

import com.bobabrewery.service.EncodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import javax.annotation.PostConstruct;

/**
 * @author PailieXiangLong
 */
@Slf4j
@Service
public class EncodeServiceImpl implements EncodeService {

    @Value("${encode.server}")
    private String baseUrl;
    private WebClient webClient;

    @PostConstruct
    private void init() {
        webClient = WebClient.builder().baseUrl(baseUrl).build();
    }

    @Override
    public String sign(String hexString) {
        log.info("hexString={}", hexString);
        return webClient.post()
                .uri(uriBuilder -> uriBuilder.path("/encode/sign")
                        .queryParam("hex", hexString).build())
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
