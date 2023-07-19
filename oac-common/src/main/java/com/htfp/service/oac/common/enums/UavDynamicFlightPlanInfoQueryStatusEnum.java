package com.htfp.service.oac.common.enums;

/**
 * @Author sunjipeng
 * @Date 2023/6/9
 * @Description 描述
 */
public enum UavDynamicFlightPlanInfoQueryStatusEnum {

    ALL(0, "所有"),
    FLIGHT_PLAN_SUBMITTED(1, "飞行计划申请提交，待审批"),
    FLIGHT_PLAN_APPROVED(2,"飞行计划已通过，待执行"),
    PLANNED_DEPARTURE(3, "计划离港"),
    DEPARTURE(4, "离港"),
    PLANNED_ARRIVAL(5,"计划进港"),
    ARRIVAL(6,"进港"),
    ;

    public final Integer code;
    public final String desc;

    UavDynamicFlightPlanInfoQueryStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static UavDynamicFlightPlanInfoQueryStatusEnum getFromCode(Integer code) {
        for (UavDynamicFlightPlanInfoQueryStatusEnum uavDynamicFlightPlanInfoQueryStatusEnum : values()) {
            if (uavDynamicFlightPlanInfoQueryStatusEnum.code.equals(code)) {
                return uavDynamicFlightPlanInfoQueryStatusEnum;
            }
        }
        return null;
    }
}
