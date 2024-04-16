package com.bobabrewery.repo.common.domain.model;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @TableName brewery_nft
 */
@Data
public class Nft implements Serializable {
    /**
     *
     */
    private Integer id;

    /**
     * NFT项目名
     */
    private String name;

    /**
     * 子标题
     */
    private String subtitle;

    /**
     * 项目Logo
     */
    private String logo;

    /**
     * 项目方名称
     */
    private String publisherName;

    /**
     * 项目方Logo
     */
    private String publisherLogo;

    /**
     * 简介
     */
    private String introduction;

    /**
     * 项目阶段
     */
    private Object steps;

    /**
     * Mint合约
     */
    private String mintContract;

    /**
     * 交易合约
     */
    private String nftContract;

    /**
     * 项目状态 -1 没开始信息不完整 0-信息完整未开始 1-白名单 2-brewerySale 3-publicSale
     */
    private Integer status;

    /**
     * 总数量
     */
    private Integer totalQuantity;

    /**
     * 当前价格
     */
    private Long currentPrice;

    /**
     * 单个NFT的价格 单位wei
     */
    private Long price;

    /**
     * 项目相关媒体
     */
    private Object medias;

    /**
     * 项目背景等参数
     */
    private Object background;

    /**
     *
     */
    private Integer chainId;

    /**
     * 截至时间
     */
    private LocalDateTime expirationTime;

    /**
     * IssueTime
     */
    private LocalDateTime issueTime;

    /**
     *
     */
    private LocalDateTime createTime;

    /**
     *
     */
    private LocalDateTime updateTime;

    private static final long serialVersionUID = 1L;
}