package com.bobabrewery.repo.ino.domain;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * INO项目表
 *
 * @TableName brewery_ino_project
 */
@Data
public class InoProject implements Serializable {
    /**
     * id
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
     * NFT合约地址
     */
    private String nftContract;

    /**
     * 项目状态 0-未开始 1-白名单 2-publicSale 3-end
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
     * 链的chain_id
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
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    private static final long serialVersionUID = 1L;
}