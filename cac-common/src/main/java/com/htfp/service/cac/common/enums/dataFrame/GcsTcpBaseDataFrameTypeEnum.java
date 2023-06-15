package com.htfp.service.cac.common.enums.dataFrame;

/**
 * @Author sunjipeng
 * @Date 2023/6/14
 * @Description 描述
 */
public enum GcsTcpBaseDataFrameTypeEnum {

    GCS_AUTH_CAC_REQUEST(1, "GCS_AUTH_CAC_REQUEST", "地面站接入数据路由认证请求"),
    GCS_AUTH_CAC_RESPONSE(2, "GCS_AUTH_CAC_RESPONSE", "地面站接入数据路由认证响应"),
    HEART_BEAT_REQUEST(3, "HEART_BEAT_REQUEST", "心跳请求"),
    HEART_BEAT_RESPONSE(4, "HEART_BEAT_RESPONSE", "心跳响应"),
    FLIGHT_PLAN_REPLY_REQUEST(5, "FLIGHT_PLAN_REPLY_REQUEST", "飞行计划审批结果请求"),
    FLIGHT_PLAN_REPLY_RESPONSE(6, "FLIGHT_PLAN_REPLY_RESPONSE", "飞行计划审批结果响应"),
    FLY_REPLY_REQUEST(7, "FLY_REPLY_REQUEST", "放飞申请结果请求"),
    FLY_REPLY_RESPONSE(8, "FLY_REPLY_RESPONSE", "放飞申请结果响应"),
    RECEIVE_ALARM_REQUEST(9, "RECEIVE_ALARM_REQUEST", "告警信息发送请求"),
    RECEIVE_ALARM_RESPONSE(10, "RECEIVE_ALARM_RESPONSE", "告警信息发送响应"),
    RECEIVE_ATC_REQUEST(11, "RECEIVE_ATC_REQUEST", "管制指令发送请求"),
    RECEIVE_ATC_RESPONSE(12, "RECEIVE_ATC_RESPONSE", "管制指令发送响应"),
    ;

    public final Integer type;
    public final String name;
    public final String desc;

    GcsTcpBaseDataFrameTypeEnum(Integer type, String name, String desc) {
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

    public static GcsTcpBaseDataFrameTypeEnum getFromType(Integer type) {
        for (GcsTcpBaseDataFrameTypeEnum gcsTcpBaseDataFrameTypeEnum : values()) {
            if (gcsTcpBaseDataFrameTypeEnum.type.equals(type)) {
                return gcsTcpBaseDataFrameTypeEnum;
            }
        }
        return null;
    }

    public static GcsTcpBaseDataFrameTypeEnum getFromMagicCodeAndType(short magicCode, byte type) {
        if (MagicCodeEnum.DATA_TRANSFER.equals(MagicCodeEnum.getFromCode(magicCode))) {
            GcsTcpTypeEnum gcsTcpTypeEnum = GcsTcpTypeEnum.getFromType(type);
            if (gcsTcpTypeEnum != null) {
                return GcsTcpBaseDataFrameTypeEnum.getFromType(gcsTcpTypeEnum.getDataFrameType());
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

}
