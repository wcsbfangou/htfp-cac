package com.htfp.service.cac.common.enums.dataFrame;

/**
 * @Author sunjipeng
 * @Date 2022-06-13 11:07
 * @Description 数据传输类型枚举
 */
public enum DataTransferTypeEnum {

    SUBSCRIBE(1, (byte) 1, "订阅"),
    CANCEL_SUBSCRIBE(2, (byte) 2, "取消订阅"),
    GCS_TO_RCS_DATA_TRANSFER(3, (byte) 3, "GCS到RCS数据透传"),
    GCS_TO_OAC_DATA_TRANSFER(4, (byte) 4, "GCS到OAC数据透传"),
    ;

    public final Integer dataFrameType;
    public final byte type;
    public final String desc;

    DataTransferTypeEnum(Integer dataFrameType, byte type, String desc) {
        this.dataFrameType = dataFrameType;
        this.type = type;
        this.desc = desc;
    }

    public Integer getDataFrameType() {
        return dataFrameType;
    }

    public byte getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    public static DataTransferTypeEnum getFromType(byte type) {
        for (DataTransferTypeEnum dataTransferTypeEnum : values()) {
            if (dataTransferTypeEnum.type == type) {
                return dataTransferTypeEnum;
            }
        }
        return null;
    }
}
