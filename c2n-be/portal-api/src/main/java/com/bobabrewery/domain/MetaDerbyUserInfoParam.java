package com.bobabrewery.domain;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;

/**
 * @author PailieXiangLong
 */
@Data
public class MetaDerbyUserInfoParam {

    /**
     * 用户钱包地址
     *
     * @mock 0x0000123456789123456789123456789123456789
     */
    @NotBlank
    @Length(min = 42, max = 64)
    private String walletAddress;

    /**
     * 马的ID
     */
    @NotNull(message = "not null")
    private Integer horsesId;

    /**
     * 用户质押的数量 来源:合约上的betInfo方法 单位Wei
     *
     * @mock 150000000000000000000
     */
    @NotNull(message = "not null")
    private BigInteger staked;

}
