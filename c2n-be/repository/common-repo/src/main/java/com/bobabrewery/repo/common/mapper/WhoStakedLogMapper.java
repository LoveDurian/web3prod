package com.bobabrewery.repo.common.mapper;

import com.bobabrewery.repo.common.domain.model.WhoStakedLog;

/**
 * @Entity com.bobabrewery.repo.common.domain.model.WhoStakedLog
 */
public interface WhoStakedLogMapper {

    int deleteByPrimaryKey(Long id);

    int insert(WhoStakedLog record);

    int insertSelective(WhoStakedLog record);

    WhoStakedLog selectByPrimaryKey(Long id);

    WhoStakedLog selectByWalletAddress(String walletAddress);

    int updateByPrimaryKeySelective(WhoStakedLog record);

    int updateByPrimaryKey(WhoStakedLog record);

    int countByWalletAddressAndContractAddress(String walletAddress, String contractAddress);

}
