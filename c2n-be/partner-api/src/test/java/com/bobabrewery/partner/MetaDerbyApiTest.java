package com.bobabrewery.partner;

import com.bobabrewery.partner.api.MetaDerbyApi;
import com.bobabrewery.partner.resp.MetaDerbyRaceResp;
import com.bobabrewery.partner.resp.MetaDerbyRoomListResp;
import com.bobabrewery.partner.resp.MetaDerbyRoomResp;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @author PailieXiangLong
 */
@Slf4j
@SpringBootTest
public class MetaDerbyApiTest {

    @Resource
    private MetaDerbyApi metaDerbyApi;


    @Test
    public void MetaDerbyApiTest1() {
        MetaDerbyRoomListResp metaDerbyRoomListResp = metaDerbyApi.roomList();
        log.info("{}", metaDerbyRoomListResp);
    }


    @Test
    public void MetaDerbyApiTest2() {
        MetaDerbyRoomResp room = metaDerbyApi.room(5739);
        log.info("{}", room);
    }

    @Test
    public void MetaDerbyApiTest3() {
        MetaDerbyRaceResp race = metaDerbyApi.race(264);
        log.info("{}", race);
    }

    @Test
    public void MetaDerbyApiTest4() {
        MetaDerbyRoomListResp metaDerbyRoomListResp = metaDerbyApi.roomList();

        metaDerbyRoomListResp.getData().stream().filter(dataDTO -> dataDTO.getStatus() > 1);


        MetaDerbyRaceResp race = metaDerbyApi.race(264);
        log.info("{}", race);
    }


}
