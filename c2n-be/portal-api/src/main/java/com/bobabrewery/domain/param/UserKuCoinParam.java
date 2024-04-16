package com.bobabrewery.domain.param;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author PailieXiangLong
 */
@Data
public class UserKuCoinParam {

    /**
     * 钱包地址
     *
     * @mock 0x123456789
     */
    @NotBlank(message = "not null")
    private String walletAddress;
    /**
     * kucoinId
     *
     * @mock 1245678
     */
    @NotNull(message = "not null")
    private Integer kucoinUid;

    /**
     * 邮件地址
     *
     * @mock 123@mail.com
     */
    @NotBlank(message = "not null")
    @Email
    private String email;

}
