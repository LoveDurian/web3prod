package com.bobabrewery.domain.resp;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author PailieXiangLong
 */
@NoArgsConstructor
@Data
public class UpdateBridgeOrderResult {

    /**
     * code
     */
    @JsonProperty("code")
    private Integer code;
    /**
     * msg
     */
    @JsonProperty("msg")
    private String msg;
    /**
     * data
     */
    @JsonProperty("data")
    private Object data;

}
