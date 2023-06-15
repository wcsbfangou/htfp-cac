package com.htfp.service.cac.common.enums.dataFrame;

/**
 * @Author sunjipeng
 * @Date 2023/6/15
 * @Description 描述
 */
public enum GcsTcpTypeEnum {
    GCS_AUTH_CAC_REQUEST(1, (byte)1, "地面站接入数据路由认证请求"),
    GCS_AUTH_CAC_RESPONSE(2, (byte)2, "地面站接入数据路由认证响应"),
    HEART_BEAT_REQUEST(3, (byte)3, "心跳请求"),
    HEART_BEAT_RESPONSE(4, (byte)4, "心跳响应"),
    FLIGHT_PLAN_REPLY_REQUEST(5, (byte)5, "飞行计划审批结果请求"),
    FLIGHT_PLAN_REPLY_RESPONSE(6, (byte)6, "飞行计划审批结果响应"),
    FLY_REPLY_REQUEST(7, (byte)7, "放飞申请结果请求"),
    FLY_REPLY_RESPONSE(8, (byte)8, "放飞申请结果响应"),
    RECEIVE_ALARM_REQUEST(9, (byte)9, "告警信息发送请求"),
    RECEIVE_ALARM_RESPONSE(10, (byte)10, "告警信息发送响应"),
    RECEIVE_ATC_REQUEST(11, (byte)11, "管制指令发送请求"),
    RECEIVE_ATC_RESPONSE(12, (byte)12, "管制指令发送响应"),
    ;

    public final Integer dataFrameType;
    public final byte type;
    public final String desc;

    GcsTcpTypeEnum(Integer dataFrameType, byte type, String desc) {
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

    public static GcsTcpTypeEnum getFromType(byte type) {
        for (GcsTcpTypeEnum gcsTcpTypeEnum : values()) {
            if (gcsTcpTypeEnum.type == type) {
                return gcsTcpTypeEnum;
            }
        }
        return null;
    }
}
