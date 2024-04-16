package com.bobabrewery.job;

import com.bobabrewery.domain.TwitterFollowerCountInfo;
import com.bobabrewery.domain.TwitterFollowers;
import com.bobabrewery.domain.TwitterRetweet;
import com.bobabrewery.repo.common.domain.model.Project;
import com.bobabrewery.repo.common.domain.model.TwitterTask;
import com.bobabrewery.repo.common.mapper.TwitterTaskMapper;
import com.bobabrewery.service.IProjectService;
import com.bobabrewery.service.TwitterApiService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author PailieXiangLong
 */
@Slf4j
@Data
@Component
public class TwitterJob {

    @Resource
    private TwitterApiService twitterApiService;

    @Resource
    private IProjectService productContractService;

    @Resource
    private TwitterTaskMapper twitterTaskMapper;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * boba_brewery TwitterID
     */
    private static final String twitterId = "1474222235279925248";
    private static final String tweetId = "1516018559192862722";

    /**
     * 下一页缓存的token
     */
    private static final String FOLLOWER_PAGE_TOKEN = "follower_twitter_next_token";

    private static final String RETWEET_PAGE_TOKEN = "retweet_twitter_next_token";

    /**
     * 根据TwitterName查出follower数量
     */
    @Scheduled(cron = "0 0/10 * * * ?")
    void updateInfo() {
        List<Project> list = productContractService.list();
        for (Project project : list) {
            if (project.getTwitterName() != null) {
                log.info("[更新 {} 的关注数量]", project.getTwitterName());
                TwitterFollowerCountInfo twitterFollowerCountInfo = twitterApiService.queryInfoByName(project.getTwitterName(), true);
                if (twitterFollowerCountInfo.getData() != null) {
                    project.setFollower(twitterFollowerCountInfo.getData().getPublicMetrics().getFollowersCount());
                    productContractService.updateById(project);
                } else {
                    for (TwitterFollowerCountInfo.ErrorsDTO error : twitterFollowerCountInfo.getErrors()) {
                        log.error("[更新 {} 的关注数量]:{}", project.getTwitterName(), error.getDetail());
                    }
                }
            }
        }
    }


    /**
     * 每30s更新5条twitterID信息
     */
    @Scheduled(cron = "0 0/1 * * * ?")
    void updateUserTwitterID() {
        // 每次查出5条
        List<TwitterTask> twitterTasks = twitterTaskMapper.selectTwitterIdIsNull(5);
        List<String> twitters = twitterTasks.stream().map(TwitterTask::getTwitterName).collect(Collectors.toList());
        log.info("[根据twitterName更新用户的twitterId]:{}个:{}", twitterTasks.size(), twitters);
        for (TwitterTask userInfo : twitterTasks) {
            try {
                TwitterFollowerCountInfo twitterFollowerCountInfo = twitterApiService.queryInfoByName(userInfo.getTwitterName().trim().replace(" ", ""), false);
                if (twitterFollowerCountInfo.getData() != null) {
                    String id = twitterFollowerCountInfo.getData().getId();
                    twitterTaskMapper.updateTwitterIdByTwitterName(Long.valueOf(id), userInfo.getTwitterName());
                } else {
                    twitterTaskMapper.updateTwitterIdByTwitterName(0L, userInfo.getTwitterName());
                    List<String> errors = twitterFollowerCountInfo.getErrors().stream().map(TwitterFollowerCountInfo.ErrorsDTO::getDetail).collect(Collectors.toList());
                    log.error("[根据twitterName更新用户的twitterId]:error{}", errors);
                }
            } catch (Exception e) {
                twitterTaskMapper.updateTwitterIdByTwitterName(0L, userInfo.getTwitterName());
                log.error("[根据twitterName更新用户的twitterId]:error:", e);
            }
        }
    }


    /**
     * 获取并更新boba_brewery的粉丝信息
     */
    @Scheduled(cron = "0 0/2 * * * ?")
    void updateUserFollowers() {
        String nextToken = (String) redisTemplate.opsForValue().get(FOLLOWER_PAGE_TOKEN);
        Integer notFollower = twitterTaskMapper.countNotFollower();
        // 没有关注的用户人数大于0
        if (notFollower > 0) {
            TwitterFollowers twitterFollowers = null;
            try {
                if (nextToken != null) {
                    twitterFollowers = twitterApiService.queryFollowersById(twitterId, nextToken);
                } else {
                    twitterFollowers = twitterApiService.queryFollowersById(twitterId, null);
                }
            } catch (Exception e) {
                redisTemplate.delete(FOLLOWER_PAGE_TOKEN);
                log.error("[获取boba_brewery的粉丝信息]失败", e);
            }

            if (twitterFollowers != null && twitterFollowers.getData() != null) {
                // 查询返回数量
                Integer resultCount = twitterFollowers.getMeta().getResultCount();
                String nextPageToken = twitterFollowers.getMeta().getNextToken();
                // 存在下一页
                if (resultCount == 1000 && nextPageToken != null) {
                    redisTemplate.opsForValue().set(FOLLOWER_PAGE_TOKEN, nextPageToken);
                } else {
                    redisTemplate.delete(FOLLOWER_PAGE_TOKEN);
                }
                List<TwitterFollowers.DataDTO> data = twitterFollowers.getData();
                for (TwitterFollowers.DataDTO datum : data) {
                    twitterTaskMapper.updateFollowerByTwitterId(Long.valueOf(datum.getId()));
                }
                log.info("[更新boba_brewery的粉丝信息]更新人数:{}", data.size());
            }
        }
    }

    @Scheduled(cron = "0 0/2 * * * ?")
    void updateUserRetweets() {
        String nextToken = (String) redisTemplate.opsForValue().get(RETWEET_PAGE_TOKEN);
        Integer integer = twitterTaskMapper.countNotRetweet();
        if (integer > 0) {
            TwitterRetweet retweet = null;
            try {
                if (nextToken != null) {
                    retweet = twitterApiService.queryRetweetByTweetId(tweetId, nextToken);
                } else {
                    retweet = twitterApiService.queryRetweetByTweetId(tweetId, null);
                }
            } catch (Exception e) {
                redisTemplate.delete(RETWEET_PAGE_TOKEN);
                log.error("[更新boba_brewery的转发信息]失败", e);
            }

            if (retweet != null && retweet.getData() != null) {
                Integer resultCount = retweet.getMeta().getResultCount();
                String nextPageToken = retweet.getMeta().getNextToken();
                // 存在下一页
                if (resultCount == 1000 && nextPageToken != null) {
                    redisTemplate.opsForValue().set(RETWEET_PAGE_TOKEN, nextPageToken);
                } else {
                    redisTemplate.delete(RETWEET_PAGE_TOKEN);
                }
                List<TwitterRetweet.DataDTO> data = retweet.getData();
                List<Long> collect = data.stream()
                        .map(dataDTO -> Long.valueOf(dataDTO.getId()))
                        .collect(Collectors.toList());
                int i = twitterTaskMapper.updateRetweetByTwitterIds(collect);
                log.info("[更新boba_brewery的转发信息]更新人数:{}", i);

            } else {
                List<TwitterRetweet.ErrorsDTO> errors = retweet.getErrors();
                for (TwitterRetweet.ErrorsDTO error : errors) {
                    log.error("[更新boba_brewery的转发信息]失败:{}", error.getDetail());
                }
            }

        }


    }

}
