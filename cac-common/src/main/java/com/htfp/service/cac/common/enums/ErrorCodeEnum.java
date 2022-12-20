package com.htfp.service.cac.common.enums;

/**
 * @Author sunjipeng
 * @Date 2022-05-18 16:39
 * @Description 返回体失败类型枚举
 */
public enum ErrorCodeEnum {

    SUCCESS(0, "成功"),

    UNKNOWN_ERROR(1001, "未知错误"),

    AUTHORIZATION_VALIDATE_FAIL(2001, "校验码认证失败"),
    DATE_VALIDATE_FAIL(2002, "时间校验失败"),
    GCS_ID_VALIDATE_FAIL(2003, "地面站编码校验失败"),
    VALIDATE_TIMEOUT(2004, "认证超时"),
    LACK_OF_AUTHORIZATION(2005, "验证码缺失"),
    LACK_OF_DATE(2006, "时间缺失"),
    LACK_OF_HEADER_GCS_ID(2007, "请求方编号缺失"),
    LACK_OF_CONTROLLABLE_UAV_TYPE(2008, "可控无人机类型缺失"),

    REQUEST_PARSE_FAIL(3001, "请求体解析失败"),
    LACK_OF_OTHER_FILED(3002, "其他字段缺失"),
    LACK_OF_COMMAND_CODE(3003, "指控命令码缺失"),
    LACK_OF_COMMAND_RESULT(3004, "指控结果缺失"),

    WRONG_UAV_ID(3101, "无人机编号错误"),
    LACK_OF_UAV_ID(3102, "无人机编号缺失"),
    LACK_OF_UAV_REG(3103, "无人机实名登记编码缺失"),
    LACK_OF_UAV_STATUS(3104, "无人机状态缺失"),
    LACK_OF_UAV_NAME(3105, "无人机状态缺失"),
    LACK_OF_UAV_TYPE(3106, "无人机类型缺失"),
    LACK_OF_UAV_PRODUCT_TYPE(3107, "无人机产品类型缺失"),
    LACK_OF_UAV_PRODUCT_SIZE_TYPE(3108, "无人机产品大小类型缺失"),
    LACK_OF_UAV_CPN(3109, "无人机CPN码缺失"),
    LACK_OF_UAV_VIN(3110, "无人机VIN码缺失"),
    LACK_OF_UAV_PVIN(3111, "无人机PVIN码缺失"),
    LACK_OF_UAV_SN(3112, "无人机SN码缺失"),
    LACK_OF_UAV_FLIGHT_CONTROL_SN(3113, "无人机飞行控制软件SN码缺失"),
    LACK_OF_UAV_IMEI(3114, "无人机IMEI码缺失"),
    LACK_OF_UAV_IMSI(3115, "无人机IMSI码缺失"),
    LACK_OF_UAV_MANUFACTURER_NAME(3116, "无人机制造厂商缺失"),
    LACK_OF_UAV_PRODUCT_NAME(3117, "无人机产品名称缺失"),

    LACK_OF_PILOT_ID(3201, "驾驶员编号缺失"),
    WRONG_PILOT_ID(3202, "驾驶员编号错误"),
    LACK_OF_LICENSE_TYPE(3203, "驾驶员执照类型缺失"),
    LACK_OF_LICENSE_ID(3204, "驾驶员执照编号缺失"),

    LACK_OF_GCS_ID(3301, "地面站编码缺失"),
    LACK_OF_GCS_REG(3302, "地面站登记编号缺失"),
    LACK_OF_GCS_IP(3303, "地面站IP缺失"),
    WRONG_GCS_ID(3304, "地面站编号错误"),
    LACK_OF_DATA_LINK_TYPE(3305, "数据链路缺失"),
    LACK_OF_GCS_TYPE(3306, "地面站类型缺失"),

    LACK_OF_OPERATOR_ID(3401, "运营主体编码缺失"),
    LACK_OF_OPERATOR_TYPE(3402, "运营主体类型缺失"),
    LACK_OF_REAL_NAME(3403, "真实姓名缺失"),
    LACK_OF_PHONE_NUMBER(3404, "联系方式缺失"),
    LACK_OF_ID_CARD_TYPE(3405, "证件类型缺失"),
    LACK_OF_ID_CARD_NUMBER(3406, "证件号码缺失"),
    LACK_OF_COMPANY_NAME(3407, "公司名称缺失"),
    LACK_OF_SOCIAL_CREDIT_CODE(3408, "统一社会信用码缺失"),
    LACK_OF_GENDER(3409, "性别缺失"),
    WRONG_OPERATOR_ID(3410, "运营主体编码错误"),

    SYSTEM_UNAVAILABLE(4001, "系统暂时不可用"),
    SYSTEM_ERROR(4002, "系统错误"),
    PROCESSING_TIMEOUT(4003, "处理超时"),
    REQUEST_TIMEOUT(4004, "请求超时"),
    DBA_ERROR(4005, "数据库异常"),

    LACK_OF_MAPPING(5001, "mapping关系缺失"),
    GCS_MISMATCH_UAV(5002, "地面站可控机型与无人机类型不一致"),
    PILOT_MISMATCH_UAV(5003, "驾驶员准驾机型与无人机类型不一致"),
    GCS_NOT_SIGN_IN(5004, "地面站未注册"),
    UAV_NOT_IN_NAVIGATION(5005, "无人机未处于航行中"),
    OTHER_BIZ_ERROR(5900, "其他业务失败"),
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
