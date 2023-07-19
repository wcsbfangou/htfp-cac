package com.htfp.service.cac.common.enums;

/**
 * @Author sunjipeng
 * @Date 2023/6/9
 * @Description 描述
 */
public enum UavFlowStatusEnums {

    NORMAL(1, "正常"),
    LIMITED(2, "受限"),
    ;

    public final Integer code;
    public final String desc;

    UavFlowStatusEnums(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static UavFlowStatusEnums getFromCode(Integer code) {
        for (UavFlowStatusEnums uavFlowStatusEnums : values()) {
            if (uavFlowStatusEnums.code.equals(code)) {
                return uavFlowStatusEnums;
            }
        }
        return null;
    }
}
