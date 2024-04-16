package com.bobabrewery.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author PailieXiangLong
 */
@NoArgsConstructor
@Data
public class TwitterFollowerCDNInfo {

    /**
     * following
     */
    @JsonProperty("following")
    private Boolean following;
    /**
     * id
     */
    @JsonProperty("id")
    private String id;
    /**
     * screenName
     */
    @JsonProperty("screen_name")
    private String screenName;
    /**
     * name
     */
    @JsonProperty("name")
    private String name;
    /**
     * protectedX
     */
    @JsonProperty("protected")
    private Boolean protectedX;
    /**
     * followersCount
     */
    @JsonProperty("followers_count")
    private Integer followersCount;
    /**
     * formattedFollowersCount
     */
    @JsonProperty("formatted_followers_count")
    private String formattedFollowersCount;
    /**
     * ageGated
     */
    @JsonProperty("age_gated")
    private Boolean ageGated;
}
