package com.bobabrewery.common.domain;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * 用户钱包地址
 *
 * @author PailieXiangLong
 */
@Data
public class WalletAddress {
    /**
     * 用户钱包地址
     *
     * @mock 0x0000123456789123456789123456789123456789
     */
    @NotBlank(message = "not null")
    @Length(min = 42, max = 64, message = "length error")
    private String walletAddress;
}
