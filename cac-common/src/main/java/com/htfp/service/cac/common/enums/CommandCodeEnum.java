package com.htfp.service.cac.common.enums;

/**
 * @Author sunjipeng
 * @Date 2022-05-19 9:38
 * @Description 指控指令枚举
 */
public enum CommandCodeEnum {

    CLIMB(1001, "爬升"),
    DIVE(1002, "俯冲"),
    UPPER_RUDDER(1003, "上舵"),
    LOWER_RUDDER(1004, "下舵"),
    LIFT_UP_HORIZONTAL(1005, "平尾抬升"),
    RECOVER_HORIZONTAL(1006, "平尾恢复"),

    LONGITUDINAL_HORIZONTAL(2001, "纵平"),
    RAISE_HEIGHT(2002, "升高"),
    LOWER_HEIGHT(2003, "降高"),

    RETRACT_FLAP(3001, "收襟翼"),
    FLAP_ANGLE_MINUS_FIVE_DEGREE(3002, "襟翼-5°"),
    FLAP_ANGLE_PLUS_FIVE_DEGREE(3003, "襟翼+5°"),

    ROLL_CROSSWISE_HORIZONTAL(4001, "横平"),
    ROLL_LEFT_PLATEN(4002, "左盘"),
    ROLL_RIGHT_PLATEN(4003, "右盘"),
    ROLL_LEFT_CORRECTION(4004, "左修"),
    ROLL_RIGHT_CORRECTION(4005, "右修"),

    COURSE_ORIENTATION(5001, "定向"),

    ENGINE_IGNITION(6001, "横平"),
    ENGINE_EXPLOSION_PROOF_CAP_INSPECTION(6002, "爆炸帽检测"),
    ENGINE_CRUISING(6003, "巡航"),
    ENGINE_IDLING(6004, "怠速"),
    ENGINE_SHUT_DOWN(6005, "关车"),
    ENGINE_UP_SHIFT(6006, "档位增"),
    ENGINE_DOWN_SHIFT(6007, "档位减"),
    ENGINE_THROTTLE_INCREASE(6008, "油门增"),
    ENGINE_THROTTLE_REDUCE(6009, "油门减"),
    ENGINE_RUN(6010, "起车"),
    ENGINE_WARN_UP(6011, "暖车"),
    ENGINE_TEST_RUN(6012, "试车"),

    ROUTE_ONE(7001, "航线1"),
    ROUTE_TWO(7002, "航线2"),
    ROUTE_THREE(7003, "航线3"),
    FENCE_ON(7004, "围栏开启"),
    FENCE_OFF(7005, "围栏关闭"),
    GLIDING_DOWN(7006, "滑降着陆"),
    SCALE_CONTROL(7007, "比例遥控"),
    THREE_DIMENSIONAL_FLIGHT(7008, "三维飞行"),

    PRE_TAKE_OFF(8001, "预起飞"),
    TAKE_OFF(8002, "起飞"),
    PASS_FLY(8003, "通场飞行"),
    GO_AROUND(8004, "复飞"),
    LANDING_ALLOW(8005, "允许着陆"),
    MANUALLY_TOUCHDOWN(8006, "手动接地"),
    COMMAND_ROLLING(8007, "指令滑跑"),
    STOP_ROLLING(8008, "终止滑跑"),
    BRAKE(8009, "刹车"),
    RELEASE_BRAKE(8010, "松刹车"),
    BRAKE_INCREASE(8011, "刹车增"),
    BRAKE_REDUCTION(8012, "刹车减"),
    GROUND_REVERSE(8013, "地面反向"),

    POWER_SOFT_OFF(9001, "电源软关"),
    LOAD_ON(9002, "载荷开"),
    LOAD_OFF(9003, "载荷关"),
    NIGHT_SERVICE_ON(9004, "夜航开"),
    NIGHT_SERVICE_OFF(9005, "夜航关"),
    MAIN_BRAKE(9006, "主刹车"),
    SPARE_BRAKE(9007, "备刹车"),
    AIR_PLASTIC_TUBE_HEATING_ON(9008, "空速管加温开"),
    AIR_PLASTIC_TUBE_HEATING_OFF(9009, "空速管加温关"),
    DYNAMO_ON(9010, "发电机开"),
    DYNAMO_OFF(9011, "发电机关"),

    SILENT_FLIGHT(10001, "静默飞行"),

    PRE_COMMAND(11001, "预指令"),
    OIL_LEVEL_RESET(11002, "油量清零"),
    UAV_TOTAL_FLIGHT_TIME_RESET(11003, "总飞行时间清零"),
    ENGINE_TOTAL_RUNNING_TIME_RESET(11004, "发动机总运行时间清零"),

    SWITCH_IMAGE(12001, "图像切换"),
    PICTURE_IN_PICTURE_ON(12002, "画中画开"),
    PICTURE_IN_PICTURE_OFF(12003, "画中画关"),
    SMALL_INFRARED_FIELD_VIEW_OF(12004, "红外小视场"),
    SMALL_INFRARED_FIELD_OF_VIEW(12005, "红外大视场"),
    TRACK(12006, "跟踪"),

    DOOR_OPEN(13001, "舱门开"),
    DOOR_STOP(13002, "舱门停"),
    DOOR_CLOSE(13003, "舱门关"),
    DOOR_SELF_CHECK(13004, "舱门自检"),

    CONSTANT_SPEED_ON(14001, "定速飞行"),
    CONSTANT_SPEED_OFF(14002, "取消定速"),
    AIR_SPEED_INCREASE(14003, "空速上调"),
    AIR_SPEED_DECREASE(14004, "空速下调"),

    GIMBAL_BEARING_PLUS(15001, "方位+"),
    GIMBAL_BEARING_MINUS(15002, "方位-"),
    GIMBAL_PITCH_PLUS(15003, "俯仰+"),
    GIMBAL_PITCH_MINUS(15004, "俯仰-"),
    GIMBAL_SCAN(15005, "扫描"),
    GIMBAL_LOCK(15006, "锁定"),
    GIMBAL_RESET(15007, "复位"),
    GIMBAL_ZOOM_IN(15008, "变焦拉近"),
    GIMBAL_ZOOM_OUT(15009, "变焦推远"),

    FLIGHT_CONTROL_A_FORCE_SLAVE_CONTROL(16001, "飞控A强制从控"),
    FLIGHT_CONTROL_B_FORCE_SLAVE_CONTROL(16002, "飞控B强制从控"),
    FLIGHT_CONTROL_ABC_RELEASE_FORCE_SLAVE_CONTROL(16003, "飞控ABC解除强制从控"),

    ENGINE_MANAGER_A_FORCE_PROHIBITION(17001, "发管A强制禁止"),
    ENGINE_MANAGER_A_RELEASE_FORCE_PROHIBITION(17002, "发管A解除强制禁止"),

    SERVO_SELF_CHECK(18001, "舵机自检"),
    SERVO_TERMINATION_SELF_CHECK(18002, "舵机终止自检"),

    TWO_MB_RATE(19001, "2M速率"),
    FOUR_MB_RATE(19002, "4M速率"),
    POD_CHANNEL_ONE(19003, "吊舱通道1"),
    POD_CHANNEL_TWO(19004, "吊舱通道2"),
    IMAGE_STITCHING_0_3(19005, "0-3号图像拼接"),
    IMAGE_STITCHING_4_6(19006, "4-6号图像拼接"),
    RECORD_ON(19007, "记录"),
    RECORD_OFF(19008, "停止记录"),
    ERASE_DATA(19009, "清空数据"),
    QUERY_CAPACITY(19010, "容量查询"),
    READ_THE_VERSION_NUMBER(19011, "读版本号"),
    VIDEO_COMPUTER_RESET(19012, "视频计算机复位"),

    VIDEO_ON(20001, "视频开"),
    VIDEO_OFF(20002, "视频关"),
    TASK_MANAGER_POWER_ON(20003, "任管供电开"),
    TASK_MANAGER_POWER_OFF(20004, "任管供电关"),
    SATELLITE_CHANNEL_ONE_ON(20005, "卫通Ⅰ开"),
    SATELLITE_CHANNEL_ONE_OFF(20006, "卫通Ⅰ关"),
    VOICE_RADIO_ON(20007, "语音电台开"),
    VOICE_RADIO_OFF(20008, "语音电台关"),
    TRANSPONDER_ON(20009, "应答机开"),
    TRANSPONDER_OFF(20010, "应答机关"),
    SATELLITE_CHANNEL_TWO_ON(20011, "卫通Ⅱ开"),
    SATELLITE_CHANNEL_TWO_OFF(20012, "卫通Ⅱ关"),

    OIL_DRAIN_ENABLE(21001, "放油使能"),
    OIL_DRAIN_STOP(21002, "放油禁止"),
    OIL_DRAIN_ON(21003, "放油开"),
    OIL_DRAIN_OFF(21004, "放油关"),
    AIR_INTAKE_FILTER_OFF(21005, "进气道过滤网关"),
    AIR_INTAKE_FILTER_ON(21006, "进气道过滤网开"),
    FUEL_MANAGEMENT_EXPERT_MODE_ON(21007, "燃油管理专家模式进入"),
    FUEL_MANAGEMENT_EXPERT_MODE_OFF(21008, "燃油管理专家模式退出"),
    FIRE_EXTINGUISH_ENABLE_OFF(21009, "灭火使能关"),
    FIRE_EXTINGUISH_ENABLE_ON(21010, "灭火使能"),
    FIRE_EXTINGUISH_OFF(21011, "灭火关"),
    FIRE_EXTINGUISH_ON(21012, "灭火开"),
    MAGNETO_OFF(21013, "磁电机关"),
    MAGNETO_ON(21014, "磁电机开"),
    RELEASE_SHUT_DOWN(21015, "解除关车"),
    AIR_RESTART(21016, "空中再启动"),
    NO_REMOTE_COMMAND(21017, "无遥控指令"),
    PITCH_PLUS(21018, "桨距增"),
    PITCH_MINUS(21019, "桨距减"),
    FISH_SCALES_PLATE_ON(21020, "鱼鳞板开"),
    FISH_SCALES_PLATE_OFF(21021, "鱼鳞板关"),
    FISH_SCALES_PLATE_AUTO(21022, "鱼鳞板自动"),
    FISH_SCALES_PLATE_STOP(21023, "鱼鳞板停止"),
    LUBRICANT_RADIATOR_ON(21024, "滑油散热器开"),
    LUBRICANT_RADIATOR_OFF(21025, "滑油散热器关"),
    LUBRICANT_RADIATOR_AUTO(21026, "滑油散热器自动"),
    LUBRICANT_RADIATOR_STOP(21027, "滑油散热器停止"),
    MAGNETO_LEFT_ON_RIGHT_OFF(21028, "磁电机左开右关"),
    MAGNETO_LEFT_OFF_RIGHT_ON(21029, "磁电机右开左关"),
    ;


    public final Integer code;
    public final String desc;

    CommandCodeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static CommandCodeEnum getFromCode(Integer code) {
        for (CommandCodeEnum commandCodeEnum : values()) {
            if (commandCodeEnum.code.equals(code)) {
                return commandCodeEnum;
            }
        }
        return null;
    }
}
