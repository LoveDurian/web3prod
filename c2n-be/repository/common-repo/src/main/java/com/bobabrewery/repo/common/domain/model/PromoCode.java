package com.bobabrewery.repo.common.domain.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @TableName brewery_promo_code
 */
@Data
public class PromoCode implements Serializable {
    /**
     * ID
     */
    private Integer id;

    /**
     * 优惠码
     */
    private String promoCode;

    /**
     * 优惠减免
     */
    private Long discount;

    private static final long serialVersionUID = 1L;
}