package com.bobabrewery.domain;

import lombok.Data;

import java.math.BigInteger;
import java.util.List;

/**
 * @Author chenkj
 * @Date 2022/1/18 2:23 下午
 */
@Data
public class ContractInfo {
    private String token;
    private Boolean isCreated;
    private Boolean earningsWithdrawn;
    private Boolean leftoverWithdrawn;
    private Boolean tokensDeposited;
    private String saleOwner;
    private BigInteger tokenPriceInPT;
    private BigInteger amountOfTokensToSell;
    private BigInteger totalTokensSold;
    private BigInteger totalETHRaised;
    private BigInteger saleStart;
    private BigInteger saleEnd;
    private BigInteger tokensUnlockTime;
    private BigInteger maxParticipation;
    private BigInteger registrationTimeStarts;
    private BigInteger registrationTimeEnds;
    private BigInteger numberOfRegistrants;
    private List<BigInteger> vestingPortionsUnlockTime;
    private List<BigInteger> vestingPercentPerPortion;
}
