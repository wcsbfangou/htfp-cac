package com.htfp.service.cac.router.biz.service.tcp.message.request;

import com.htfp.service.cac.router.biz.service.tcp.codec.GcsTcpBaseDataFrame;
import lombok.Data;

/**
 * @Author sunjipeng
 * @Date 2023/6/14
 * @Description 描述
 */
@Data
public class TcpFlightPlanReplyRequest extends GcsTcpBaseDataFrame {

    private byte applyFlightPlanIdLength;
    private String applyFlightPlanId;
    private byte replyFlightPlanIdLength;
    private String replyFlightPlanId;
    private byte flightPlanPass;

}
