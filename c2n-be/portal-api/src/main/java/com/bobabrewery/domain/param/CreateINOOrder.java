package com.bobabrewery.domain.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author PailieXiangLong
 */
@Data
public class CreateINOOrder {

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
    @NotNull(message = "not null")
    private Integer amount;

    /**
     * 合约地址
     *
     * @mock 0x123456789
     */
    @NotBlank(message = "not null")
    private String contractAddress;

}
