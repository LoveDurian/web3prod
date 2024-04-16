package com.bobabrewery.partner.api.fallback;

import com.bobabrewery.partner.api.MetaDerbyApi;
import com.bobabrewery.partner.resp.MetaDerbyRaceResp;
import com.bobabrewery.partner.resp.MetaDerbyRoomListResp;
import com.bobabrewery.partner.resp.MetaDerbyRoomResp;

/**
 * @author PailieXiangLong
 */
public class MetaDerbyApiFallBack implements MetaDerbyApi {
    @Override
    public MetaDerbyRaceResp race(Integer raceId) {
        MetaDerbyRaceResp resp = new MetaDerbyRaceResp();
        resp.setCode(404);
        return resp;
    }

    @Override
    public MetaDerbyRoomListResp roomList() {
        MetaDerbyRoomListResp resp = new MetaDerbyRoomListResp();
        resp.setCode(404);
        return resp;
    }

    @Override
    public MetaDerbyRoomResp room(Integer roomId) {
        MetaDerbyRoomResp resp = new MetaDerbyRoomResp();
        resp.setCode(404);
        return resp;
    }
}
