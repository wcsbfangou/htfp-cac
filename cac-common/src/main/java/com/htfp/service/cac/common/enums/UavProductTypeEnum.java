package com.htfp.service.cac.common.enums;

/**
 * @Author sunjipeng
 * @Date 2022/12/8
 * @Description 描述
 */
public enum UavProductTypeEnum {

    MULTIPLE_ROTOR_WING(1, "A", "多旋翼"),
    FIXED_WING(2, "B", "固定翼"),
    HELICOPTER(3, "C", "直升机"),
    VERTICAL_TAKEOFF_LANDING_FIXED_WING(4, "D", "垂直起降固定翼"),
    ROTATING_ROTOR_WING(5, "E", "自转旋翼"),
    AIRSHIP(6, "F", "飞艇"),
    OTHERS(7, "G", "其他"),
    ;

    public final Integer code;
    public final String type;
    public final String desc;

    UavProductTypeEnum(Integer code, String type, String desc) {
        this.code = code;
        this.type = type;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    public static UavProductTypeEnum getFromCode(Integer code) {
        for (UavProductTypeEnum uavProductTypeEnum : values()) {
            if (uavProductTypeEnum.code.equals(code)) {
                return uavProductTypeEnum;
            }
        }
        return null;
    }
}
