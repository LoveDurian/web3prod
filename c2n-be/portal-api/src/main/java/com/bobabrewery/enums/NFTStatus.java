package com.bobabrewery.enums;

/**
 * @author orange
 */
public enum NFTStatus {

    NOT_START(0),
    WHITELIST_SALE(1),
    PUBLIC_SALE(2),
    SALE_END(3);

    private Integer code;

    NFTStatus(int code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public static NFTStatus getStatusEnum(Integer code) {
        for (NFTStatus userTypeEnum : NFTStatus.values()) {
            if (userTypeEnum.getCode().equals(code)) {
                return userTypeEnum;
            }
        }
        return null;
    }
}
