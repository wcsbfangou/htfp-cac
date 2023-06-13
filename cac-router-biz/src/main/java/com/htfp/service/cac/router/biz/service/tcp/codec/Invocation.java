package com.htfp.service.cac.router.biz.service.tcp.codec;

/**
 * @Author sunjipeng
 * @Date 2023/6/12
 * @Description 描述
 */
public class Invocation {

    /**
     * 类型
     */
    private String type;
    /**
     * 消息，JSON 格式
     */
    private String message;

    // 空构造方法
    public Invocation() {
    }

    public Invocation(String type, String message) {
        this.type = type;
        this.message = message;
    }

/*    public Invocation(String type, Message message) {
        this.type = type;
        this.message = JSON.toJSONString(message);
    }*/

    public String getType() {
        return type;
    }

    public Invocation setType(String type) {
        this.type = type;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Invocation setMessage(String message) {
        this.message = message;
        return this;
    }

    @Override
    public String toString() {
        return "Invocation{" +
                "type='" + type + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
