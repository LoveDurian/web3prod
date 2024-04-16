package com.bobabrewery.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author PailieXiangLong
 */
@NoArgsConstructor
@Data
public class TwitterFollowerCountInfo {

    /**
     * data
     */
    @JsonProperty("data")
    private DataDTO data;
    /**
     * errors
     */
    @JsonProperty("errors")
    private List<ErrorsDTO> errors;

    /**
     * DataDTO
     */
    @Data
    public static class DataDTO {
        /**
         * id
         */
        @JsonProperty("id")
        private String id;
        /**
         * publicMetrics
         */
        @JsonProperty("public_metrics")
        private PublicMetricsDTO publicMetrics;
        /**
         * name
         */
        @JsonProperty("name")
        private String name;
        /**
         * username
         */
        @JsonProperty("username")
        private String username;

        /**
         * PublicMetricsDTO
         */
        @NoArgsConstructor
        @Data
        public static class PublicMetricsDTO {
            /**
             * followersCount
             */
            @JsonProperty("followers_count")
            private Integer followersCount;
            /**
             * followingCount
             */
            @JsonProperty("following_count")
            private Integer followingCount;
            /**
             * tweetCount
             */
            @JsonProperty("tweet_count")
            private Integer tweetCount;
            /**
             * listedCount
             */
            @JsonProperty("listed_count")
            private Integer listedCount;
        }
    }


    /**
     * ErrorsDTO
     */
    @NoArgsConstructor
    @Data
    public static class ErrorsDTO {
        /**
         * value
         */
        @JsonProperty("value")
        private String value;
        /**
         * detail
         */
        @JsonProperty("detail")
        private String detail;
        /**
         * title
         */
        @JsonProperty("title")
        private String title;
        /**
         * resourceType
         */
        @JsonProperty("resource_type")
        private String resourceType;
        /**
         * parameter
         */
        @JsonProperty("parameter")
        private String parameter;
        /**
         * resourceId
         */
        @JsonProperty("resource_id")
        private String resourceId;
        /**
         * type
         */
        @JsonProperty("type")
        private String type;
    }
}
