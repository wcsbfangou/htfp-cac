package com.htfp.service.cac.common.enums.dataFrame;

/**
 * @Author sunjipeng
 * @Date 2022-06-13 13:30
 */
public enum DataFrameTypeEnum {

    DATA_TRANSFER_SUBSCRIBE(1, "DATA_TRANSFER_SUBSCRIBE", "订阅"),
    DATA_TRANSFER_CANCEL_SUBSCRIBE(2, "DATA_TRANSFER_CANCEL_SUBSCRIBE", "取消订阅"),
    DATA_TRANSFER_GCS_TO_RCS(3, "DATA_TRANSFER_GCS_TO_RCS","GCS到RCS数据透传"),
    ;

    public final Integer type;
    public final String name;
    public final String desc;

    DataFrameTypeEnum(Integer type, String name, String desc) {
        this.type = type;
        this.name = name;
        this.desc = desc;
    }

    public Integer getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public static DataFrameTypeEnum getFromType(Integer type) {
        for (DataFrameTypeEnum dataFrameTypeEnum : values()) {
            if (dataFrameTypeEnum.type.equals(type)) {
                return dataFrameTypeEnum;
            }
        }
        return null;
    }

    public static DataFrameTypeEnum getFromMagicCodeAndType(short magicCode, byte type){
        if(MagicCodeEnum.DATA_TRANSFER.equals(MagicCodeEnum.getFromCode(magicCode))){
            DataTransferTypeEnum dataTransferTypeEnum = DataTransferTypeEnum.getFromType(type);
            if(dataTransferTypeEnum!=null){
                return DataFrameTypeEnum.getFromType(dataTransferTypeEnum.getDataFrameType());
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
}
