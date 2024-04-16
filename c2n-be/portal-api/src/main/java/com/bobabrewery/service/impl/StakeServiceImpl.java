package com.bobabrewery.service.impl;

import com.bobabrewery.common.Result;
import com.bobabrewery.common.exceptin.CommonException;
import com.bobabrewery.enums.ReCode;
import com.bobabrewery.enums.Web3Net;
import com.bobabrewery.repo.common.domain.model.BreweryWalletStatus;
import com.bobabrewery.repo.common.mapper.BreweryWalletStatusMapper;
import com.bobabrewery.service.StakeService;
import com.bobabrewery.util.AllocationStaking;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Staked
 */
@Slf4j
@Service
public class StakeServiceImpl implements StakeService {

    @Resource
    private BreweryWalletStatusMapper mapper;

    @Value("${staking.boba}")
    private String bobaStakeContract;

    @Value("${owner.private.key}")
    private String privateKey;

    private Web3j web3j = Web3Net.MAIN_BOBA.getWeb3j();
    private Credentials credentials;
    private static ContractGasProvider contractGasProvider = new DefaultGasProvider();

    @PostConstruct
    public void init() {
        credentials = Credentials.create(privateKey);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public Result<Boolean> stake(String walletAddress, String contractAddress, String amount, Integer type, Integer chainId) {
        boolean isSuccess;
        if (!check(walletAddress)) {
            throw new CommonException(ReCode.INVALID_PARAMETERS);
        }
        BreweryWalletStatus walletStatus = mapper.selectByWalletAddressAndType(walletAddress, type);
        if (walletStatus != null) {
            walletStatus.setAmount(walletStatus.getAmount().add(new BigDecimal(amount)));
            walletStatus.setStaked(1);
            walletStatus.setType(type);
            walletStatus.setChainId(chainId);
            walletStatus.setContractAddress(contractAddress);
            isSuccess = mapper.updateByPrimaryKeySelective(walletStatus) > 0;
        } else {
            BreweryWalletStatus insertWalletStatus = new BreweryWalletStatus();
            insertWalletStatus.setWalletAddress(walletAddress);
            insertWalletStatus.setAmount(new BigDecimal(amount));
            insertWalletStatus.setType(type);
            insertWalletStatus.setStaked(1);
            insertWalletStatus.setChainId(chainId);
            insertWalletStatus.setContractAddress(contractAddress);
            isSuccess = mapper.insertSelective(insertWalletStatus) > 0;
        }
        return Result.ok(isSuccess);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public Result<Boolean> withdraw(String walletAddress, String contractAddress, String amount, Integer type, Integer chainId) {
        boolean isSuccess = false;
        if (!check(walletAddress)) {
            throw new CommonException(ReCode.INVALID_PARAMETERS);
        }
        BreweryWalletStatus walletStatus = mapper.selectByWalletAddressAndType(walletAddress, type);
        if (walletStatus != null) {
            try {
                walletStatus.setAmount(walletStatus.getAmount().subtract(new BigDecimal(amount)));
            } catch (NumberFormatException e) {
                throw new CommonException(ReCode.INVALID_PARAMETERS);
            }
            if (!chainId.equals(walletStatus.getChainId())) {
                walletStatus.setChainId(chainId);
            }
            if (!contractAddress.equals(walletStatus.getContractAddress())) {
                walletStatus.setContractAddress(contractAddress);
            }
            walletStatus.setWithdrawn(1);
            walletStatus.setType(type);
            isSuccess = mapper.updateByPrimaryKeySelective(walletStatus) > 0;
        }
        return Result.ok(isSuccess);
    }

    @Override
    public boolean checkStake(String walletAddress) throws Exception {
        AllocationStaking allocationStaking = AllocationStaking.load(bobaStakeContract, web3j, credentials, contractGasProvider);
        BigInteger send = allocationStaking.deposited(BigInteger.valueOf(0), walletAddress).send();
        return send != null;
    }


    static boolean check(String address) {
        return address.startsWith("0x") && address.length() > 40 && address.length() < 60;
    }
}
