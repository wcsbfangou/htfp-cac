package com.htfp.service.cac.common.enums;

/**
 * @Author sunjipeng
 * @Date 2022-05-18 19:04
 */
public enum UavStatusEnum {

    SHUT_DOWN(0, "关机"),
    STOP(1, "停止"),
    SLIDE(2, "滑行"),
    TAKE_OFF(3, "起飞"),
    CLIMB(4, "爬升"),
    CRUISE(5, "巡航"),
    DESCEND(6, "下降"),
    LANDING(7, "着陆"),
    ;


    public final Integer code;
    public final String desc;

    UavStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static UavStatusEnum getFromCode(Integer code) {
        for (UavStatusEnum uavStatusEnum : values()) {
            if (uavStatusEnum.code.equals(code)) {
                return uavStatusEnum;
            }
        }
        return null;
    }
}
