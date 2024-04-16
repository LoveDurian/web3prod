package com.bobabrewery.partner.resp;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class MetaDerbyRaceResp {

    @JsonProperty("code")
    private Integer code;
    @JsonProperty("message")
    private String message;
    @JsonProperty("show_err")
    private Boolean showErr;
    @JsonProperty("timestamp")
    private Integer timestamp;
    @JsonProperty("data")
    private DataDTO data;

    @NoArgsConstructor
    @Data
    public static class DataDTO {
        @JsonProperty("race_id")
        private Integer raceId;
        @JsonProperty("status")
        private Integer status;
        @JsonProperty("race_name")
        private String raceName;
        @JsonProperty("racecourse")
        private String racecourse;
        @JsonProperty("distance")
        private Integer distance;
        @JsonProperty("race_url")
        private String raceUrl;
        @JsonProperty("start_at")
        private Long startAt;
        @JsonProperty("time_elapsed")
        private Double timeElapsed;
        @JsonProperty("prize_pool")
        private String prizePool;
        @JsonProperty("shortest_time_elapsed")
        private Double shortestTimeElapsed;
        @JsonProperty("ranking")
        private List<RankingDTO> ranking;

        @NoArgsConstructor
        @Data
        public static class RankingDTO {
            @JsonProperty("horse_id")
            private Integer horseId;
            @JsonProperty("user_id")
            private Integer userId;
            @JsonProperty("horse_name")
            private String horseName;
            @JsonProperty("time_elapsed")
            private Double timeElapsed;
            @JsonProperty("gate")
            private Integer gate;
            @JsonProperty("bloodLine")
            private String bloodLine;
            @JsonProperty("acceleration")
            private String acceleration;
            @JsonProperty("aggressiveness")
            private String aggressiveness;
            @JsonProperty("sprintSpeed")
            private String sprintSpeed;
            @JsonProperty("image_url")
            private String imageUrl;
            @JsonProperty("color")
            private ColorDTO color;
            @JsonProperty("award")
            private String award;
            @JsonProperty("rank")
            private Integer rank;
            @JsonProperty("wallet_addr")
            private String walletAddr;

            @NoArgsConstructor
            @Data
            public static class ColorDTO {
                @JsonProperty("R")
                private Double r;
                @JsonProperty("G")
                private Double g;
                @JsonProperty("B")
                private Double b;
            }
        }
    }
}
