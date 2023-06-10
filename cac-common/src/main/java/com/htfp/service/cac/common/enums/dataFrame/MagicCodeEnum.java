package com.htfp.service.cac.common.enums.dataFrame;

/**
 * @Author sunjipeng
 * @Date 2022-06-13 10:55
 * @Description 数据帧头魔数枚举
 */
public enum MagicCodeEnum {

    DATA_TRANSFER((short) 0x0DAD, "数据透传"),
    ;

    public final short code;
    public final String desc;

    MagicCodeEnum(short code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public short getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static MagicCodeEnum getFromCode(short code) {
        for (MagicCodeEnum magicCodeEnum : values()) {
            if (magicCodeEnum.code == code) {
                return magicCodeEnum;
            }
        }
        return null;
    }
}
