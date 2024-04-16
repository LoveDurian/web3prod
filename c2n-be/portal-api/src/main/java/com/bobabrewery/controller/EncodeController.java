package com.bobabrewery.controller;

import com.bobabrewery.common.Result;
import com.bobabrewery.common.exceptin.CommonException;
import com.bobabrewery.enums.ReCode;
import com.bobabrewery.repo.common.domain.model.UserInfo;
import com.bobabrewery.repo.common.mapper.UserInfoDao;
import com.bobabrewery.repo.common.mapper.VoucherMapper;
import com.bobabrewery.service.EncodeService;
import com.bobabrewery.util.CredentialsUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.utils.Numeric;

import javax.annotation.Resource;
import java.math.BigInteger;

/**
 * 对用户信息使用私钥加签
 */
@Slf4j
@RestController
@RequestMapping("/encode")
public class EncodeController {

    @Resource
    private UserInfoDao userInfoDao;

    @Resource
    private EncodeService encodeServiceImpl;

    @Resource
    private CredentialsUtils credentialsUtils;


    @Resource
    private VoucherMapper voucherMapper;

    /**
     * sign_registration
     *
     * @param userAddress     用户钱包地址
     * @param contractAddress 合约地址
     * @return
     */
    @PostMapping("/sign_registration")
    public Result<String> signRegistration(String userAddress, String contractAddress) {
        if (StringUtils.isBlank(userAddress) || StringUtils.isBlank(contractAddress)) {
            throw new CommonException(ReCode.INVALID_PARAMETERS);
        }
        UserInfo byAccountId = userInfoDao.findByAccountId(userAddress);
        if (byAccountId == null) {
            throw new CommonException(ReCode.USER_DID_NOT_REGISTER);
        }
        String hex = Numeric.prependHexPrefix((Numeric.cleanHexPrefix(userAddress).concat(Numeric.cleanHexPrefix(contractAddress))).toLowerCase());
        return Result.ok(encodeServiceImpl.sign(hex));
    }

    /**
     * sign_participation
     *
     * @param userAddress     用户钱包
     * @param amount          数量
     * @param contractAddress 合约地址
     * @return
     */
    @PostMapping("/sign_participation")
    public Result<String> signParticipation(String userAddress, String amount, String contractAddress) {
        if (StringUtils.isBlank(userAddress) || StringUtils.isBlank(contractAddress) || StringUtils.isBlank(amount)) {
            throw new CommonException(ReCode.INVALID_PARAMETERS);
        }
        UserInfo byAccountId = userInfoDao.findByAccountId(userAddress);
        if (byAccountId == null) {
            throw new CommonException(ReCode.USER_DID_NOT_REGISTER);
        }

        String userAddressHexString = Numeric.cleanHexPrefix(userAddress);
        String amountHexString = Numeric.toHexStringNoPrefixZeroPadded(new BigInteger(amount), 64);
        String contractAddressHesString = Numeric.cleanHexPrefix(contractAddress);
        String hexString = Numeric.prependHexPrefix((userAddressHexString.concat(amountHexString).concat(contractAddressHesString)).toLowerCase());
        return Result.ok(encodeServiceImpl.sign(hexString));
    }


}
