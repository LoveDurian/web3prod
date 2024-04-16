package com.bobabrewery.service.impl;

import com.bobabrewery.common.exceptin.CommonException;
import com.bobabrewery.enums.ReCode;
import com.bobabrewery.repo.common.domain.model.ReferralCode;
import com.bobabrewery.repo.common.mapper.ReferralCodeMapper;
import com.bobabrewery.service.ReferralCodeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author PailieXiangLong
 */
@Slf4j
@Service
public class ReferralCodeServiceImpl implements ReferralCodeService {

    @Resource
    private ReferralCodeMapper mapper;

    private static final String FORMAT = "%s_%s";

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public String generateReferralCode(String walletAddress, Integer pid) {
        String referralCode = mapper.findReferralCodeByWalletAddressAndPid(walletAddress, pid);
        if (StringUtils.isNotBlank(referralCode)) {
            return referralCode;
        } else {
            ReferralCode code = new ReferralCode();
            code.setWalletAddress(walletAddress);
            code.setPid(pid);
            String generateCode = DigestUtils.md5Hex(String.format(FORMAT, walletAddress, pid));
            code.setReferralCode(generateCode);
            int has = mapper.createHas(code);
            if (has > 0) {
                return generateCode;
            } else {
                throw new CommonException(ReCode.FAILED);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public Boolean bindReferralCode(String referralCode, String participant) {
        ReferralCode byReferralCode = mapper.findByReferralCode(referralCode);
        if (byReferralCode != null) {
            byReferralCode.setParticipant(participant);
            int has = mapper.createHas(byReferralCode);
            if (has > 0) {
                return true;
            } else {
                throw new CommonException(ReCode.FAILED);
            }
        } else {
            throw new CommonException(ReCode.FAILED);
        }
    }

    @Override
    public Boolean referralSuccess(String referralCode, String participant) {
        int i = mapper.updateStatusByReferralCodeAndParticipant(referralCode, participant, 1);
        if (i > 0) {
            return true;
        } else {
            throw new CommonException(ReCode.FAILED);
        }
    }

    @Override
    public Long countReferralSuccess(String referralCode) {
        return mapper.countByReferralCodeAndStatus(referralCode);
    }
}
