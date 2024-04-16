package com.bobabrewery.service.impl;

import cn.hutool.crypto.SecureUtil;
import com.alibaba.fastjson.JSON;
import com.bobabrewery.common.PageParam;
import com.bobabrewery.common.Result;
import com.bobabrewery.common.exceptin.CommonException;
import com.bobabrewery.common.util.Page;
import com.bobabrewery.domain.param.CreateBridgeOrder;
import com.bobabrewery.domain.param.UpdateBridgeOrder;
import com.bobabrewery.domain.resp.CreateBridgeOrderResult;
import com.bobabrewery.domain.resp.UpdateBridgeOrderResult;
import com.bobabrewery.enums.ReCode;
import com.bobabrewery.repo.common.domain.model.BridgeOrder;
import com.bobabrewery.repo.common.mapper.BridgeOrderMapper;
import com.bobabrewery.service.BridgeOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import javax.annotation.Resource;
import java.time.Duration;

/**
 * @author PailieXiangLong
 */
@Slf4j
@Service
public class BridgeOrderServiceImpl implements BridgeOrderService {

    @Resource
    private BridgeOrderMapper mapper;

    private static final String hmacSHA256KEY = "dLFhCx8QJtgDtjEKeRxQqtRlzmLsirCNw5VPFU8JcbCisnxG1cd00vllodU6Fk6G";
    private static final String apiKey = "SbDvkmbPnxIbCXXpgk47CzpHJ5ATxWHc2nds1yxrw40ngx5CoLKKuTv0R6UZpADS";

    private final static String BASE_URL = "https://s.isafepal.com";

    private WebClient webClient = WebClient.builder()
            .baseUrl(BASE_URL)
            .build();

    public static final String CREATE_SIGNATURE_TEMPLATE = "receive_addr=%s&client_order=%s&timestamp=%s";

    public static final String UPDATE_SIGNATURE_TEMPLATE = "id=%s&txid=%s&timestamp=%s";

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public Result<CreateBridgeOrderResult.DataDTO> generator(String account, String uuid, CreateBridgeOrder createBridgeOrder) {

        createBridgeOrder.setAccountId(account);
        createBridgeOrder.setClientOrder(uuid);
        createBridgeOrder.setReceiveAddr(account);
        createBridgeOrder.setRefundAddr(account);

        long l = System.currentTimeMillis() / 1000L;
        String format = String.format(CREATE_SIGNATURE_TEMPLATE, account, uuid, String.valueOf(l));
        String signature = SecureUtil.hmacSha256(hmacSHA256KEY).digestHex(format);

        CreateBridgeOrderResult block = webClient.post().uri(uriBuilder ->
                        uriBuilder
                                .queryParam("timestamp", l)
                                .queryParam("signature", signature)
                                .path("/safewallet/bridge/v2/create_order")
                                .build()
                ).header("Api-Key", apiKey)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(createBridgeOrder)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve().bodyToMono(CreateBridgeOrderResult.class)
                .timeout(Duration.ofSeconds(5))
                .block();
        log.debug("REQUEST:Time:{},SIG:{},BODY:{}", l, signature, JSON.toJSONString(createBridgeOrder));
        log.debug("RESPONSE:{}", block);
        if (block != null) {
            if (block.getCode() != null && block.getCode() == 0 && block.getData() != null && block.getData().getStatus() > 0) {
                BridgeOrder order = new BridgeOrder();
                order.setOrderId(uuid);
                order.setAccountId(account);
                int has = mapper.createHas(order);
                log.info("[BridgeOrder]用户:{},创建:{},{}", account, uuid, has);
                return Result.ok(block.getData());
            } else {
                log.error("[BridgeOrder]用户:{} 创建订单:{} 失败 SafePalResult:{}", account, uuid, JSON.toJSONString(block));
                return Result.fail(ReCode.BRIDGE_ORDER_CREATE_FAILED, block.getMsg());
            }
        } else {
            log.error("[BridgeOrder]用户:{} 创建订单:{} 请求失败", account, uuid);
            return Result.fail(ReCode.BRIDGE_ORDER_CREATE_FAILED);
        }


    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public Result<String> updateOrder(String orderId, String txId) {
        if (mapper.countByOrderId(orderId) > 0) {
            long l = System.currentTimeMillis() / 1000L;
            String format = String.format(UPDATE_SIGNATURE_TEMPLATE, orderId, txId, String.valueOf(l));
            String signature = SecureUtil.hmacSha256(hmacSHA256KEY).digestHex(format);
            UpdateBridgeOrder updateBridgeOrder = new UpdateBridgeOrder();
            updateBridgeOrder.setId(orderId);
            updateBridgeOrder.setTxid(txId);
            UpdateBridgeOrderResult block = webClient.post().uri(uriBuilder ->
                            uriBuilder
                                    .queryParam("timestamp", l)
                                    .queryParam("signature", signature)
                                    .path("/safewallet/bridge/v2/notice_deposit")
                                    .build()
                    ).header("Api-Key", apiKey)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(updateBridgeOrder)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve().bodyToMono(UpdateBridgeOrderResult.class)
                    .timeout(Duration.ofSeconds(5))
                    .block();
            log.debug("REQUEST:Time:{},SIG:{},BODY:{}", l, signature, JSON.toJSONString(updateBridgeOrder));
            log.debug("RESPONSE:{}", block);
            if (block != null) {
                if (block.getCode() == 0) {
                    // 更新自己数据库的depositTxid
                    int i = mapper.updateDepositTxidByOrderId(orderId, txId);
                    log.info("[BridgeOrder]更新订单:{},交易ID:{}{}", orderId, txId, i > 0 ? "成功" : "失败");
                    return Result.ok();
                } else {
                    log.error("[BridgeOrder]更新订单:{},交易ID:{} 失败,SafePalResult:{}", orderId, txId, block.getMsg());
                    throw new CommonException(ReCode.FAILED, block.getMsg());
                }
            } else {
                log.error("[BridgeOrder]更新订单:{},交易ID:{} 请求失败", orderId, txId);
                throw new CommonException(ReCode.FAILED);
            }
        } else {
            throw new CommonException(ReCode.DATA_NOT_FOUND);
        }
    }

    @Override
    public Result<Page<BridgeOrder>> list(PageParam pageParam, String accountId) {
        BridgeOrder order = new BridgeOrder();
        order.setAccountId(accountId);
        return Result.ok(new Page<>(
                pageParam,
                mapper.count(order),
                mapper.list(pageParam, order)
        ));
    }
}
