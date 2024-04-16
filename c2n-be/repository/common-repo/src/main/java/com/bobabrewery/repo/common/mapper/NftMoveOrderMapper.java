package com.bobabrewery.repo.common.mapper;

import com.bobabrewery.repo.common.domain.model.NftMoveOrder;

import java.util.List;

/**
 * @author PailieXiangLong
 */
public interface NftMoveOrderMapper {

    int deleteById(Long id);


    int saledTokenId(Long tokenId);

    int create(NftMoveOrder record);

    int creates(List<NftMoveOrder> records);

    int createHas(NftMoveOrder record);

    Long count(NftMoveOrder where);

    NftMoveOrder findById(Long id);

    List<NftMoveOrder> findByIds(List<Long> ids);

    List<Long> findTokenIdList();

    List<String> findTxIdByStatus(Integer status);

    int updateStatusByTxId(Integer status, String txId);

    int updateByIdHas(NftMoveOrder record);

    int updateByTokenId(Long tokenId, String txId, Integer status);

    int updateById(NftMoveOrder record);
}
