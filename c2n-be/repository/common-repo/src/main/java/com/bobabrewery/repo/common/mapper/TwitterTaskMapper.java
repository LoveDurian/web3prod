package com.bobabrewery.repo.common.mapper;

import com.bobabrewery.repo.common.domain.model.TwitterTask;

import java.util.List;


/**
 * DAO层
 *
 * @author orange.cxl@qq.com
 * @date 2022-03-09 17:48:17
 */
public interface TwitterTaskMapper {

    int create(TwitterTask twitterTask);

    TwitterTask findById(String id);

    TwitterTask findByAccountIdAndPid(String accountId, Integer pid);

    List<TwitterTask> findByIds(List<Long> ids);

    int update(TwitterTask twitterTask);

    int updateFollowerByTwitterId(Long twitterId);

    int updateRetweetByTwitterId(Long twitterId);

    int updateRetweetByTwitterIds(List<Long> ids);

    Long count(TwitterTask twitterTask);

    Integer countNotFollower();

    Integer countNotRetweet();

    List<TwitterTask> selectTwitterIdIsNull(Integer limit);

    int updateTwitterIdByTwitterName(Long twitterId, String twitterName);

    List<TwitterTask> list(TwitterTask twitterTask);
}