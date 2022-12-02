package com.htfp.service.cac.common.enums;

/**
 * @Author sunjipeng
 * @Date 2022-06-06 15:22
 * @Description 地面站类型枚举
 */
public enum GcsTypeEnum {

    GCS(1, "地面站"),
    RCS(2, "远程地面站"),
    GCS_AND_RCS(3, "地面站兼远程地面站"),
    ;

    public final Integer code;
    public final String desc;

    GcsTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static GcsTypeEnum getFromCode(Integer code) {
        for (GcsTypeEnum gcsTypeEnum : values()) {
            if (gcsTypeEnum.code.equals(code)) {
                return gcsTypeEnum;
            }
        }
        return null;
    }
}
