package com.bobabrewery.partner.resp;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class MetaDerbyRoomResp {

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
        @JsonProperty("room_horses")
        private List<RoomHorsesDTO> roomHorses;
        @JsonProperty("room")
        private RoomDTO room;
        @JsonProperty("race_id")
        private Integer raceId;

        @NoArgsConstructor
        @Data
        public static class RoomDTO {
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

        @NoArgsConstructor
        @Data
        public static class RoomHorsesDTO {
            @JsonProperty("horse_id")
            private Integer horseId;
            @JsonProperty("horse_name")
            private String horseName;
            @JsonProperty("bloodline")
            private String bloodline;
            @JsonProperty("acceleration")
            private String acceleration;
            @JsonProperty("aggressiveness")
            private String aggressiveness;
            @JsonProperty("sprint_speed")
            private String sprintSpeed;
            @JsonProperty("img_url")
            private String imgUrl;
            @JsonProperty("gate")
            private Integer gate;
        }
    }
}
