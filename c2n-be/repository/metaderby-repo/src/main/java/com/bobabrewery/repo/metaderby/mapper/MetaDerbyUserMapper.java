package com.bobabrewery.repo.metaderby.mapper;

import java.math.BigInteger;

/**
 * @author PailieXiangLong
 */
public interface MetaDerbyUserMapper {
    BigInteger findByWalletAddress(String walletAddress);
}
