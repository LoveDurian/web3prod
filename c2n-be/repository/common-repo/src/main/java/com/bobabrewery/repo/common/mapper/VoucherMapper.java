package com.bobabrewery.repo.common.mapper;

import com.bobabrewery.repo.common.domain.model.Voucher;

/**
 * @author orange
 * @description 针对表【brewery_voucher】的数据库操作Mapper
 * @createDate 2022-04-20 14:37:44
 * @Entity com.bobabrewery.repo.common.domain.model.Voucher
 */
public interface VoucherMapper {

    Voucher findByWalletAddress(String walletAddress);

    int updateStatusByWalletAddress(String walletAddress);

}




