package com.bobabrewery.service;

import com.bobabrewery.domain.MetaDerbyUserInfoParam;
import com.bobabrewery.domain.param.TicketParam;
import com.bobabrewery.partner.resp.MetaDerbyHorsesInfoResp;
import com.bobabrewery.partner.resp.MetaDerbyUserInfoResp;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.Future;

/**
 * @author PailieXiangLong
 */
public interface MetaDerbyService {
    /**
     * 白名单判断
     *
     * @param walletAddress
     * @return
     */
    boolean existWhite(String walletAddress);

    /**
     * 所有已经使用的白名单的总金额(换算成Bre  取项目开始时的bre价格)
     *
     * @return
     */
    Future<BigDecimal> countAll10U();


    /**
     * 确认使用10U
     *
     * @param ticketParam
     * @return
     */
    boolean confirmTicket(TicketParam ticketParam);


    /**
     * 当前用户的10U质押的马匹ID
     *
     * @param walletAddress
     * @return
     */
    Integer currentTicket(String walletAddress);


    /**
     * 全部马匹信息
     *
     * @return
     */
    List<MetaDerbyHorsesInfoResp> allHorsesInfo();

    /**
     * 用户信息
     *
     * @param param
     * @return
     */
    MetaDerbyUserInfoResp userInfo(MetaDerbyUserInfoParam param);


}
