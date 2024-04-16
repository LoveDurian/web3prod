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
public class TwitterRetweet {


    /**
     * data
     */
    @JsonProperty("data")
    private List<DataDTO> data;
    /**
     * meta
     */
    @JsonProperty("meta")
    private MetaDTO meta;
    /**
     * errors
     */
    @JsonProperty("errors")
    private List<ErrorsDTO> errors;

    /**
     * MetaDTO
     */
    @NoArgsConstructor
    @Data
    public static class MetaDTO {
        /**
         * resultCount
         */
        @JsonProperty("result_count")
        private Integer resultCount;
        /**
         * nextToken
         */
        @JsonProperty("next_token")
        private String nextToken;
    }

    /**
     * DataDTO
     */
    @NoArgsConstructor
    @Data
    public static class DataDTO {
        /**
         * id
         */
        @JsonProperty("id")
        private String id;
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
