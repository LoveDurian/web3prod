package com.bobabrewery.service;

import com.bobabrewery.domain.TwitterFollowerCountInfo;
import com.bobabrewery.domain.TwitterFollowers;
import com.bobabrewery.domain.TwitterRetweet;

/**
 * @author PailieXiangLong
 */
public interface TwitterApiService {
    /**
     * 根据Twitter用户名查询follower信息
     *
     * @param twitterName   twitterName
     * @param publicMetrics 带follower信息
     * @return
     */
    TwitterFollowerCountInfo queryInfoByName(String twitterName, boolean publicMetrics);


    /**
     * 查询用户的粉丝信息
     *
     * @param id
     * @param nextToken
     * @return
     */
    TwitterFollowers queryFollowersById(String id, String nextToken);

    /**
     * 查询tweet的转发量
     *
     * @param tweetId
     * @return
     */
    TwitterRetweet queryRetweetByTweetId(String tweetId, String nextToken);
}
