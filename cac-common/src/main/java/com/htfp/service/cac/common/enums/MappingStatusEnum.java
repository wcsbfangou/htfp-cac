package com.htfp.service.cac.common.enums;

/**
 * @Author sunjipeng
 * @Date 2022-05-25 21:25
 * @Description Mapping关系的Status枚举
 */
public enum MappingStatusEnum {

    VALID(0, "生效"),
    INVALID(1, "失效"),
    ;

    public final Integer code;
    public final String desc;

    MappingStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static MappingStatusEnum getFromCode(Integer code) {
        for (MappingStatusEnum mappingStatusEnum : values()) {
            if (mappingStatusEnum.code.equals(code)) {
                return mappingStatusEnum;
            }
        }
        return null;
    }

}
