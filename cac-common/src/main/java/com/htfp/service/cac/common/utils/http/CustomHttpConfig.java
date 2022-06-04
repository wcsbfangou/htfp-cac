package com.htfp.service.cac.common.utils.http;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author sunjipeng
 * @Date 2022-06-02 16:51
 */
@Getter
@Setter
public class CustomHttpConfig {

    private int connectionRequestTime = 5 * 1000;
    private int connectTime = 30 * 1000;
    private int socketTime = 30 * 1000;

    public CustomHttpConfig() {

    }

    public CustomHttpConfig(int connectTime, int socketTime) {
        this.connectTime = connectTime;
        this.socketTime = socketTime;
    }
}
