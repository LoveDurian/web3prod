package com.bobabrewery.repo.common.domain.model;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author yunmengmeng
 * @since 2022-01-19
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UserRegisterProduct implements Serializable {

    /**
     * 钱包id
     */
    private String accountId;

    /**
     * 用户注册项目id
     */
    private Integer productId;

    /**
     * 用户质押金额
     */
    private String stakingAmount;

    /**
     * 用户质押金额后可得到amount数量
     */
    private String winAmount;

    /**
     * purchase
     */
    private String purchase;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    private String purchased;


}
