package com.bobabrewery.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public
class PancakeSwapPrice {
    @JsonProperty("updated_at")
    private Long updatedAt;
    @JsonProperty("data")
    private DataDTO data;

    @NoArgsConstructor
    @Data
    public static class DataDTO {
        @JsonProperty("name")
        private String name;
        @JsonProperty("symbol")
        private String symbol;
        @JsonProperty("price")
        private String price;
        @JsonProperty("price_BNB")
        private String priceBnb;
    }
}
