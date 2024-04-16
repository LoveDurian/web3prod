package com.bobabrewery.service.impl;

import com.bobabrewery.common.Result;
import com.bobabrewery.common.exceptin.CommonException;
import com.bobabrewery.enums.ReCode;
import com.bobabrewery.repo.common.domain.model.Airdrop;
import com.bobabrewery.repo.common.mapper.AirdropMapper;
import com.bobabrewery.repo.common.mapper.ProductContractMapper;
import com.bobabrewery.service.AirDropService;
import com.bobabrewery.service.EncodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.web3j.utils.Numeric;

import javax.annotation.Resource;
import java.math.BigInteger;

/**
 * @author PailieXiangLong
 */
@Slf4j
@Service
public class AirDropServiceImpl implements AirDropService {

    @Resource
    private ProductContractMapper productContractMapper;

    @Resource
    private EncodeService encodeService;

    @Resource
    private AirdropMapper airdropMapper;


    @Override
    public Result<String> airDrop(String walletAddress, Integer projectId, String contractAddress) {
        Airdrop byPidAndWalletAddress = airdropMapper.findByPidAndWalletAddress(projectId, walletAddress);
        if (byPidAndWalletAddress != null) {
            String amountHexString = Numeric.toHexStringNoPrefixZeroPadded(BigInteger.valueOf(byPidAndWalletAddress.getAmount()), 64);
            String hex = Numeric.cleanHexPrefix(walletAddress).concat(amountHexString).concat(Numeric.cleanHexPrefix(contractAddress));
            String s = Numeric.prependHexPrefix(hex.toLowerCase());
            String sign = encodeService.sign(s);
            return Result.ok(sign);
        } else {
            throw new CommonException(ReCode.FAILED);
        }
    }

    @Override
    public Result<Long> userAriDropAmount(String walletAddress, Integer projectId) {
        Airdrop byPidAndWalletAddress = airdropMapper.findByPidAndWalletAddress(projectId, walletAddress);
        if (byPidAndWalletAddress != null) {
            return Result.ok(byPidAndWalletAddress.getAmount());
        } else {
            throw new CommonException(ReCode.FAILED);
        }
    }
}
