package com.bobabrewery.partner.resp;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author PailieXiangLong
 */
@Data
public class MetaDerbyHorsesInfoResp {
    /**
     * 马匹ID
     */
    private Integer id;
    /**
     * 马匹赔率 = 用户投注当前马匹金额(有10U带10U)/所有马匹投注总额
     */
    private BigDecimal odds;
}
