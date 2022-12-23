package com.htfp.service.oac.client.enums;

/**
 * @Author sunjipeng
 * @Date 2022/12/20
 * @Description 描述
 */
public enum FlightInfoTypeEnum {

    NOTICE(0, "普通通知"),
    ALARM(1, "告警"),
    OTHERS(2, "其他"),
    ;


    public final Integer code;
    public final String desc;

    FlightInfoTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static FlightInfoTypeEnum getFromCode(Integer code) {
        for (FlightInfoTypeEnum flightInfoTypeEnum : values()) {
            if (flightInfoTypeEnum.code.equals(code)) {
                return flightInfoTypeEnum;
            }
        }
        return null;
    }
}
