package com.htfp.service.cac.common.enums;

/**
 * @Author sunjipeng
 * @Date 2022-05-26 22:18
 */
public enum NavigationStatusEnums {

    PROGRESSING(0, "进行中"),
    FINISH(1, "结束"),
    ;

    public final Integer code;
    public final String desc;

    NavigationStatusEnums(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static NavigationStatusEnums getFromCode(Integer code) {
        for (NavigationStatusEnums navigationStatusEnums : values()) {
            if (navigationStatusEnums.code.equals(code)) {
                return navigationStatusEnums;
            }
        }
        return null;
    }
}
