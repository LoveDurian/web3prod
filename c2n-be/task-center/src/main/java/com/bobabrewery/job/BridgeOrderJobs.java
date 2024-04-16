package com.bobabrewery.job;

import com.bobabrewery.domain.SafePalResult;
import com.bobabrewery.repo.common.domain.model.BridgeOrder;
import com.bobabrewery.repo.common.mapper.BridgeOrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author PailieXiangLong
 */
@Slf4j
@Component
public class BridgeOrderJobs {

    @Resource
    private BridgeOrderMapper bridgeOrderMapper;

    private final static String BASE_URL = "https://s.isafepal.com";

    private final static WebClient webClient = WebClient.builder()
            .baseUrl(BASE_URL)
            .build();

    /**
     * 定时更新用户的订单
     */
    @Scheduled(cron = "*/30 * * * * ?")
    public void updateOrderInfo() {
        List<String> accountIds = bridgeOrderMapper.findAllAccountId();
        int updateCount = 0;
        for (String accountId : accountIds) {
            List<String> orderIds = bridgeOrderMapper.findOrderIdByAccountIdNotEnd(accountId);
            for (String orderId : orderIds) {
                SafePalResult result = webClient.get()
                        .uri(uriBuilder ->
                                uriBuilder
                                        .queryParam("id", orderId)
                                        .path("/safewallet/bridge/v2/order")
                                        .build()
                        )
                        .retrieve().bodyToMono(SafePalResult.class).block();
                if (result != null) {
                    if (result.getCode() == 0 && result.getData() != null) {
                        BridgeOrder order = new BridgeOrder();
                        BeanUtils.copyProperties(result.getData(), order);
                        int i = bridgeOrderMapper.updateById(order);
                        updateCount += i;
                    } else {
                        log.error("拉取用户:{},订单信息:{},失败:{}", accountId, orderId, result.getMsg());
                    }
                } else {
                    log.error("拉取用户:{}订单信息:{}失败null", accountId, orderId);
                }
            }
        }
        log.info("[更新用户BridgeOrder]本次更新数量：{}", updateCount);
    }

}
