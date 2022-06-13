package com.htfp.service.cac.common.enums.dataFrame;

/**
 * @Author sunjipeng
 * @Date 2022-06-13 11:19
 */
public enum SerializationAlgorithmEnum {

    NO_ALGORITHM((byte)0, "不使用序列化算法"),
    ;

    public final byte type;
    public final String desc;

    SerializationAlgorithmEnum(byte type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public byte getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    public static SerializationAlgorithmEnum getFromType(byte type) {
        for (SerializationAlgorithmEnum serializationAlgorithmEnum : values()) {
            if (serializationAlgorithmEnum.type == type) {
                return serializationAlgorithmEnum;
            }
        }
        return null;
    }

}
