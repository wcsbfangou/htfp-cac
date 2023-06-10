package com.htfp.service.cac.common.enums;

/**
 * @Author sunjipeng
 * @Date 2022/12/8
 * @Description 描述
 */
public enum IllegalRecordTypeEnum {

    NO_ILLEGAL(0, "没有违法违规记录"),
    ILLEGAL(1, "有违法违规记录"),
            ;

    public final Integer code;
    public final String desc;

    IllegalRecordTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static IllegalRecordTypeEnum getFromCode(Integer code) {
        for (IllegalRecordTypeEnum illegalRecordTypeEnum : values()) {
            if (illegalRecordTypeEnum.code.equals(code)) {
                return illegalRecordTypeEnum;
            }
        }
        return null;
    }
}
