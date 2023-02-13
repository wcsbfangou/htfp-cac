package com.htfp.service.oac.common.enums;

/**
 * @Author sunjipeng
 * @Date 2022/12/20
 * @Description 描述
 */
public enum UavDynamicInfoQueryStatusEnum {

    FLIGHT_PLAN_PASS_AND_NOT_OVER(0, "飞行计划已通过且未结束"),
    ARRIVAL(1, "处于进港状态"),
    DEPARTURE(2, "处于离港状态"),
    ;


    public final Integer code;
    public final String desc;

    UavDynamicInfoQueryStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static UavDynamicInfoQueryStatusEnum getFromCode(Integer code) {
        for (UavDynamicInfoQueryStatusEnum uavDynamicInfoQueryStatusEnum : values()) {
            if (uavDynamicInfoQueryStatusEnum.code.equals(code)) {
                return uavDynamicInfoQueryStatusEnum;
            }
        }
        return null;
    }
}
