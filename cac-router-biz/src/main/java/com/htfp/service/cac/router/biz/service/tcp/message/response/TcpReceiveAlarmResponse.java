package com.htfp.service.cac.router.biz.service.tcp.message.response;

import com.htfp.service.cac.router.biz.service.tcp.codec.GcsTcpBaseDataFrame;
import lombok.Data;

/**
 * @Author sunjipeng
 * @Date 2023/6/14
 * @Description 描述
 */
@Data
public class TcpReceiveAlarmResponse extends GcsTcpBaseDataFrame {

    private byte success;
    private int code;
    private int messageLength;
    private String message;

}