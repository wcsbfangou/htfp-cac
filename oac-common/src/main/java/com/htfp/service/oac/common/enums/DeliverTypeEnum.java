package com.htfp.service.oac.common.enums;

/**
 * @Author sunjipeng
 * @Date 2022/12/8
 * @Description 描述
 */
public enum DeliverTypeEnum {

    GENERATED(0, "已生成"),
    DELIVERING(1, "正在下发"),
    DELIVERED(2, "已下发"),
    ;

    public final Integer code;
    public final String desc;

    DeliverTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static DeliverTypeEnum getFromCode(Integer code) {
        for (DeliverTypeEnum deliverTypeEnum : values()) {
            if (deliverTypeEnum.code.equals(code)) {
                return deliverTypeEnum;
            }
        }
        return null;
    }
}
