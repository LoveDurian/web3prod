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
public class TwitterFollowers {

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

        /**
         * previousToken
         */
        @JsonProperty("previous_Token")
        private String previousToken;
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
}
