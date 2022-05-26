package com.htfp.service.cac.common.enums;

/**
 * @Author sunjipeng
 * @Date 2022-05-25 21:25
 */
public enum MappingStatusEnums {

    VALID(0, "生效"),
    INVALID(1, "失效"),
    ;

    public final Integer code;
    public final String desc;

    MappingStatusEnums(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static MappingStatusEnums getFromCode(Integer code) {
        for (MappingStatusEnums mappingStatusEnums : values()) {
            if (mappingStatusEnums.code.equals(code)) {
                return mappingStatusEnums;
            }
        }
        return null;
    }

}
