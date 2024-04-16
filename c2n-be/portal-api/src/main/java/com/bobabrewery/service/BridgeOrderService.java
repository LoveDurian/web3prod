package com.bobabrewery.service;

import com.bobabrewery.common.PageParam;
import com.bobabrewery.common.Result;
import com.bobabrewery.common.util.Page;
import com.bobabrewery.domain.param.CreateBridgeOrder;
import com.bobabrewery.domain.resp.CreateBridgeOrderResult;
import com.bobabrewery.repo.common.domain.model.BridgeOrder;

/**
 * @author PailieXiangLong
 */
public interface BridgeOrderService {
    /**
     * 生成订单号
     *
     * @param account 用户钱包地址
     * @param order
     * @return
     */
    Result<CreateBridgeOrderResult.DataDTO> generator(String account, String uuid, CreateBridgeOrder order);


    /**
     * SafePal更新订单
     *
     * @param orderId 订单ID
     * @param txId    链上交易ID
     * @return
     */
    Result<String> updateOrder(String orderId, String txId);


    /**
     * 用户订单列表
     *
     * @param pageParam
     * @param accountId
     * @return
     */
    Result<Page<BridgeOrder>> list(PageParam pageParam, String accountId);


}
