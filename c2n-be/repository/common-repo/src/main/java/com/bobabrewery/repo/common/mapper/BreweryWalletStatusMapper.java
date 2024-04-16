package com.bobabrewery.repo.common.mapper;

import com.bobabrewery.repo.common.domain.model.BreweryWalletStatus;

/**
 * @author orange
 * @description 针对表【brewery_wallet_status】的数据库操作Mapper
 * @createDate 2022-02-18 16:10:34
 * @Entity com.bobabrewery.repo.common.domain.model.BreweryWalletStatus
 */
public interface BreweryWalletStatusMapper {

    int insertSelective(BreweryWalletStatus record);

    int updateByPrimaryKeySelective(BreweryWalletStatus record);

    BreweryWalletStatus selectByWalletAddressAndType(String walletAddress, Integer type);

}
