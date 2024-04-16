package com.bobabrewery.privatesign.controller;

import com.bobabrewery.common.Result;
import com.bobabrewery.common.enums.ReCode;
import com.bobabrewery.common.exceptin.CommonException;
import com.bobabrewery.privatesign.util.CredentialsUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.crypto.Hash;
import org.web3j.crypto.Sign;
import org.web3j.utils.Numeric;

import java.math.BigInteger;

/**
 * @author orange
 */
@Slf4j
@RestController

public class PrivateController {

    /**
     * 设置接口的访问token
     */
    private static final String ACCESS_TOKEN = "a1075cca07d0488e8d3035b5d46dcd3b";

    /**
     * 设置私钥
     *
     * @param privateKey
     * @return
     */
    @PostMapping("/privateKey/{privateKey}")
    public Result<String> privateKey(@PathVariable String privateKey, @RequestHeader(name = "accessToken") String accessToken) {
        if (accessToken.equals(ACCESS_TOKEN)) {
            if (CredentialsUtils.privateKey != null) {
                throw new CommonException(ReCode.PRIVATE_KEY_EXIST);
            } else {
                CredentialsUtils.privateKey = privateKey;
                return Result.ok();
            }
        } else {
            throw new CommonException(ReCode.INVALID_PARAMETERS);
        }

    }

    /**
     * sign_registration
     *
     * @param userAddress
     * @param contractAddress
     * @return
     */
    @PostMapping("/sign_registration")
    public String signRegistration(String userAddress, String contractAddress) {
        if (StringUtils.isBlank(userAddress) || StringUtils.isBlank(contractAddress)) {
            throw new CommonException(ReCode.INVALID_PARAMETERS);
        }
        String hexString = Numeric.prependHexPrefix((Numeric.cleanHexPrefix(userAddress).concat(Numeric.cleanHexPrefix(contractAddress))).toLowerCase());
        String sign = getSign(hexString);
        log.info("userAddress:{}, contractAddress: {}, sign:{}", userAddress, contractAddress, sign);
        return sign;
    }

    /**
     * sign_participation
     *
     * @param userAddress
     * @param amount
     * @param contractAddress
     * @return
     */
    @PostMapping("/sign_participation")
    public String signParticipation(String userAddress, String amount, String contractAddress) {
        if (StringUtils.isBlank(userAddress) || StringUtils.isBlank(contractAddress) || StringUtils.isBlank(amount)) {
            throw new CommonException(ReCode.INVALID_PARAMETERS);
        }
        String userAddressHexString = Numeric.cleanHexPrefix(userAddress);
        String amountHexString = Numeric.toHexStringNoPrefixZeroPadded(new BigInteger(amount), 64);
        String contractAddressHesString = Numeric.cleanHexPrefix(contractAddress);
        String hexString = Numeric.prependHexPrefix((userAddressHexString.concat(amountHexString).concat(contractAddressHesString)).toLowerCase());
        String sign = getSign(hexString);
        log.info("userAddress:{}, amount:{} ,contractAddress: {}, sign:{}", userAddress, amount, contractAddress, sign);
        return sign;
    }

    /**
     * 根据用户参数获取秘钥
     *
     * @param hex
     * @return
     */
    @PostMapping("/sign")
    public String getSign(String hex) {
        if (CredentialsUtils.privateKey == null) {
            throw new CommonException(ReCode.PRIVATE_KEY_NOT_EXIST);
        }
        byte[] messageHash = Hash.sha3(Numeric.hexStringToByteArray(hex));
        byte[] ethereumMessageHash = Sign.getEthereumMessageHash(messageHash);
        Sign.SignatureData signatureData = Sign.signMessage(ethereumMessageHash, CredentialsUtils.getInstance().getEcKeyPair(), false);
        return Numeric.toHexStringNoPrefix(signatureData.getR()).concat(Numeric.toHexStringNoPrefix(signatureData.getS())).concat(Numeric.toHexStringNoPrefix(signatureData.getV()));
    }
}
