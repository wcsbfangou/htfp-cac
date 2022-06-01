package com.htfp.service.cac.common.enums;

/**
 * @Author sunjipeng
 * @Date 2022-06-01 20:25
 */
public enum CommandResultEnum {

    SUCCESS(0, true, "成功"),
    FAIL(1, false,"失败"),
    ;

    public final Integer code;
    public final Boolean result;
    public final String desc;

    CommandResultEnum(Integer code, Boolean result, String desc) {
        this.code = code;
        this.result = result;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public Boolean getResult() {
        return result;
    }

    public String getDesc() {
        return desc;
    }

    public static CommandResultEnum getFromCode(Integer code) {
        for (CommandResultEnum commandResultEnum : values()) {
            if (commandResultEnum.code.equals(code)) {
                return commandResultEnum;
            }
        }
        return null;
    }

    public static CommandResultEnum getFromResult(Boolean result) {
        for (CommandResultEnum commandResultEnum : values()) {
            if (commandResultEnum.result.equals(result)) {
                return commandResultEnum;
            }
        }
        return null;
    }
}
