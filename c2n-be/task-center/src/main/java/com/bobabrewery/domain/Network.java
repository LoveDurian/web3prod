package com.bobabrewery.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * 区块网络地址
 *
 * @TableName brewery_network
 */
@Data
public class Network implements Serializable {
    /**
     *
     */
    private Integer id;

    /**
     *
     */
    private String name;

    /**
     * 网络地址
     */
    private String networkUrl;

    /**
     * ChainID
     */
    private Integer chainId;

    /**
     * 网络类型 0-主网 1-测试网
     */
    private Integer type;

    private static final long serialVersionUID = 1L;
}