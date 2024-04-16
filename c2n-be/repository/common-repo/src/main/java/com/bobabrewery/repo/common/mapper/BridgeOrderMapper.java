package com.bobabrewery.repo.common.mapper;

import com.bobabrewery.common.PageParam;
import com.bobabrewery.repo.common.domain.model.BridgeOrder;

import java.util.List;

/**
 * @author PailieXiangLong
 */
public interface BridgeOrderMapper {

    int deleteById(Long id);

    int create(BridgeOrder record);

    int creates(List<BridgeOrder> records);

    int createHas(BridgeOrder record);

    Long count(BridgeOrder where);

    List<BridgeOrder> list(PageParam page, BridgeOrder where);

    BridgeOrder findById(Long id);

    int countByOrderId(String orderId);

    List<BridgeOrder> findByIds(List<Long> ids);

    int updateByIdHas(BridgeOrder record);

    int updateById(BridgeOrder record);

    int updateDepositTxidByOrderId(String orderId, String depositTxid);

    List<String> findOrderIdByAccountId(String accountId);

    List<String> findOrderIdByAccountIdNotEnd(String accountId);

    List<String> findAllAccountId();
}
