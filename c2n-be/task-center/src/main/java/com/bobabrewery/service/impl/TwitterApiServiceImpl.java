package com.bobabrewery.service.impl;

import com.bobabrewery.domain.TwitterFollowerCountInfo;
import com.bobabrewery.domain.TwitterFollowers;
import com.bobabrewery.domain.TwitterRetweet;
import com.bobabrewery.service.TwitterApiService;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;

/**
 * @author PailieXiangLong
 */
@Service
public class TwitterApiServiceImpl implements TwitterApiService {

    private final static String ACCESS_TOKEN = "Bearer AAAAAAAAAAAAAAAAAAAAANoVYAEAAAAAjmvRA7E3xwZ8xnBOsB3nvAGJutA%3DwlFCveITQpkKDFi5B5UBI3mMFZKcvQRIOMXI9Eud9wiq6N4390";

    private final static String BASE_URL = "https://api.twitter.com/2/";

    private WebClient webClient = WebClient.builder()
            .baseUrl(BASE_URL)
            .defaultHeader("Authorization", ACCESS_TOKEN)
            .build();

    private WebClient cdnClient = WebClient.create();

    @Override
    public TwitterFollowerCountInfo queryInfoByName(String twitterName, boolean publicMetrics) {
        return webClient.get()
                .uri(uriBuilder -> {
                    if (publicMetrics) {
                        uriBuilder.queryParam("user.fields", "public_metrics");
                    }
                    return uriBuilder.path("users/by/username/{twitterName}").build(twitterName);
                })
                .retrieve()
                .bodyToMono(TwitterFollowerCountInfo.class)
                .block();
    }


    @Override
    public TwitterFollowers queryFollowersById(String id, String nextToken) {
        return webClient.get()
                .uri(uriBuilder -> {
                    UriBuilder uriBuilder1 = uriBuilder.queryParam("max_results", 1000);
                    if (nextToken != null) {
                        uriBuilder1.queryParam("pagination_token", nextToken);
                    }
                    return uriBuilder1.path("users/{id}/followers").build(id);
                })
                .retrieve()
                .bodyToMono(TwitterFollowers.class)
                .block();
    }

    @Override
    public TwitterRetweet queryRetweetByTweetId(String tweetId, String nextToken) {
        return webClient.get().uri(uriBuilder -> {
            if (nextToken != null) {
                uriBuilder.queryParam("pagination_token", nextToken);
            }
            return uriBuilder.path("tweets/{tweetId}/retweeted_by").build(tweetId);
        }).retrieve().bodyToMono(TwitterRetweet.class).block();
    }
}
