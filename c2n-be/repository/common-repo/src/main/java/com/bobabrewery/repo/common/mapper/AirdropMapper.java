package com.bobabrewery.repo.common.mapper;

import com.bobabrewery.common.PageParam;
import com.bobabrewery.repo.common.domain.model.Airdrop;

import java.util.List;

/**
 * @author PailieXiangLong
 */
public interface AirdropMapper {

    int deleteById(Long id);

    int create(Airdrop record);

    int creates(List<Airdrop> records);

    int createHas(Airdrop record);

    Long count(Airdrop where);

    List<Airdrop> list(PageParam page, Airdrop where);

    Airdrop findById(Long id);

    Airdrop findByPidAndWalletAddress(Integer pid, String walletAddress);

    List<Airdrop> findByIds(List<Long> ids);

    int updateByIdHas(Airdrop record);

    int updateById(Airdrop record);
}
