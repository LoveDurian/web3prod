package com.bobabrewery.repo.common.mapper;

import com.bobabrewery.common.PageParam;
import com.bobabrewery.repo.common.domain.model.NFTWhite;

import java.util.List;

/**
 * @author PailieXiangLong
 */
public interface NftWhiteMapper {

    int deleteById(Long id);

    int create(NFTWhite record);

    int creates(List<NFTWhite> records);

    int createHas(NFTWhite record);

    Long count(NFTWhite where);

    List<NFTWhite> list(PageParam page, NFTWhite where);

    NFTWhite findById(Long id);

    List<NFTWhite> findByIds(List<Long> ids);

    int updateByIdHas(NFTWhite record);

    int updateById(NFTWhite record);

    NFTWhite findByWalletAddress(String walletAddress);

    int exist(String walletAddress);
}
