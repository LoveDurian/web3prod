package com.bobabrewery.partner.resp;

import lombok.Data;

/**
 * @author PailieXiangLong
 */
@Data
public class MetaDerbyUserInfoResp {


    /**
     * 当前使用10U的马匹ID
     */
    private Integer currentTicket;

    /**
     * 用户马匹信息
     */
    private HorsesInfo horsesInfo;


    @Data
    public static class HorsesInfo {
        /**
         * 当前马匹ID
         */
        private Integer id;

        /**
         * 预计得到的bre数量 =  用户投注金额(+10U) * 马匹赔率
         */
        private String estimate;

        /**
         * 用户质押的数量
         */
        private String pledge;
    }
}
