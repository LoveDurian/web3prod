package com.bobabrewery.domain.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author PailieXiangLong
 */
@NoArgsConstructor
@Data
public class UpdateBridgeOrder {
    /**
     * 订单ID
     */
    @JsonProperty("id")
    private String id;
    /**
     * 链上txID
     */
    @JsonProperty("txid")
    private String txid;
}
