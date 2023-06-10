package com.htfp.service.oac.common.enums;

/**
 * @Author sunjipeng
 * @Date 2022/12/8
 * @Description 描述
 */
public enum PilotLicenseTypeEnum {

    PILOT_LICENSE(0, "飞行执照"),
    CIVIL_UAV_PILOT_CERTIFICATE(1, "民用无人机驾驶员合格证"),
    TRAINING(2, "训练"),
    ;

    public final Integer code;
    public final String desc;

    PilotLicenseTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static PilotLicenseTypeEnum getFromCode(Integer code) {
        for (PilotLicenseTypeEnum pilotLicenseTypeEnum : values()) {
            if (pilotLicenseTypeEnum.code.equals(code)) {
                return pilotLicenseTypeEnum;
            }
        }
        return null;
    }
}
