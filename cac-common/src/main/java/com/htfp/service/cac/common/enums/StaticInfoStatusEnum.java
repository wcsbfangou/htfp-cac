package com.htfp.service.cac.common.enums;

/**
 * @Author sunjipeng
 * @Date 2022/12/7
 * @Description 描述
 */
public enum StaticInfoStatusEnum {

    TYPE_IN(0, "录入"),
    REGISTERING(1, "注册中"),
    REGISTERED(2, "已注册"),
    CANCELING(3,"注销中"),
    CANCELED(4,"已注销"),
    ;

    public final Integer code;
    public final String desc;

    StaticInfoStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static StaticInfoStatusEnum getFromCode(Integer code) {
        for (StaticInfoStatusEnum staticInfoStatusEnum : values()) {
            if (staticInfoStatusEnum.code.equals(code)) {
                return staticInfoStatusEnum;
            }
        }
        return null;
    }

}
