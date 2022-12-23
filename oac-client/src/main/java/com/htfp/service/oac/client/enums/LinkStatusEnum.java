package com.htfp.service.oac.client.enums;

/**
 * @Author sunjipeng
 * @Date 2022/12/21
 * @Description 描述
 */
public enum LinkStatusEnum {

    OFFLINE(0, "离线"),
    ONLINE(1, "上线"),
    DISCONNECT(2, "断线"),
    ;

    public final Integer code;
    public final String desc;

    LinkStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static LinkStatusEnum getFromCode(Integer code) {
        for (LinkStatusEnum linkStatusEnum : values()) {
            if (linkStatusEnum.code.equals(code)) {
                return linkStatusEnum;
            }
        }
        return null;
    }
}
