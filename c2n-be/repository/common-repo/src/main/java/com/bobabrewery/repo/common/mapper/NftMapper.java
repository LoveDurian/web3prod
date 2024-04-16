package com.bobabrewery.repo.common.mapper;

import com.bobabrewery.common.PageParam;
import com.bobabrewery.repo.common.domain.model.Nft;

import java.util.List;

/**
 * @author PailieXiangLong
 */
public interface NftMapper {
    Long count(Nft where);

    List<Nft> list(PageParam page, Nft where);

    Nft findById(Integer id);

    List<Nft> findByIds(List<Long> ids);

    int updateStatus(Integer id, Integer status);

}
