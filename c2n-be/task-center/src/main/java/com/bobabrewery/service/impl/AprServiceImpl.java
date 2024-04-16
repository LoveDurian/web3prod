package com.bobabrewery.service.impl;

import com.bobabrewery.domain.PancakeSwapPrice;
import com.bobabrewery.enums.Web3Net;
import com.bobabrewery.service.AprService;
import com.bobabrewery.service.PancakeSwapPriceApi;
import com.bobabrewery.util.CredentialsUtils;
import com.bobabrewery.util.LP;
import com.bobabrewery.util.StakeContract;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.tuples.generated.Tuple3;
import org.web3j.tuples.generated.Tuple5;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

@Slf4j
@Service
public class AprServiceImpl implements AprService {

    @Value("${stake.bsc.contract}")
    private String stakeBscAddress;

    @Value("${stake.boba.contract}")
    private String stakeBobaAddress;

    public static final String BSClpContractAddress = "0x2a50b9D094f3aceA0Ddd7E6077dD6dadA51ac36d";

    public static final String BObalpContractAddress = "0x8Ebb37552fFaB6997baD219213019718abea2cE8";

    private final static ContractGasProvider contractGasProvider = new DefaultGasProvider();

    @Override
    public BigDecimal priceInLPTokenBSC(BigDecimal breweryPrice) throws Exception {
        Web3j web3j = Web3Net.MAIN_BSC.getWeb3j();
        Credentials credentials = CredentialsUtils.getCredentials();
        LP load = LP.load(BSClpContractAddress, web3j, credentials, contractGasProvider);
        BigInteger totalSupply = load.totalSupply().send();
        Tuple3<BigInteger, BigInteger, BigInteger> send = load.getReserves().send();
        BigInteger reserves = send.component1();
        BigDecimal liquidity = breweryPrice.multiply(new BigDecimal(reserves)).multiply(new BigDecimal(2));
        return liquidity.divide(new BigDecimal(totalSupply), RoundingMode.HALF_UP);
    }

    @Override
    public BigDecimal priceInLPTokenBoba(BigDecimal breweryPrice) throws Exception {
        Web3j web3j = Web3Net.MAIN_BOBA.getWeb3j();
        Credentials credentials = CredentialsUtils.getCredentials();
        LP load = LP.load(BObalpContractAddress, web3j, credentials, contractGasProvider);
        BigInteger totalSupply = load.totalSupply().send();
        Tuple3<BigInteger, BigInteger, BigInteger> send = load.getReserves().send();
        BigInteger reserves = send.component1();
        BigDecimal liquidity = breweryPrice.multiply(new BigDecimal(reserves)).multiply(new BigDecimal(2));
        return liquidity.divide(new BigDecimal(totalSupply), RoundingMode.HALF_UP);
    }


    @Resource
    private PancakeSwapPriceApi pancakeSwapPriceApi;

    @Override
    public BigDecimal getBreweryPrice() {
        PancakeSwapPrice pancakeSwapPrice = pancakeSwapPriceApi.breweryPrice();
        if (pancakeSwapPrice != null && pancakeSwapPrice.getData() != null) {
            return new BigDecimal(pancakeSwapPrice.getData().getPrice());
        } else {
            throw new RuntimeException("getBreweryPrice 获取失败");
        }
    }

    /**
     * 获取BSC上质押的LP总量
     *
     * @return
     */
    @Override
    public BigDecimal getBSCTotalDeposits() throws Exception {
        Web3j build = Web3Net.MAIN_BSC.getWeb3j();
        Credentials credentials = CredentialsUtils.getCredentials();
        StakeContract load = StakeContract.load(stakeBscAddress, build, credentials, contractGasProvider);
        Tuple5<String, BigInteger, BigInteger, BigInteger, BigInteger> send1 = load.poolInfo(BigInteger.valueOf(2)).send();
        return new BigDecimal(send1.component5());
    }


    /**
     * 获取Boba上质押的LP总量
     *
     * @return
     * @throws Exception
     */
    @Override
    public BigDecimal getBobaTotalDeposits() throws Exception {
        Web3j build = Web3Net.MAIN_BOBA.getWeb3j();
        Credentials credentials = CredentialsUtils.getCredentials();
        StakeContract load = StakeContract.load(stakeBobaAddress, build, credentials, contractGasProvider);
        Tuple5<String, BigInteger, BigInteger, BigInteger, BigInteger> send1 = load.poolInfo(BigInteger.valueOf(2)).send();
        return new BigDecimal(send1.component5());
    }
}
