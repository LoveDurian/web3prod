package com.bobabrewery.domain.param;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @author orange
 */
@Data
public class TicketParam {

    /**
     * 用户钱包地址
     *
     * @mock 0x0000123456789123456789123456789123456789
     */
    @NotBlank
    @Length(min = 42, max = 64)
    private String walletAddress;

    /**
     * 马匹Id
     *
     * @mock 1
     */
    @NotBlank(message = "not null")
    private String horsesId;
}
