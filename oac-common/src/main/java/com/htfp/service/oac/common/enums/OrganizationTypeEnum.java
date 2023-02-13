package com.htfp.service.oac.common.enums;

/**
 * @Author sunjipeng
 * @Date 2022/12/8
 * @Description 描述
 */
public enum OrganizationTypeEnum {

    ORGAN(1, "机关"),
    PUBLIC_INSTITUTION(2, "事业单位"),
    ENTERPRISE(3,"企业"),
    SOCIAL_ORGANIZATION(4,"社会团体"),
    OTHER(5,"其他组织机构"),
    ;

    public final Integer code;
    public final String desc;

    OrganizationTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static OrganizationTypeEnum getFromCode(Integer code) {
        for (OrganizationTypeEnum organizationTypeEnum : values()) {
            if (organizationTypeEnum.code.equals(code)) {
                return organizationTypeEnum;
            }
        }
        return null;
    }
}
