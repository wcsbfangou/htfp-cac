package com.htfp.service.cac.common.enums;

/**
 * @Author sunjipeng
 * @Date 2022-05-18 16:39
 */
public enum ErrorCodeEnum {

    SUCCESS(0, "成功"),

    UNKNOWN_ERROR(1001, "未知错误"),

    AUTHORIZATION_VALIDATE_FAIL(2001, "校验码认证失败"),
    DATE_VALIDATE_FAIL(2002, "时间校验失败"),
    GCS_ID_VALIDATE_FAIL(2003, "时间校验失败"),
    VALIDATE_TIMEOUT(2004, "认证超时"),
    LACK_OF_AUTHORIZATION(2005, "验证码缺失"),
    LACK_OF_DATA(2006, "时间缺失"),
    LACK_OF_HEADER_GCS_ID(2007, "请求方编号缺失"),

    REQUEST_PARSE_FAIL(3001, "请求体解析失败"),
    LACK_OF_OTHER_FILED(3002, "其他字段缺失"),
    LACK_OF_UAV_ID(3003, "无人机编号缺失"),
    LACK_OF_PILOT_ID(3004, "驾驶员编号缺失"),
    LACK_OF_UAV_STATUS(3005, "无人机状态缺失"),
    LACK_OF_COMMAND_CODE(3006, "指控命令码缺失"),
    LACK_OF_COMMAND_RESULT(3007, "指控结果缺失"),
    LACK_OF_GCS_ID(3008, "地面站编号缺失"),
    LACK_OF_GCS_IP(3009, "地面站IP缺失"),

    SYSTEM_UNAVAILABLE(4001, "系统暂时不可用"),
    SYSTEM_ERROR(4002, "系统错误"),
    PROCESSING_TIMEOUT(4003, "处理超时"),
    REQUEST_TIMEOUT(4004, "请求超时"),
    DBA_ERROR(4005, "数据库异常"),

    LACK_OF_MAPPING(5001, "mapping关系缺失"),
    GCS_MISMATCH_UAV(5002, "地面站可控机型与无人机类型不一致"),
    PILOT_MISMATCH_UAV(5003, "驾驶员准驾机型与无人机类型不一致"),
    OTHER_BIZ_ERROR(5004, "其他业务失败"),
    ;


    public final Integer code;
    public final String desc;

    ErrorCodeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static ErrorCodeEnum getFromCode(Integer code) {
        for (ErrorCodeEnum errorCodeEnum : values()) {
            if (errorCodeEnum.code.equals(code)) {
                return errorCodeEnum;
            }
        }
        return null;
    }
}
