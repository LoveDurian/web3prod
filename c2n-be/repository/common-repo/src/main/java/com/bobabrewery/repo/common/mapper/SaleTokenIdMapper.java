package com.bobabrewery.repo.common.mapper;

import java.util.List;

/**
* @author orange
*/
public interface SaleTokenIdMapper {

    List<Long> findTokenIdList();

    List<Long> findByStatusLimit1(int len,Long max);

    int updateStatusByTxId(String txId,Integer status);

    int update(Long tokenID,String txId,String walletAddress,Integer status);

    void callBackTxId(String txId,Integer tokenId,String walletAddress,Integer status);
}




