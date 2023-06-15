package com.htfp.service.cac.router.biz.service.tcp.message.request;

import com.htfp.service.cac.router.biz.service.tcp.codec.GcsTcpBaseDataFrame;
import lombok.Data;

/**
 * @Author sunjipeng
 * @Date 2023/6/14
 * @Description 描述
 */
@Data
public class TcpReceiveAlarmRequest extends GcsTcpBaseDataFrame {

    private int alarmLevel;
    private byte contentLength;
    private String content;
    private long effectTime;
}
