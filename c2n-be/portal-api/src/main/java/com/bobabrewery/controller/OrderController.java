package com.bobabrewery.controller;

import cn.hutool.crypto.SecureUtil;
import com.bobabrewery.common.PageParam;
import com.bobabrewery.common.Result;
import com.bobabrewery.common.exceptin.CommonException;
import com.bobabrewery.common.util.Page;
import com.bobabrewery.domain.param.CreateBridgeOrder;
import com.bobabrewery.domain.resp.CreateBridgeOrderResult;
import com.bobabrewery.enums.ReCode;
import com.bobabrewery.repo.common.domain.model.BridgeOrder;
import com.bobabrewery.service.BridgeOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * Bridge订单
 *
 * @author PailieXiangLong
 */
@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {

    private static final String KEY = "bre2022.PL";


    @Resource
    private BridgeOrderService bridgeOrderServiceImpl;

    /**
     * 创建订单接口
     *
     * @param accountId 钱包地址
     * @param hash      hash生成算法: md5(accountId+timeStamp+bre2022.PL)
     * @param timeStamp
     * @param order
     * @return
     * @apiNote 原:(生成UUID)
     * 请求体中 deposit_coin receive_coin deposit_amount 为必须
     */
    @PostMapping("/create/{accountId}")
    public Result<CreateBridgeOrderResult.DataDTO> generateOrder(@PathVariable("accountId") String accountId, @NotBlank String hash, @NotNull Long timeStamp, @RequestBody CreateBridgeOrder order) {
        long current = System.currentTimeMillis() / 1000L;
        long time = timeStamp / 1000L;
        // 当前时间-时间戳的绝对值小于等于1 (时间戳与当前时间相差在60秒内,方可请求成功)
        if (Math.abs((current - time)) <= 60) {
            String md5sum = SecureUtil.md5(accountId + timeStamp + KEY);
            if (hash.equals(md5sum)) {
                String uuid = UUID.randomUUID().toString();
                return bridgeOrderServiceImpl.generator(accountId, uuid, order);
            }
        }
        throw new CommonException(ReCode.INVALID_PARAMETERS);
    }


    /**
     * SafePal更新订单接口
     *
     * @param orderId 订单ID
     * @param txId    交易ID
     * @param hash    md5(orderId+txId+bre2022.PL);
     * @return
     * @apiNote 返回状态: 200->调用SafePal接口成功 404-> 我们数据库没这个订单  其他-调用失败or没有调用
     */
    @PostMapping("update")
    public Result<String> update(@RequestParam String orderId, @RequestParam String txId, @NotBlank String hash) {
        String md5sum = SecureUtil.md5(orderId + txId + KEY);
        if (hash.equals(md5sum)) {
            return bridgeOrderServiceImpl.updateOrder(orderId, txId);
        }
        throw new CommonException(ReCode.INVALID_PARAMETERS);
    }

    /**
     * 用户订单列表
     *
     * @param pageParam
     * @param accountId 钱包地址
     * @param hash      hash生成算法: md5(accountId+bre2022.PL)
     * @return
     */
    @PostMapping("/list")
    public Result<Page<BridgeOrder>> list(@Validated PageParam pageParam, @NotBlank String accountId, @NotBlank String hash) {
        String md5sum = SecureUtil.md5(accountId + KEY);
        if (hash.equals(md5sum)) {
            return bridgeOrderServiceImpl.list(pageParam, accountId);
        } else {
            throw new CommonException(ReCode.INVALID_PARAMETERS);
        }
    }

}
