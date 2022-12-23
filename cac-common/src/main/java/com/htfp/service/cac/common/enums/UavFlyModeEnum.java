package com.htfp.service.cac.common.enums;

/**
 * @Author sunjipeng
 * @Date 2022/12/20
 * @Description 描述
 */
public enum UavFlyModeEnum {

    READY(1, "准备起飞"),
    TAKE_OFF(2, "正在起飞"),
    HOLD(3,"正在盘旋"),
    MISSION(4,"正在按航线飞行"),
    RETURN_TO_LAUNCH(5, "返航"),
    LAND(6, "降落"),
    OFF_BOARD(7,"外部接管中"),
    MANUAL(8,"手动模式"),
    MAY_DAY(9, "应急状态"),
    FELLOW_ME(10,"跟随动态位置"),
    UNKNOWN(300,"未知模式"),
    ;

    public final Integer code;
    public final String desc;

    UavFlyModeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static UavFlyModeEnum getFromCode(Integer code) {
        for (UavFlyModeEnum uavFlyModeEnum : values()) {
            if (uavFlyModeEnum.code.equals(code)) {
                return uavFlyModeEnum;
            }
        }
        return null;
    }

}
