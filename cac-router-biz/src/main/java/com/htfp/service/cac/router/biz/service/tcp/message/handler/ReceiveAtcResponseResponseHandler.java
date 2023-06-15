package com.htfp.service.cac.router.biz.service.tcp.message.handler;

import com.htfp.service.cac.common.enums.dataFrame.GcsTcpBaseDataFrameTypeEnum;
import com.htfp.service.cac.router.biz.service.tcp.codec.GcsTcpBaseDataFrame;
import io.netty.channel.Channel;
import org.springframework.stereotype.Component;

/**
 * @Author sunjipeng
 * @Date 2023/6/15
 * @Description 描述
 */
@Component
public class ReceiveAtcResponseResponseHandler implements IMessageHandler {

    /**
     * 执行处理消息
     *
     * @param channel 通道
     * @param gcsTcpBaseDataFrame 消息
     */
    @Override
    public void execute(Channel channel, GcsTcpBaseDataFrame gcsTcpBaseDataFrame) {

    }

    /**
     * @return 消息类型
     */
    @Override
    public String getType() {
        return GcsTcpBaseDataFrameTypeEnum.RECEIVE_ATC_RESPONSE.getName();
    }
}

