package com.bobabrewery.partner.api;

import com.bobabrewery.partner.api.fallback.MetaDerbyApiFallBack;
import com.bobabrewery.partner.resp.MetaDerbyRaceResp;
import com.bobabrewery.partner.resp.MetaDerbyRoomListResp;
import com.bobabrewery.partner.resp.MetaDerbyRoomResp;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author PailieXiangLong
 */
@Service
@FeignClient(name = "metaderbyApi", url = "${meta-derby.baseUrl}", fallback = MetaDerbyApiFallBack.class)
public interface MetaDerbyApi {

    @GetMapping("/open_tournament/race")
    MetaDerbyRaceResp race(@RequestParam("race_id") Integer raceId);


    @GetMapping("/open_tournament/room_list")
    MetaDerbyRoomListResp roomList();

    @GetMapping("/open_tournament/room")
    MetaDerbyRoomResp room(@RequestParam("room_id") Integer roomId);

}