package com.htfp.service.oac.common.enums;

/**
 * @Author sunjipeng
 * @Date 2022/12/20
 * @Description 描述
 */
public enum AtcTypeEnum {

    TIME_LIMIT_LEAVE(1, "限时离开"),
    LANDING_IMMEDIATELY(2, "立即降落"),
    HOVER_IMMEDIATELY(3,"立即悬停"),
    UN_HOVER(4,"解除悬停，继续飞行"),
    TAKE_OFF(5, "起飞"),
    RETURN_TO_LAUNCH(6, "返航"),
    STOP_MOTOR_AUTO_CRASH(7,"停止马达，自动坠毁"),
    FLY_TO_DESIGNATED_POS(8,"飞到指定位置"),
    NOT_ALLOW_TAKE_OFF(9, "禁止起飞"),
    ALLOW_TAKE_OFF(10,"允许起飞"),
    DECELERATE(11,"减速"),
    ;

    public final Integer code;
    public final String desc;

    AtcTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static AtcTypeEnum getFromCode(Integer code) {
        for (AtcTypeEnum atcTypeEnum : values()) {
            if (atcTypeEnum.code.equals(code)) {
                return atcTypeEnum;
            }
        }
        return null;
    }

}
