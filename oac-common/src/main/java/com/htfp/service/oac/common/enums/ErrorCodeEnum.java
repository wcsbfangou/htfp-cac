package com.htfp.service.oac.common.enums;

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
    LACK_OF_IP(3005, "IP地址缺失"),
    LACK_OF_ORG_TYPE(3006, "组织机构类型缺失"),
    LACK_OF_ORG_NAME(3007, "组织机构名称缺失"),
    LACK_OF_LNG(3008, "经度缺失"),
    LACK_OF_LAT(3009, "纬度缺失"),
    LACK_OF_ALT(3010, "高度缺失"),
    LACK_OF_APPLICANT_TYPE(3011, "申请者类型缺失"),
    LACK_OF_ORGANIZATION(3012, "组织缺失"),
    LACK_OF_PERSON(3013, "人员缺失"),
    LACK_OF_AIRSPACE_NUM(3014, "空域代号缺失"),
    LACK_OF_ROUTE_POINT(3015, "航线点缺失"),
    LACK_OF_TAKE_OFF_AIRPORT(3016, "起飞机场编码缺失"),
    LACK_OF_LANDING_AIRPORT(3017, "降落机场编码缺失"),
    LACK_OF_TAKE_OFF_SITE(3018, "起飞位点缺失"),
    LACK_OF_LANDING_SITE(3019, "降落位点缺失"),
    LACK_OF_START_TIME(3020, "开始时间缺失"),
    LACK_OF_END_TIME(3021, "结束时间缺失"),
    LACK_OF_TIME(3022, "时间缺失"),
    LACK_OF_OPERATION_SCENARIO(3023, "运行场景缺失"),
    LACK_OF_MISSION_TYPE(3024, "飞行任务性质缺失"),
    LACK_OF_GROUND_SPEED(3025, "地速缺失"),
    LACK_OF_ABSOLUTE_SPEED(3026, "地速缺失"),
    LACK_OF_RELATIVE_HEIGHT(3027, "相对高度缺失"),
    LACK_OF_HEIGHT(3028, "高度缺失"),
    LACK_OF_TRUE_COURSE(3029, "真航向缺失"),
    LACK_OF_PITCH_ANGLE(3030, "俯仰角缺失"),
    LACK_OF_ROLL_ANGLE(3031, "横滚角缺失"),
    LACK_OF_UAV_REPORT_CODE(3132, "无人机上报数据编码缺失"),


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
    LACK_OF_PILOT_INFO(3205, "驾驶员信息缺失"),

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
    LACK_OF_NATIONALITY(3410, "国籍缺失"),
    WRONG_OPERATOR_ID(3411, "运营主体编码错误"),

    LACK_OF_APPLY_AIRSPACE_ID(3501, "空域申请Id缺失"),
    LACK_OF_REPLY_AIRSPACE_ID(3502, "空域审批Id缺失"),
    LACK_OF_APPLY_AIRSPACE_RESULT(3503, "空域审批结果缺失"),
    LACK_OF_APPLY_FLIGHT_PLAN_ID(3504, "飞行计划申请Id缺失"),
    LACK_OF_REPLY_FLIGHT_PLAN_ID(3505, "飞行计划审批Id缺失"),
    LACK_OF_APPLY_FLIGHT_PLAN_RESULT(3506, "飞行计划审批结果缺失"),
    LACK_OF_APPLY_VERIFY_ID(3507, "无人机接入申请Id缺失"),
    LACK_OF_REPLY_VERIFY_ID(3508, "无人机接入审批Id缺失"),
    LACK_OF_APPLY_VERIFY_RESULT(3509, "无人机接入结果缺失"),
    LACK_OF_APPLY_FLY_ID(3510, "放飞申请Id缺失"),
    LACK_OF_REPLY_FLY_ID(3511, "放飞审批Id缺失"),
    LACK_OF_APPLY_FLY_RESULT(3512, "放飞审批结果缺失"),
    LACK_OF_PLAN_STATUS(3513, "飞行计划状态缺失"),
    LACK_OF_AIRPORT_ID(3514, "机场ID缺失"),
    LACK_OF_ALARM_ID(3515, "告警ID缺失"),
    LACK_OF_ATC_TYPE(3516, "管制类型缺失"),
    WRONG_APPLY_AIRSPACE_ID(3517, "空域申请Id错误"),
    WRONG_REPLY_AIRSPACE_ID(3518, "空域审批Id错误"),
    WRONG_APPLY_FLIGHT_PLAN_ID(3519, "飞行计划申请Id错误"),
    WRONG_REPLY_FLIGHT_PLAN_ID(3520, "飞行计划审批Id错误"),
    WRONG_APPLY_VERIFY_ID(3521, "无人机接入申请Id错误"),
    WRONG_REPLY_VERIFY_ID(3522, "无人机接入审批Id错误"),
    WRONG_APPLY_FLY_ID(3523, "放飞申请Id错误"),
    WRONG_REPLY_FLY_ID(3524, "放飞审批Id错误"),
    LACK_OF_ALARM_TYPE(3525, "告警类型缺失"),
    LACK_OF_ALARM_CONTENT(3526, "告警内容缺失"),
    WRONG_AIRPORT_ID(3527, "机场编码缺错误"),

    SYSTEM_UNAVAILABLE(4001, "系统暂时不可用"),
    SYSTEM_ERROR(4002, "系统错误"),
    PROCESSING_TIMEOUT(4003, "处理超时"),
    REQUEST_TIMEOUT(4004, "请求超时"),
    DBA_ERROR(4005, "数据库异常"),

    LACK_OF_MAPPING(5001, "mapping关系缺失"),
    GCS_MISMATCH_UAV(5002, "地面站可控机型与无人机类型不一致"),
    PILOT_MISMATCH_UAV(5003, "驾驶员准驾机型与无人机类型不一致"),
    GCS_NOT_SIGN_IN(5004, "地面站未上线"),
    UAV_NOT_IN_NAVIGATION(5005, "无人机未处于航行中"),
    UAV_NOT_REGISTER(5006, "无人机未注册"),
    PILOT_NOT_REGISTER(5007, "驾驶员未注册"),
    OPERATOR_NOT_REGISTER(5008, "运营主体未注册"),
    UAV_HAS_VERIFIED(5009, "无人机已完成校验，无需重复校验"),
    AIRSPACE_NOT_APPROVED(5010, "空域申请未通过"),
    FLIGHT_PLAN_NOT_APPROVED(5011, "飞行计划未通过"),
    FlY_NOT_APPROVED(5012, "放飞申请未通过"),
    IN_FLIGHT_PLAN(5013, "无人机正在执行飞行计划中"),
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
