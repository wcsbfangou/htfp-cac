package com.htfp.service.oac.common.enums;

/**
 * @Author sunjipeng
 * @Date 2022/12/8
 * @Description 描述
 */
public enum UavProductSizeTypeEnum {

    MICRO_TYPE(0, 0, "微型"),
    LIGHT_ONE_TYPE(1, 1, "Ⅰ类:轻型1"),
    LIGHT_TWO_TYPE(2, 1, "Ⅰ类:轻型2"),
    SMALL_TYPE(3, 2, "Ⅱ类:小型"),
    MEDIUM_TYPE(4, 3, "Ⅲ类:中型"),
    LARGE_TYPE(5, 4, "Ⅳ类:大型"),
    PLANT_PROTECTION_TYPE(6, 5, "Ⅴ类:植保类无人机"),
    AIRSHIP_TYPE(7, 6, "Ⅵ类:无人飞艇"),
    SPECIAL_RISK_TWO_TYPE(8, 7, "Ⅶ类:有特殊风险的Ⅱ类无人机"),
    ;

    public final Integer code;
    public final Integer type;
    public final String desc;

    UavProductSizeTypeEnum(Integer code, Integer type, String desc) {
        this.code = code;
        this.type = type;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public Integer getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    public static UavProductSizeTypeEnum getFromCode(Integer code) {
        for (UavProductSizeTypeEnum uavProductSizeTypeEnum : values()) {
            if (uavProductSizeTypeEnum.code.equals(code)) {
                return uavProductSizeTypeEnum;
            }
        }
        return null;
    }
}
