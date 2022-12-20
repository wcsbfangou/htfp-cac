package com.htfp.service.cac.common.enums;

/**
 * @Author sunjipeng
 * @Date 2022/12/8
 * @Description 描述
 */
public enum PilotTypeEnum {

    SINGLE_UAV_PILOT(1, "独立操控无人机驾驶员"),
    DISTRIBUTED_UAV_SYSTEM_PILOT(2, "分布式无人机系统操作负责人"),
    ;

    public final Integer code;
    public final String desc;

    PilotTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static PilotTypeEnum getFromCode(Integer code) {
        for (PilotTypeEnum pilotTypeEnum : values()) {
            if (pilotTypeEnum.code.equals(code)) {
                return pilotTypeEnum;
            }
        }
        return null;
    }
}
