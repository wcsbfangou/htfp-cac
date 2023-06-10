package com.htfp.service.cac.common.enums;

/**
 * @Author sunjipeng
 * @Date 2022/12/20
 * @Description 描述
 */
public enum UavPowerTypeEnum {
    ELECTRIC(1, "电动"),
    OIL(2, "油动"),
    HYBRID(3,"混动"),

    ;

    public final Integer code;
    public final String desc;

    UavPowerTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static UavPowerTypeEnum getFromCode(Integer code) {
        for (UavPowerTypeEnum uavPowerTypeEnum : values()) {
            if (uavPowerTypeEnum.code.equals(code)) {
                return uavPowerTypeEnum;
            }
        }
        return null;
    }

}
