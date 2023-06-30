package com.htfp.service.cac.common.enums;

/**
 * @Author sunjipeng
 * @Date 2022/12/22
 * @Description 描述
 */
public enum ApplyStatusEnum {

    PENDING(0, "待审批"),
    APPROVED(1, "审批通过"),
    UNAPPROVED(2, "审批未通过"),
    COMPLETE(3, "完成"),
    OVERDUE(4, "过期"),
    REVOKE(5, "撤销"),
    CANCEL(6, "取消"),
    ;


    public final Integer code;
    public final String desc;

    ApplyStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static ApplyStatusEnum getFromCode(Integer code) {
        for (ApplyStatusEnum applyStatusEnum : values()) {
            if (applyStatusEnum.code.equals(code)) {
                return applyStatusEnum;
            }
        }
        return null;
    }

}
