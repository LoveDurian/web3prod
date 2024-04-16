package com.bobabrewery.domain.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author PailieXiangLong
 */
@Data
public class CreateMoveOrder {

    /**
     * 钱包地址
     *
     * @mock 0x123456789
     */
    @NotBlank(message = "not null")
    private String walletAddress;
    /**
     * 合约地址
     *
     * @mock 0x123456789
     */
    @NotBlank(message = "not null")
    private String contractAddress;
}
