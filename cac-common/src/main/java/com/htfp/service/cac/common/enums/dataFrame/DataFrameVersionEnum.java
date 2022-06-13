package com.htfp.service.cac.common.enums.dataFrame;

/**
 * @Author sunjipeng
 * @Date 2022-06-13 11:22
 */
public enum DataFrameVersionEnum {

    VERSION_0((byte)0, "版本0"),
    ;

    public final byte type;
    public final String desc;

    DataFrameVersionEnum(byte type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public byte getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    public static DataFrameVersionEnum getFromType(byte type) {
        for (DataFrameVersionEnum dataFrameVersionEnum : values()) {
            if (dataFrameVersionEnum.type == type) {
                return dataFrameVersionEnum;
            }
        }
        return null;
    }
}
