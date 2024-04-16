package com.bobabrewery.partner.resp;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class MetaDerbyRoomListResp {

    @JsonProperty("code")
    private Integer code;
    @JsonProperty("message")
    private String message;
    @JsonProperty("show_err")
    private Boolean showErr;
    @JsonProperty("timestamp")
    private Integer timestamp;
    @JsonProperty("data")
    private List<DataDTO> data;

    @NoArgsConstructor
    @Data
    public static class DataDTO {
        @JsonProperty("id")
        private Integer id;
        @JsonProperty("capacity")
        private Integer capacity;
        @JsonProperty("current_cap")
        private Integer currentCap;
        @JsonProperty("type")
        private Integer type;
        @JsonProperty("start_time")
        private Integer startTime;
        @JsonProperty("end_time")
        private Integer endTime;
        @JsonProperty("status")
        private Integer status;
        @JsonProperty("created_at")
        private String createdAt;
        @JsonProperty("updated_at")
        private String updatedAt;
        @JsonProperty("prize_pool")
        private String prizePool;
        @JsonProperty("race_id")
        private Integer raceId;
    }
}
