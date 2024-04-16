package com.bobabrewery.repo.common.mapper;

import com.bobabrewery.repo.common.domain.model.ReferralCode;

import java.util.List;

/**
 * @author PailieXiangLong
 */
public interface ReferralCodeMapper {

    int create(ReferralCode record);

    int createHas(ReferralCode record);

    Long count(ReferralCode where);

    ReferralCode findByReferralCode(String referralCode);

    String findReferralCodeByWalletAddressAndPid(String walletAddress, Integer pid);

    int updateStatusByReferralCodeAndParticipant(String referralCode, String participant, Integer status);

    Long countByReferralCodeAndStatus(String referralCode);

    List<ReferralCode> list();

}
