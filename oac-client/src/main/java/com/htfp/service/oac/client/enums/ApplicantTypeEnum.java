package com.htfp.service.oac.client.enums;

/**
 * @Author sunjipeng
 * @Date 2022/12/20
 * @Description 描述
 */
public enum ApplicantTypeEnum {
    ORGANIZATION(1, "组织机构"),
    PERSON(2, "个人"),
    ;

    public final Integer code;
    public final String desc;

    ApplicantTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static ApplicantTypeEnum getFromCode(Integer code) {
        for (ApplicantTypeEnum applicantTypeEnum : values()) {
            if (applicantTypeEnum.code.equals(code)) {
                return applicantTypeEnum;
            }
        }
        return null;
    }
}
