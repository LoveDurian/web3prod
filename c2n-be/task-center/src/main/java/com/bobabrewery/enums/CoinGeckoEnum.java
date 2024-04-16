package com.bobabrewery.enums;

/**
 * @author PailieXiangLong
 */
public enum CoinGeckoEnum {
    SHIBUI("shibui-dao", "SHIBUI");

    private String apiId;
    private String tokenName;

    CoinGeckoEnum(String apiId, String tokenName) {
        this.apiId = apiId;
        this.tokenName = tokenName;
    }

    public String getApiId() {
        return apiId;
    }

    public String getTokenName() {
        return tokenName;
    }

    public static CoinGeckoEnum get(String tokenName) {
        for (CoinGeckoEnum value : CoinGeckoEnum.values()) {
            if (value.getTokenName().equals(tokenName)) {
                return value;
            }
        }
        return null;
    }
}
