package com.htfp.service.oac.common.enums;

/**
 * @Author sunjipeng
 * @Date 2022/12/20
 * @Description 描述
 */
public enum AlarmLevelTypeEnum {

    NOT_CLEAR(0, "情况不明"),
    ALARM(1, "告警"),
    DISTRESS(2, "遇险"),
    ;


    public final Integer code;
    public final String desc;

    AlarmLevelTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static AlarmLevelTypeEnum getFromCode(Integer code) {
        for (AlarmLevelTypeEnum alarmLevelTypeEnum : values()) {
            if (alarmLevelTypeEnum.code.equals(code)) {
                return alarmLevelTypeEnum;
            }
        }
        return null;
    }
}
