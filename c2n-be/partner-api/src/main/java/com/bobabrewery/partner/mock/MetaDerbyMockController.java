package com.bobabrewery.partner.mock;

import com.bobabrewery.partner.api.MetaDerbyApi;
import com.bobabrewery.partner.resp.MetaDerbyRaceResp;
import com.bobabrewery.partner.resp.MetaDerbyRoomListResp;
import com.bobabrewery.partner.resp.MetaDerbyRoomResp;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * response
 * MetaDerbymock
 *
 * @author PailieXiangLong
 */
@RestController
public class MetaDerbyMockController {


    @Resource
    private ObjectMapper objectMapper;

    @Resource
    private MetaDerbyApi metaderbyApi;

    /**
     * race
     *
     * @param raceId
     * @return
     * @throws IOException
     */
    @GetMapping("/open_tournament/race")
    MetaDerbyRaceResp race(@RequestParam("race_id") Integer raceId) throws IOException {
        String s = loadClassPath(String.format("race_%d.json", raceId));
        return objectMapper.readValue(s, MetaDerbyRaceResp.class);
    }

    /**
     * room_list
     *
     * @return
     * @throws IOException
     */
    @GetMapping("/open_tournament/room_list")
    MetaDerbyRoomListResp roomList() throws IOException {
        String s = loadClassPath("room_list.json");
        return objectMapper.readValue(s, MetaDerbyRoomListResp.class);
    }

    /**
     * room
     *
     * @param roomId
     * @return
     * @throws IOException
     */
    @GetMapping("/open_tournament/room")
    MetaDerbyRoomResp room(@RequestParam("room_id") Integer roomId) throws IOException {
        String s = loadClassPath(String.format("room_%d.json", roomId));
        return objectMapper.readValue(s, MetaDerbyRoomResp.class);
    }

    /**
     * sync
     *
     * @return
     * @throws IOException
     */
    @GetMapping("/sync")
    String sync() throws IOException {

        MetaDerbyRoomListResp metaDerbyRoomListResp = metaderbyApi.roomList();
        ClassPathResource resource = new ClassPathResource("room_list.json");
        File file = resource.getFile();
        new FileOutputStream(file).write(
                objectMapper.writeValueAsString(metaDerbyRoomListResp).getBytes(StandardCharsets.UTF_8)
        );
        List<MetaDerbyRoomListResp.DataDTO> data = metaDerbyRoomListResp.getData();
        for (MetaDerbyRoomListResp.DataDTO datum : data) {
            Integer roomId = datum.getId();
            MetaDerbyRoomResp room = metaderbyApi.room(roomId);
            File file1 = new File("/home/orange/IdeaProjects/brewery/partner-api/target/classes/" + String.format("room_%d.json", roomId));
            new FileOutputStream(file1).write(
                    objectMapper.writeValueAsString(room).getBytes(StandardCharsets.UTF_8)
            );
            Integer raceId = datum.getRaceId();
            MetaDerbyRaceResp race = metaderbyApi.race(raceId);
            File raceFile = new File("/home/orange/IdeaProjects/brewery/partner-api/target/classes/" + String.format("race_%d.json", raceId));
            boolean raceFile1 = raceFile.createNewFile();
            new FileOutputStream(raceFile).write(
                    objectMapper.writeValueAsString(race).getBytes(StandardCharsets.UTF_8)
            );
        }
        return "OK";
    }

    private static String loadClassPath(String fileName) throws IOException {
        ClassPathResource resource = new ClassPathResource(fileName);
        InputStream inputStream = resource.getInputStream();
        StringBuilder builder = new StringBuilder();
        int i;
        while ((i = inputStream.read()) != -1) {
            builder.append((char) i);
        }
        inputStream.close();
        return builder.toString();
    }

}
