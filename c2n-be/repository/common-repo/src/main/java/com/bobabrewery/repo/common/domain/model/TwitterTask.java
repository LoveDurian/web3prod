package com.bobabrewery.repo.common.domain.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author orange.cxl@qq.com
 * @date 2022-03-09 17:49:11
 */
@Data
public class TwitterTask implements Serializable {

    private static final long serialVersionUID = 4426644622384739176L;

    /**
     * ${item.comment}
     */
    private String accountId;

    /**
     * 项目ID  0-平台 >0 项目id
     */
    private Integer pid;

    /**
     * ${item.comment}
     */
    private Long twitterId;

    /**
     * ${item.comment}
     */
    private String twitterName;

    /**
     * ${item.comment}
     */
    private String retweetLink;

    /**
     * 0 未关注 1 已关注
     */
    private Integer follower;

    /**
     * 0 未转发 1 已转发
     */
    private Integer retweet;

    /**
     * ${item.comment}
     */
    private Date createTime;

    /**
     * ${item.comment}
     */
    private Date updateTime;


}