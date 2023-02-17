package com.htfp.service.cac.common.enums;

/**
 * @Author sunjipeng
 * @Date 2022/12/20
 * @Description 描述
 */
public enum FlightPlanStatusTypeEnum {

    FLIGHT_PLAN_FORMULATED(1, "飞行计划已制定，待提交"),
    FLIGHT_PLAN_SUBMITTED(2, "飞行计划申请提交，待审批"),
    FLIGHT_PLAN_APPROVED(3,"飞行计划已通过，待执行"),
    FLIGHT_PLAN_UNAPPROVED(4,"飞行计划审批未通过"),

    FLY_APPLY_SUBMITTED(5, "放飞申请提交，待审批"),
    FLY_APPLY_APPROVED(6, "放飞申请通过，待离港"),
    FLY_APPLY_UNAPPROVED(7,"放飞申请未通过"),

    FLIGHT_PLAN_IMPLEMENT(8,"执行飞行计划，已离港"),

    ENTER_IDENTIFICATION_AREA(9, "进入机场识别区，待降落审批"),
    LANDING_APPLY_APPROVED(10,"降落审批通过，待进港"),
    PREPARE_LANDING(11,"已进港，准备降落"),
    COMPLETE_LANDING(12,"降落完成，飞行计划待结束"),

    FLIGHT_PLAN_FINISHED(13,"飞行计划结束"),
    FLIGHT_PLAN_OVERDUE(14,"飞行计划过期"),
    FLY_APPLY_OVERDUE(15,"放飞申请过期"),
    FLIGHT_PLAN_REVOKE(16,"飞行计划撤销"),
    FLIGHT_PLAN_CANCEL(17,"飞行计划取消"),
    ;

    public final Integer code;
    public final String desc;

    FlightPlanStatusTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static FlightPlanStatusTypeEnum getFromCode(Integer code) {
        for (FlightPlanStatusTypeEnum flightPlanStatusTypeEnum : values()) {
            if (flightPlanStatusTypeEnum.code.equals(code)) {
                return flightPlanStatusTypeEnum;
            }
        }
        return null;
    }
}
