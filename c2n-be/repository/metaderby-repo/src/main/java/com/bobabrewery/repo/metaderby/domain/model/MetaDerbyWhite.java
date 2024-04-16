package com.bobabrewery.repo.metaderby.domain.model;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @TableName brewery_meta_derby_white
 */
@Data
public class MetaDerbyWhite implements Serializable {
    /**
     * ID
     */
    private Long id;

    /**
     * 钱包地址
     */
    private String walletAddress;

    /**
     * 使用10U的马匹ID
     */
    private Integer horses;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    private static final long serialVersionUID = 1L;
}