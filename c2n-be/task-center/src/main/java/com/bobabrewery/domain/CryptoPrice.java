package com.bobabrewery.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@Data
public class CryptoPrice {
    @JsonProperty("USD")
    private BigDecimal price;
}
