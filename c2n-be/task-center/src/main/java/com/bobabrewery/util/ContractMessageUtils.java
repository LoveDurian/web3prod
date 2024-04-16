package com.bobabrewery.util;

import com.alibaba.fastjson.JSON;
import com.bobabrewery.repo.common.domain.model.Project;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.tuples.generated.Tuple14;
import org.web3j.tuples.generated.Tuple2;
import org.web3j.tuples.generated.Tuple3;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 * @Author chenkj
 * @Date 2022/1/10 10:36 上午
 * 从合约拉取信息
 */
public class ContractMessageUtils {

    private final static ContractGasProvider contractGasProvider = new DefaultGasProvider();
    public static final BigDecimal e24 = new BigDecimal("1000000000000000000000000");
    public static final BigDecimal e36 = new BigDecimal("1000000000000000000000000000000000000");

    /**
     * 从合约拉信息到项目中
     *
     * @param web3j
     * @param project
     * @return
     * @throws Exception
     */
    public static Project pullContractInfo(Web3j web3j, Project project, BigDecimal price) throws Exception {
        Credentials credentials = CredentialsUtils.getCredentials();
        BrewerySale brewerySale = BrewerySale.load(project.getSaleContractAddress(), web3j, credentials, contractGasProvider);
        //注册信息
        Tuple3<BigInteger, BigInteger, BigInteger> registrationInfo = brewerySale.registration().send();
        //解锁信息
        Tuple2<List<BigInteger>, List<BigInteger>> vestingInfo = brewerySale.getVestingInfo().send();
        //销售信息
        Tuple14<String, Boolean, Boolean, Boolean, Boolean, String, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger> sale = brewerySale.sale().send();

        // registration
        project.setRegistrationTimeStarts(new Date(registrationInfo.component1().longValue() * 1000L));
        project.setRegistrationTimeEnds(new Date(registrationInfo.component2().longValue() * 1000L));
        project.setNumberOfRegistrants(registrationInfo.component3().intValue());
        // sale
        project.setSaleStart(new Date(sale.component11().longValue() * 1000L));
        project.setSaleEnd(new Date(sale.component12().longValue() * 1000L));
        project.setMaxParticipation(String.valueOf(sale.component14()));
        String tokenPriceInPT = String.valueOf(sale.component7());
        project.setTokenPriceInPT(tokenPriceInPT);
        project.setTotalTokensSold(String.valueOf(sale.component9()));
        project.setAmountOfTokensToSell(String.valueOf(sale.component8()));
        project.setUnlockTime(new Date(sale.component13().longValue() * 1000L));
        project.setTge(new Date(sale.component13().longValue() * 1000L));
        BigDecimal totalSoldDec = new BigDecimal(sale.component9());
        // totalRaised = TotalTokensSold *  tokenPriceInPT  * USDT_price
        //             550000000000000000000000 * 765500 * 1 / if boba : e24  bsc: 36
        String totalRaised;

        switch (project.getChainId()) {
            case 28:
            case 288:
                totalRaised = totalSoldDec.multiply(new BigDecimal(tokenPriceInPT)).multiply(price).divide(e24).toPlainString();
                break;
            case 97:
            case 56:
                totalRaised = totalSoldDec.multiply(new BigDecimal(tokenPriceInPT)).multiply(price).divide(e36).toPlainString();
                break;
            default:
                totalRaised = totalSoldDec.multiply(new BigDecimal(tokenPriceInPT)).multiply(price).divide(e36).toPlainString();
                break;
        }
        project.setTotalRaised(totalRaised);
        //vestingInfo
        project.setVestingPortionsUnlockTime(JSON.toJSONString(vestingInfo.component1()));
        project.setVestingPercentPerPortion(JSON.toJSONString(vestingInfo.component2()));
        return project;
    }
}
