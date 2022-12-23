package com.htfp.service.oac.client.enums;

/**
 * @Author sunjipeng
 * @Date 2022/12/20
 * @Description 描述
 */
public enum FlightMissionTypeEnum {

    ILLEGAL_CONSTRUCTION_PATROL(1, "违法建设巡查"),
    MARITIME_AFFAIRS_PATROL(2, "海事巡查"),
    GEOLOGICAL_DISASTER_RESCUE_IN_FLOOD_SEASON_FLIGHT(3,"汛期地质灾害抢险排查"),
    TRAINING_FLIGHT(4,"训练飞行"),
    TEST_FLIGHT(5, "试飞"),
    SKILLED_FLIGHT(6, "熟练飞行"),
    TRANSITION(7,"转场"),
    PERSONAL_ENTERTAINMENT(8,"个人娱乐"),
    AIR_SHOW(9, "航空表演"),
    SKYWRITING(10, "空中广告"),
    AIR_PHOTOGRAPHY(11,"空中拍照"),
    PARACHUTING_FLIGHT_SERVICE(12,"跳伞飞行服务"),
    AERIAL_PHOTOGRAPHY(13, "航空摄影"),
    AIR_TRAVEL(14, "空中游览"),
    PILOT_TRAINING(15,"驾驶员培训"),
    CHARTER_FLIGHT(16,"包机飞行"),
    PETROLEUM_FLIGHT_SERVICE(17, "石油服务"),

    METEOROLOGICAL_OBSERVATION(18, "气象探测"),
    SCIENTIFIC_EXPERIMENT(19,"科学实验"),
    MARINE_MONITORING(20,"海洋监测"),
    HELICOPTER_PILOT(21, "直升机引航"),
    URBAN_FIRE_CONTROL(22, "城市消防"),
    AERIAL_PATROL(23,"空中巡查"),
    MEDICAL_AID(24,"医疗救护"),
    ELECTRICAL_WORK(25, "电力工作"),
    FISHERY_FLIGHT(26, "渔业飞行"),
    AERIAL_SPRAY(27,"航空喷洒"),
    AERIAL_FOREST_PROTECTION(28,"航空护林"),
    AERIAL_PROSPECT(29, "航空探矿"),
    ARTIFICIAL_PRECIPITATION(30, "人工降水"),
    ROAD_AND_BRIDGE_PATROL(31, "路桥巡检"),
    OTHERS(32, "其他"),
    ;

    public final Integer code;
    public final String desc;

    FlightMissionTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static FlightMissionTypeEnum getFromCode(Integer code) {
        for (FlightMissionTypeEnum flightMissionTypeEnum : values()) {
            if (flightMissionTypeEnum.code.equals(code)) {
                return flightMissionTypeEnum;
            }
        }
        return null;
    }
}
