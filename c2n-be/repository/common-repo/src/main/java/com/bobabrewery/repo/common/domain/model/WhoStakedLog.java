package com.bobabrewery.repo.common.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @TableName who_staked_log
 */
@Data
public class WhoStakedLog implements Serializable {
    /**
     * ID
     */
    @JsonIgnore
    private Long id;

    /**
     * 钱包地址
     */
    private String walletAddress;

    /**
     * 合约地址
     */
    @JsonIgnore
    private String contractAddress;

    /**
     * stake时的IP
     */
    @JsonIgnore
    private String ip;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private static final long serialVersionUID = 1L;
}