package com.htfp.service.oac.common.enums;

/**
 * @Author sunjipeng
 * @Date 2022/12/8
 * @Description 描述
 */
public enum GenderEnum {

    FEMALE(0, "女性"),
    MALE(1, "男性"),
    ;

    public final Integer code;
    public final String desc;

    GenderEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static GenderEnum getFromCode(Integer code) {
        for (GenderEnum genderEnum : values()) {
            if (genderEnum.code.equals(code)) {
                return genderEnum;
            }
        }
        return null;
    }
}
