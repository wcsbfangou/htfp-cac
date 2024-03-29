package com.htfp.service.oac.biz.model.inner.request;

import lombok.Data;

/**
 * @Author sunjipeng
 * @Date 2022/12/21
 * @Description 描述
 */
@Data
public class SendAlarmRequest {

    private String cpn;
    private Integer alarmLevel;
    private String content;
    private String effectTime;

}
