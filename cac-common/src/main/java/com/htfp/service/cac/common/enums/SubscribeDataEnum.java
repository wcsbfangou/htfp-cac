package com.htfp.service.cac.common.enums;

/**
 * @Author sunjipeng
 * @Date 2022-06-29 11:11
 */
public enum SubscribeDataEnum {

    SUBSCRIBE(0, "订阅"),
    UN_SUBSCRIBE(1, "未订阅"),
    ;

    public final Integer code;
    public final String desc;

    SubscribeDataEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static SubscribeDataEnum getFromCode(Integer code) {
        for (SubscribeDataEnum subscribeDataEnum : values()) {
            if (subscribeDataEnum.code.equals(code)) {
                return subscribeDataEnum;
            }
        }
        return null;
    }
}
