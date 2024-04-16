package com.bobabrewery.domain.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author PailieXiangLong
 */
@Data
public class CreateClaim {
    /**
     * 钱包地址
     *
     * @mock 0x615fDC569f5FF6Fc832d5968f73f917F13471200
     */
    @NotBlank(message = "not null")
    private String walletAddress;

    /**
     * 合约地址
     *
     * @mock 0x615fDC569f5FF6Fc832d5968f73f917F13471200
     */
    @NotBlank(message = "not null")
    private String contractAddress;
}
