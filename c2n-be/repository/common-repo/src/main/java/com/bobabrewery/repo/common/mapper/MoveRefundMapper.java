package com.bobabrewery.repo.common.mapper;

import com.bobabrewery.repo.common.domain.model.MoveRefund;

import java.util.List;

/**
 * @Entity com.bobabrewery.repo.common.domain.model.MoveRefund
 */
public interface MoveRefundMapper {

    List<MoveRefund> findByWalletAddress(String walletAddress);

    int exist(String walletAddress);

}




