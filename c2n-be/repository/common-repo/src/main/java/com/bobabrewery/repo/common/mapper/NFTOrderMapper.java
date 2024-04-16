package com.bobabrewery.repo.common.mapper;

import com.bobabrewery.common.PageParam;
import com.bobabrewery.repo.common.domain.model.NFTOrder;

import java.util.List;

/**
 * @author PailieXiangLong
 */
public interface NFTOrderMapper {

    int deleteById(Long id);

    int create(NFTOrder record);

    int creates(List<NFTOrder> records);

    int createHas(NFTOrder record);

    Long count(NFTOrder where);

    List<NFTOrder> list(PageParam page, NFTOrder where);

    NFTOrder findById(Long id);

    Long findByWalletAddress(String walletAddress);

    List<NFTOrder> findByIds(List<Long> ids);

    int updateByIdHas(NFTOrder record);

    int updateById(NFTOrder record);
}
