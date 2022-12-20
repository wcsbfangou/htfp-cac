package com.htfp.service.oac.client.enums;

/**
 * @Author sunjipeng
 * @Date 2022/12/8
 * @Description 描述
 */
public enum IdCardTypeEnum {

    IDENTITY_CARD(0, "身份证"),
    BUSINESS_LICENSE(1, "营业执照"),
    PASSPORT(2, "护照"),
    MILITARY_CARD(3,"军人证"),
    POLICE_CARD(4,"警员证"),
    ;

    public final Integer code;
    public final String desc;

    IdCardTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static IdCardTypeEnum getFromCode(Integer code) {
        for (IdCardTypeEnum idCardTypeEnum : values()) {
            if (idCardTypeEnum.code.equals(code)) {
                return idCardTypeEnum;
            }
        }
        return null;
    }
}
