package com.bobabrewery;

import com.alibaba.fastjson.JSON;
import com.bobabrewery.job.MetaDerbyJobs;
import com.bobabrewery.partner.api.MetaDerbyApi;
import com.bobabrewery.partner.resp.MetaDerbyRoomListResp;
import com.bobabrewery.partner.resp.MetaDerbyRoomResp;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.web3j.utils.Convert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author PailieXiangLong
 */
@Slf4j
@ExtendWith({SpringExtension.class})
public class TaskAppTest {

    @MockBean
    private MetaDerbyJobs metaDerbyJobs;

    @MockBean
    private MetaDerbyApi metaderbyApi;

    public static List<Integer> splitInteger(int n, int sum, int m) {
        //随机抽取n-1个小于sum的数
        List<Integer> list = new ArrayList();
        //将0和sum加入到里list中
        list.add(0);
        //每个数至少为m
        sum = sum - n * m;
        list.add(sum);

        //生成n-1个小于sum的随机数
        int temp = 0;
        for (int i = 0; i < n - 1; i++) {
            temp = (int) (Math.random() * sum);
            list.add(temp);
        }
        Collections.sort(list);
        List<Integer> nums = new ArrayList();
        for (int i = 0; i < n; i++) {
            //每份最少为m
            nums.add(list.get(i + 1) - list.get(i) + m);

        }
        return nums;
    }


    @BeforeEach
    public void mockData() throws ExecutionException, InterruptedException {
        Random mockRandom = mock(Random.class);

        when(mockRandom.nextInt()).thenReturn(1, 2, 3);

        Mockito.doReturn(Convert.toWei("20000", Convert.Unit.ETHER).toBigInteger()).when(metaDerbyJobs).totalStakes();

        when(metaDerbyJobs.totalStakeByIdStr(any())).thenReturn(
                Convert.toWei("1438", Convert.Unit.ETHER).toBigInteger(),
                Convert.toWei("269", Convert.Unit.ETHER).toBigInteger(),
                Convert.toWei("1775", Convert.Unit.ETHER).toBigInteger(),
                Convert.toWei("708", Convert.Unit.ETHER).toBigInteger(),
                Convert.toWei("452", Convert.Unit.ETHER).toBigInteger(),
                Convert.toWei("3915", Convert.Unit.ETHER).toBigInteger(),
                Convert.toWei("3705", Convert.Unit.ETHER).toBigInteger(),
                Convert.toWei("2484", Convert.Unit.ETHER).toBigInteger(),
                Convert.toWei("454", Convert.Unit.ETHER).toBigInteger(),
                Convert.toWei("945", Convert.Unit.ETHER).toBigInteger(),
                Convert.toWei("1878", Convert.Unit.ETHER).toBigInteger(),
                Convert.toWei("1977", Convert.Unit.ETHER).toBigInteger()
        );


        String roomListJson = "{\"code\":0,\"message\":\"success\",\"show_err\":false,\"timestamp\":1659062392,\"data\":[{\"id\":5074,\"capacity\":12,\"current_cap\":12,\"type\":2,\"start_time\":1659060000,\"end_time\":1659063000,\"status\":1,\"created_at\":\"2022-07-29T02:00:00.078Z\",\"updated_at\":\"2022-07-29T02:33:00.406Z\",\"prize_pool\":\"10000000000000000000000\",\"race_id\":169},{\"id\":5068,\"capacity\":12,\"current_cap\":12,\"type\":2,\"start_time\":1659056400,\"end_time\":1659059400,\"status\":4,\"created_at\":\"2022-07-29T01:00:00.372Z\",\"updated_at\":\"2022-07-29T01:51:42.943Z\",\"prize_pool\":\"10000000000000000000000\",\"race_id\":168},{\"id\":5060,\"capacity\":12,\"current_cap\":12,\"type\":2,\"start_time\":1659052800,\"end_time\":1659055800,\"status\":4,\"created_at\":\"2022-07-29T00:00:00.126Z\",\"updated_at\":\"2022-07-29T00:51:42.808Z\",\"prize_pool\":\"10000000000000000000000\",\"race_id\":167},{\"id\":5054,\"capacity\":12,\"current_cap\":12,\"type\":2,\"start_time\":1659049200,\"end_time\":1659052200,\"status\":4,\"created_at\":\"2022-07-28T23:00:00.372Z\",\"updated_at\":\"2022-07-28T23:51:43.53Z\",\"prize_pool\":\"10000000000000000000000\",\"race_id\":166},{\"id\":5046,\"capacity\":12,\"current_cap\":12,\"type\":2,\"start_time\":1659045600,\"end_time\":1659048600,\"status\":4,\"created_at\":\"2022-07-28T22:00:00.255Z\",\"updated_at\":\"2022-07-28T22:51:43.543Z\",\"prize_pool\":\"10000000000000000000000\",\"race_id\":165}]}";
        MetaDerbyRoomListResp metaDerbyRoomListResp = JSON.parseObject(roomListJson, MetaDerbyRoomListResp.class);
        Mockito.doReturn(metaDerbyRoomListResp).when(metaderbyApi).roomList();

        String room5074 = "{\"code\":0,\"message\":\"success\",\"show_err\":false,\"timestamp\":1659065078,\"data\":{\"room_horses\":[{\"horse_name\":\"87172125830\",\"bloodline\":\"Count Fleet\",\"acceleration\":\"Rare\",\"aggressiveness\":\"Elite\",\"sprint_speed\":\"Epic\",\"img_url\":\"https://d2vvute2v3y7pn.cloudfront.net/NFT/normal/normal_%23CCFF33.png\"},{\"horse_name\":\"87172125827\",\"bloodline\":\"Citation\",\"acceleration\":\"Legendary\",\"aggressiveness\":\"Epic\",\"sprint_speed\":\"Elite\",\"img_url\":\"https://d2vvute2v3y7pn.cloudfront.net/NFT/normal/normal_%23153E66.png\"},{\"horse_name\":\"87172125829\",\"bloodline\":\"Count Fleet\",\"acceleration\":\"Rare\",\"aggressiveness\":\"Epic\",\"sprint_speed\":\"Legendary\",\"img_url\":\"https://d2vvute2v3y7pn.cloudfront.net/NFT/normal/normal_%234D9900.png\"},{\"horse_name\":\"87172125824\",\"bloodline\":\"Standardbreds\",\"acceleration\":\"Epic\",\"aggressiveness\":\"Epic\",\"sprint_speed\":\"Pacer\",\"img_url\":\"https://d2vvute2v3y7pn.cloudfront.net/NFT/normal/normal_%23FFCC33.png\"},{\"horse_name\":\"87172125835\",\"bloodline\":\"Standardbreds\",\"acceleration\":\"Epic\",\"aggressiveness\":\"Epic\",\"sprint_speed\":\"Rare\",\"img_url\":\"https://d2vvute2v3y7pn.cloudfront.net/NFT/normal/normal_%23663E52.png\"},{\"horse_name\":\"87172125833\",\"bloodline\":\"Citation\",\"acceleration\":\"Legendary\",\"aggressiveness\":\"Rare\",\"sprint_speed\":\"Elite\",\"img_url\":\"https://d2vvute2v3y7pn.cloudfront.net/NFT/normal/normal_%238DFF66.png\"},{\"horse_name\":\"87172125832\",\"bloodline\":\"Citation\",\"acceleration\":\"Rare\",\"aggressiveness\":\"Pacer\",\"sprint_speed\":\"Elite\",\"img_url\":\"https://d2vvute2v3y7pn.cloudfront.net/NFT/normal/normal_%23CCFF99.png\"},{\"horse_name\":\"87172125831\",\"bloodline\":\"Count Fleet\",\"acceleration\":\"Elite\",\"aggressiveness\":\"Legendary\",\"sprint_speed\":\"Elite\",\"img_url\":\"https://d2vvute2v3y7pn.cloudfront.net/NFT/normal/normal_%235C995C.png\"},{\"horse_name\":\"bfb1b825707\",\"bloodline\":\"Standardbreds\",\"acceleration\":\"Epic\",\"aggressiveness\":\"Rare\",\"sprint_speed\":\"Rare\",\"img_url\":\"https://d2vvute2v3y7pn.cloudfront.net/NFT/normal/normal_%237BCC7B.png\"},{\"horse_name\":\"87172125825\",\"bloodline\":\"Citation\",\"acceleration\":\"Rare\",\"aggressiveness\":\"Rare\",\"sprint_speed\":\"Elite\",\"img_url\":\"https://d2vvute2v3y7pn.cloudfront.net/NFT/normal/normal_%23004D66.png\"},{\"horse_name\":\"87172125828\",\"bloodline\":\"Citation\",\"acceleration\":\"Pacer\",\"aggressiveness\":\"Pacer\",\"sprint_speed\":\"Legendary\",\"img_url\":\"https://d2vvute2v3y7pn.cloudfront.net/NFT/normal/normal_%2329CC7B.png\"},{\"horse_name\":\"87172125826\",\"bloodline\":\"Standardbreds\",\"acceleration\":\"Rare\",\"aggressiveness\":\"Rare\",\"sprint_speed\":\"Epic\",\"img_url\":\"https://d2vvute2v3y7pn.cloudfront.net/NFT/normal/normal_%23FF33FF.png\"}],\"room\":{\"id\":5074,\"capacity\":12,\"current_cap\":12,\"type\":2,\"start_time\":1659060000,\"end_time\":1659063000,\"status\":4,\"created_at\":\"2022-07-29T02:00:00.078Z\",\"updated_at\":\"2022-07-29T02:51:43.091Z\",\"prize_pool\":\"10000000000000000000000\",\"race_id\":169},\"race_id\":169}}";
        MetaDerbyRoomResp metaDerbyRoomResp5074 = JSON.parseObject(room5074, MetaDerbyRoomResp.class);
        Mockito.doReturn(metaDerbyRoomResp5074).when(metaderbyApi).room(5074);

        String room5068 = "{\"code\":0,\"message\":\"success\",\"show_err\":false,\"timestamp\":1659064494,\"data\":{\"room_horses\":[{\"horse_name\":\"87172125834\",\"bloodline\":\"Count Fleet\",\"acceleration\":\"Epic\",\"aggressiveness\":\"Rare\",\"sprint_speed\":\"Pacer\",\"img_url\":\"https://d2vvute2v3y7pn.cloudfront.net/NFT/normal/normal_%23CC297B.png\"},{\"horse_name\":\"87172125828\",\"bloodline\":\"Citation\",\"acceleration\":\"Pacer\",\"aggressiveness\":\"Pacer\",\"sprint_speed\":\"Legendary\",\"img_url\":\"https://d2vvute2v3y7pn.cloudfront.net/NFT/normal/normal_%2329CC7B.png\"},{\"horse_name\":\"87172125824\",\"bloodline\":\"Standardbreds\",\"acceleration\":\"Epic\",\"aggressiveness\":\"Epic\",\"sprint_speed\":\"Pacer\",\"img_url\":\"https://d2vvute2v3y7pn.cloudfront.net/NFT/normal/normal_%23FFCC33.png\"},{\"horse_name\":\"87172125827\",\"bloodline\":\"Citation\",\"acceleration\":\"Legendary\",\"aggressiveness\":\"Epic\",\"sprint_speed\":\"Elite\",\"img_url\":\"https://d2vvute2v3y7pn.cloudfront.net/NFT/normal/normal_%23153E66.png\"},{\"horse_name\":\"87172125835\",\"bloodline\":\"Standardbreds\",\"acceleration\":\"Epic\",\"aggressiveness\":\"Epic\",\"sprint_speed\":\"Rare\",\"img_url\":\"https://d2vvute2v3y7pn.cloudfront.net/NFT/normal/normal_%23663E52.png\"},{\"horse_name\":\"87172125825\",\"bloodline\":\"Citation\",\"acceleration\":\"Rare\",\"aggressiveness\":\"Rare\",\"sprint_speed\":\"Elite\",\"img_url\":\"https://d2vvute2v3y7pn.cloudfront.net/NFT/normal/normal_%23004D66.png\"},{\"horse_name\":\"87172125833\",\"bloodline\":\"Citation\",\"acceleration\":\"Legendary\",\"aggressiveness\":\"Rare\",\"sprint_speed\":\"Elite\",\"img_url\":\"https://d2vvute2v3y7pn.cloudfront.net/NFT/normal/normal_%238DFF66.png\"},{\"horse_name\":\"87172125826\",\"bloodline\":\"Standardbreds\",\"acceleration\":\"Rare\",\"aggressiveness\":\"Rare\",\"sprint_speed\":\"Epic\",\"img_url\":\"https://d2vvute2v3y7pn.cloudfront.net/NFT/normal/normal_%23FF33FF.png\"},{\"horse_name\":\"87172125829\",\"bloodline\":\"Count Fleet\",\"acceleration\":\"Rare\",\"aggressiveness\":\"Epic\",\"sprint_speed\":\"Legendary\",\"img_url\":\"https://d2vvute2v3y7pn.cloudfront.net/NFT/normal/normal_%234D9900.png\"},{\"horse_name\":\"87172125831\",\"bloodline\":\"Count Fleet\",\"acceleration\":\"Elite\",\"aggressiveness\":\"Legendary\",\"sprint_speed\":\"Elite\",\"img_url\":\"https://d2vvute2v3y7pn.cloudfront.net/NFT/normal/normal_%235C995C.png\"},{\"horse_name\":\"87172125830\",\"bloodline\":\"Count Fleet\",\"acceleration\":\"Rare\",\"aggressiveness\":\"Elite\",\"sprint_speed\":\"Epic\",\"img_url\":\"https://d2vvute2v3y7pn.cloudfront.net/NFT/normal/normal_%23CCFF33.png\"},{\"horse_name\":\"87172125832\",\"bloodline\":\"Citation\",\"acceleration\":\"Rare\",\"aggressiveness\":\"Pacer\",\"sprint_speed\":\"Elite\",\"img_url\":\"https://d2vvute2v3y7pn.cloudfront.net/NFT/normal/normal_%23CCFF99.png\"}],\"room\":{\"id\":5068,\"capacity\":12,\"current_cap\":12,\"type\":2,\"start_time\":1659056400,\"end_time\":1659059400,\"status\":4,\"created_at\":\"2022-07-29T01:00:00.372Z\",\"updated_at\":\"2022-07-29T01:51:42.943Z\",\"prize_pool\":\"10000000000000000000000\",\"race_id\":168},\"race_id\":168}}";
        MetaDerbyRoomResp metaDerbyRoomResp5068 = JSON.parseObject(room5068, MetaDerbyRoomResp.class);
        Mockito.doReturn(metaDerbyRoomResp5068).when(metaderbyApi).room(5068);

        String room5060 = "{\"code\":0,\"message\":\"success\",\"show_err\":false,\"timestamp\":1659064812,\"data\":{\"room_horses\":[{\"horse_name\":\"87172125825\",\"bloodline\":\"Citation\",\"acceleration\":\"Rare\",\"aggressiveness\":\"Rare\",\"sprint_speed\":\"Elite\",\"img_url\":\"https://d2vvute2v3y7pn.cloudfront.net/NFT/normal/normal_%23004D66.png\"},{\"horse_name\":\"87172125827\",\"bloodline\":\"Citation\",\"acceleration\":\"Legendary\",\"aggressiveness\":\"Epic\",\"sprint_speed\":\"Elite\",\"img_url\":\"https://d2vvute2v3y7pn.cloudfront.net/NFT/normal/normal_%23153E66.png\"},{\"horse_name\":\"87172125834\",\"bloodline\":\"Count Fleet\",\"acceleration\":\"Epic\",\"aggressiveness\":\"Rare\",\"sprint_speed\":\"Pacer\",\"img_url\":\"https://d2vvute2v3y7pn.cloudfront.net/NFT/normal/normal_%23CC297B.png\"},{\"horse_name\":\"87172125830\",\"bloodline\":\"Count Fleet\",\"acceleration\":\"Rare\",\"aggressiveness\":\"Elite\",\"sprint_speed\":\"Epic\",\"img_url\":\"https://d2vvute2v3y7pn.cloudfront.net/NFT/normal/normal_%23CCFF33.png\"},{\"horse_name\":\"87172125835\",\"bloodline\":\"Standardbreds\",\"acceleration\":\"Epic\",\"aggressiveness\":\"Epic\",\"sprint_speed\":\"Rare\",\"img_url\":\"https://d2vvute2v3y7pn.cloudfront.net/NFT/normal/normal_%23663E52.png\"},{\"horse_name\":\"87172125831\",\"bloodline\":\"Count Fleet\",\"acceleration\":\"Elite\",\"aggressiveness\":\"Legendary\",\"sprint_speed\":\"Elite\",\"img_url\":\"https://d2vvute2v3y7pn.cloudfront.net/NFT/normal/normal_%235C995C.png\"},{\"horse_name\":\"87172125826\",\"bloodline\":\"Standardbreds\",\"acceleration\":\"Rare\",\"aggressiveness\":\"Rare\",\"sprint_speed\":\"Epic\",\"img_url\":\"https://d2vvute2v3y7pn.cloudfront.net/NFT/normal/normal_%23FF33FF.png\"},{\"horse_name\":\"87172125833\",\"bloodline\":\"Citation\",\"acceleration\":\"Legendary\",\"aggressiveness\":\"Rare\",\"sprint_speed\":\"Elite\",\"img_url\":\"https://d2vvute2v3y7pn.cloudfront.net/NFT/normal/normal_%238DFF66.png\"},{\"horse_name\":\"87172125824\",\"bloodline\":\"Standardbreds\",\"acceleration\":\"Epic\",\"aggressiveness\":\"Epic\",\"sprint_speed\":\"Pacer\",\"img_url\":\"https://d2vvute2v3y7pn.cloudfront.net/NFT/normal/normal_%23FFCC33.png\"},{\"horse_name\":\"87172125828\",\"bloodline\":\"Citation\",\"acceleration\":\"Pacer\",\"aggressiveness\":\"Pacer\",\"sprint_speed\":\"Legendary\",\"img_url\":\"https://d2vvute2v3y7pn.cloudfront.net/NFT/normal/normal_%2329CC7B.png\"},{\"horse_name\":\"87172125829\",\"bloodline\":\"Count Fleet\",\"acceleration\":\"Rare\",\"aggressiveness\":\"Epic\",\"sprint_speed\":\"Legendary\",\"img_url\":\"https://d2vvute2v3y7pn.cloudfront.net/NFT/normal/normal_%234D9900.png\"},{\"horse_name\":\"87172125832\",\"bloodline\":\"Citation\",\"acceleration\":\"Rare\",\"aggressiveness\":\"Pacer\",\"sprint_speed\":\"Elite\",\"img_url\":\"https://d2vvute2v3y7pn.cloudfront.net/NFT/normal/normal_%23CCFF99.png\"}],\"room\":{\"id\":5060,\"capacity\":12,\"current_cap\":12,\"type\":2,\"start_time\":1659052800,\"end_time\":1659055800,\"status\":4,\"created_at\":\"2022-07-29T00:00:00.126Z\",\"updated_at\":\"2022-07-29T00:51:42.808Z\",\"prize_pool\":\"10000000000000000000000\",\"race_id\":167},\"race_id\":167}}";
        MetaDerbyRoomResp metaDerbyRoomResp5060 = JSON.parseObject(room5060, MetaDerbyRoomResp.class);
        Mockito.doReturn(metaDerbyRoomResp5060).when(metaderbyApi).room(5060);

        String room5054 = "{\"code\":0,\"message\":\"success\",\"show_err\":false,\"timestamp\":1659064890,\"data\":{\"room_horses\":[{\"horse_name\":\"87172125832\",\"bloodline\":\"Citation\",\"acceleration\":\"Rare\",\"aggressiveness\":\"Pacer\",\"sprint_speed\":\"Elite\",\"img_url\":\"https://d2vvute2v3y7pn.cloudfront.net/NFT/normal/normal_%23CCFF99.png\"},{\"horse_name\":\"87172125834\",\"bloodline\":\"Count Fleet\",\"acceleration\":\"Epic\",\"aggressiveness\":\"Rare\",\"sprint_speed\":\"Pacer\",\"img_url\":\"https://d2vvute2v3y7pn.cloudfront.net/NFT/normal/normal_%23CC297B.png\"},{\"horse_name\":\"87172125827\",\"bloodline\":\"Citation\",\"acceleration\":\"Legendary\",\"aggressiveness\":\"Epic\",\"sprint_speed\":\"Elite\",\"img_url\":\"https://d2vvute2v3y7pn.cloudfront.net/NFT/normal/normal_%23153E66.png\"},{\"horse_name\":\"87172125826\",\"bloodline\":\"Standardbreds\",\"acceleration\":\"Rare\",\"aggressiveness\":\"Rare\",\"sprint_speed\":\"Epic\",\"img_url\":\"https://d2vvute2v3y7pn.cloudfront.net/NFT/normal/normal_%23FF33FF.png\"},{\"horse_name\":\"87172125831\",\"bloodline\":\"Count Fleet\",\"acceleration\":\"Elite\",\"aggressiveness\":\"Legendary\",\"sprint_speed\":\"Elite\",\"img_url\":\"https://d2vvute2v3y7pn.cloudfront.net/NFT/normal/normal_%235C995C.png\"},{\"horse_name\":\"87172125825\",\"bloodline\":\"Citation\",\"acceleration\":\"Rare\",\"aggressiveness\":\"Rare\",\"sprint_speed\":\"Elite\",\"img_url\":\"https://d2vvute2v3y7pn.cloudfront.net/NFT/normal/normal_%23004D66.png\"},{\"horse_name\":\"87172125833\",\"bloodline\":\"Citation\",\"acceleration\":\"Legendary\",\"aggressiveness\":\"Rare\",\"sprint_speed\":\"Elite\",\"img_url\":\"https://d2vvute2v3y7pn.cloudfront.net/NFT/normal/normal_%238DFF66.png\"},{\"horse_name\":\"87172125824\",\"bloodline\":\"Standardbreds\",\"acceleration\":\"Epic\",\"aggressiveness\":\"Epic\",\"sprint_speed\":\"Pacer\",\"img_url\":\"https://d2vvute2v3y7pn.cloudfront.net/NFT/normal/normal_%23FFCC33.png\"},{\"horse_name\":\"87172125828\",\"bloodline\":\"Citation\",\"acceleration\":\"Pacer\",\"aggressiveness\":\"Pacer\",\"sprint_speed\":\"Legendary\",\"img_url\":\"https://d2vvute2v3y7pn.cloudfront.net/NFT/normal/normal_%2329CC7B.png\"},{\"horse_name\":\"87172125830\",\"bloodline\":\"Count Fleet\",\"acceleration\":\"Rare\",\"aggressiveness\":\"Elite\",\"sprint_speed\":\"Epic\",\"img_url\":\"https://d2vvute2v3y7pn.cloudfront.net/NFT/normal/normal_%23CCFF33.png\"},{\"horse_name\":\"87172125829\",\"bloodline\":\"Count Fleet\",\"acceleration\":\"Rare\",\"aggressiveness\":\"Epic\",\"sprint_speed\":\"Legendary\",\"img_url\":\"https://d2vvute2v3y7pn.cloudfront.net/NFT/normal/normal_%234D9900.png\"},{\"horse_name\":\"87172125835\",\"bloodline\":\"Standardbreds\",\"acceleration\":\"Epic\",\"aggressiveness\":\"Epic\",\"sprint_speed\":\"Rare\",\"img_url\":\"https://d2vvute2v3y7pn.cloudfront.net/NFT/normal/normal_%23663E52.png\"}],\"room\":{\"id\":5054,\"capacity\":12,\"current_cap\":12,\"type\":2,\"start_time\":1659049200,\"end_time\":1659052200,\"status\":4,\"created_at\":\"2022-07-28T23:00:00.372Z\",\"updated_at\":\"2022-07-28T23:51:43.53Z\",\"prize_pool\":\"10000000000000000000000\",\"race_id\":166},\"race_id\":166}}";
        MetaDerbyRoomResp metaDerbyRoomResp5054 = JSON.parseObject(room5054, MetaDerbyRoomResp.class);
        Mockito.doReturn(metaDerbyRoomResp5054).when(metaderbyApi).room(5054);

        String room5046 = "{\"code\":0,\"message\":\"success\",\"show_err\":false,\"timestamp\":1659064906,\"data\":{\"room_horses\":[{\"horse_name\":\"87172125824\",\"bloodline\":\"Standardbreds\",\"acceleration\":\"Epic\",\"aggressiveness\":\"Epic\",\"sprint_speed\":\"Pacer\",\"img_url\":\"https://d2vvute2v3y7pn.cloudfront.net/NFT/normal/normal_%23FFCC33.png\"},{\"horse_name\":\"87172125830\",\"bloodline\":\"Count Fleet\",\"acceleration\":\"Rare\",\"aggressiveness\":\"Elite\",\"sprint_speed\":\"Epic\",\"img_url\":\"https://d2vvute2v3y7pn.cloudfront.net/NFT/normal/normal_%23CCFF33.png\"},{\"horse_name\":\"87172125828\",\"bloodline\":\"Citation\",\"acceleration\":\"Pacer\",\"aggressiveness\":\"Pacer\",\"sprint_speed\":\"Legendary\",\"img_url\":\"https://d2vvute2v3y7pn.cloudfront.net/NFT/normal/normal_%2329CC7B.png\"},{\"horse_name\":\"87172125826\",\"bloodline\":\"Standardbreds\",\"acceleration\":\"Rare\",\"aggressiveness\":\"Rare\",\"sprint_speed\":\"Epic\",\"img_url\":\"https://d2vvute2v3y7pn.cloudfront.net/NFT/normal/normal_%23FF33FF.png\"},{\"horse_name\":\"87172125827\",\"bloodline\":\"Citation\",\"acceleration\":\"Legendary\",\"aggressiveness\":\"Epic\",\"sprint_speed\":\"Elite\",\"img_url\":\"https://d2vvute2v3y7pn.cloudfront.net/NFT/normal/normal_%23153E66.png\"},{\"horse_name\":\"87172125835\",\"bloodline\":\"Standardbreds\",\"acceleration\":\"Epic\",\"aggressiveness\":\"Epic\",\"sprint_speed\":\"Rare\",\"img_url\":\"https://d2vvute2v3y7pn.cloudfront.net/NFT/normal/normal_%23663E52.png\"},{\"horse_name\":\"87172125825\",\"bloodline\":\"Citation\",\"acceleration\":\"Rare\",\"aggressiveness\":\"Rare\",\"sprint_speed\":\"Elite\",\"img_url\":\"https://d2vvute2v3y7pn.cloudfront.net/NFT/normal/normal_%23004D66.png\"},{\"horse_name\":\"87172125834\",\"bloodline\":\"Count Fleet\",\"acceleration\":\"Epic\",\"aggressiveness\":\"Rare\",\"sprint_speed\":\"Pacer\",\"img_url\":\"https://d2vvute2v3y7pn.cloudfront.net/NFT/normal/normal_%23CC297B.png\"},{\"horse_name\":\"87172125832\",\"bloodline\":\"Citation\",\"acceleration\":\"Rare\",\"aggressiveness\":\"Pacer\",\"sprint_speed\":\"Elite\",\"img_url\":\"https://d2vvute2v3y7pn.cloudfront.net/NFT/normal/normal_%23CCFF99.png\"},{\"horse_name\":\"87172125833\",\"bloodline\":\"Citation\",\"acceleration\":\"Legendary\",\"aggressiveness\":\"Rare\",\"sprint_speed\":\"Elite\",\"img_url\":\"https://d2vvute2v3y7pn.cloudfront.net/NFT/normal/normal_%238DFF66.png\"},{\"horse_name\":\"87172125831\",\"bloodline\":\"Count Fleet\",\"acceleration\":\"Elite\",\"aggressiveness\":\"Legendary\",\"sprint_speed\":\"Elite\",\"img_url\":\"https://d2vvute2v3y7pn.cloudfront.net/NFT/normal/normal_%235C995C.png\"},{\"horse_name\":\"87172125829\",\"bloodline\":\"Count Fleet\",\"acceleration\":\"Rare\",\"aggressiveness\":\"Epic\",\"sprint_speed\":\"Legendary\",\"img_url\":\"https://d2vvute2v3y7pn.cloudfront.net/NFT/normal/normal_%234D9900.png\"}],\"room\":{\"id\":5046,\"capacity\":12,\"current_cap\":12,\"type\":2,\"start_time\":1659045600,\"end_time\":1659048600,\"status\":4,\"created_at\":\"2022-07-28T22:00:00.255Z\",\"updated_at\":\"2022-07-28T22:51:43.543Z\",\"prize_pool\":\"10000000000000000000000\",\"race_id\":165},\"race_id\":165}}";
        MetaDerbyRoomResp metaDerbyRoomResp5046 = JSON.parseObject(room5046, MetaDerbyRoomResp.class);
        Mockito.doReturn(metaDerbyRoomResp5046).when(metaderbyApi).room(5046);
    }

    @Test
    @DisplayName("测试赔率计算")
    public void test1() throws ExecutionException, InterruptedException {
//
//        MetaDerbyRoomListResp resp = metaderbyApi.roomList();
//        // 获取未锁定房间
//        List<MetaDerbyRoomListResp.DataDTO> rooms = resp.getData().stream().filter(dataDTO -> dataDTO.getStatus() < 3).collect(Collectors.toList());
//
//        for (MetaDerbyRoomListResp.DataDTO room : rooms) {
//            // 房间信息
//            MetaDerbyRoomResp roomInfo = metaderbyApi.room(room.getId());
//            // 房间内的马信息
//            List<MetaDerbyRoomResp.DataDTO.RoomHorsesDTO> roomHorses = roomInfo.getData().getRoomHorses();
//            // 比赛信息
//            Integer raceId = roomInfo.getData().getRoom().getRaceId();
//            // 总质押量
//            BigInteger allHorsesStake = metaDerbyJobs.totalStakes();
//            List<MetaDerbyHorsesInfoResp> resultList = new ArrayList<>();
//            for (MetaDerbyRoomResp.DataDTO.RoomHorsesDTO roomHors : roomHorses) {
//                String horseName = roomHors.getHorseName();
//                BigInteger currentHorsesStake = metaDerbyJobs.totalStakeByIdStr(horseName);
//                // 某个马的总质押量
//                MetaDerbyHorsesInfoResp result = new MetaDerbyHorsesInfoResp();
//                // 当前马匹赔率 = 所有马匹总金额 / 当前马匹总金额
//                if (allHorsesStake.compareTo(BigInteger.ZERO) > 0) {
//                    BigDecimal divide = new BigDecimal(allHorsesStake).divide(new BigDecimal(currentHorsesStake), 2, RoundingMode.HALF_UP);
//                    result.setOdds(divide);
//                } else {
//                    result.setOdds(BigDecimal.ZERO);
//                }
//                result.setId(horseName);
//                resultList.add(result);
//            }
//            String s = JSON.toJSONString(resultList);
//            log.info("{}", s);
//        }


    }

}
