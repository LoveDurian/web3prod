package com.bobabrewery.domain.param;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author PailieXiangLong
 */
@Data
public class CreateCyberpop {


    /**
     * 钱包地址
     *
     * @mock 0x123456789
     */
    @NotBlank(message = "not null")
    private String walletAddress;

    /**
     * 购买数量
     *
     * @mock 1
     */
    @NotNull
    @Min(value = 1)
    @Max(value = 3)
    private Integer amount;


    @NotBlank
    private String nftType;
    /**
     * 合约地址
     *
     * @mock 0x123456789
     */
    @NotBlank(message = "not null")
    private String contractAddress;

}
