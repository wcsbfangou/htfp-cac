package com.htfp.service.cac.common.enums;

/**
 * @Author sunjipeng
 * @Date 2022/12/8
 * @Description 描述
 */
public enum OperatorTypeEnum {

    HUMAN(1, "运营人"),
    COMPANY(2, "运营企业"),
    GOV(3,"政府监管部门"),
    OTHERS(4,"其他"),
    ;

    public final Integer code;
    public final String desc;

    OperatorTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static OperatorTypeEnum getFromCode(Integer code) {
        for (OperatorTypeEnum operatorTypeEnum : values()) {
            if (operatorTypeEnum.code.equals(code)) {
                return operatorTypeEnum;
            }
        }
        return null;
    }
}
