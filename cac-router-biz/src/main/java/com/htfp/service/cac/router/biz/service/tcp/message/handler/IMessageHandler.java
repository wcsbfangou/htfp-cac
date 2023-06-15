package com.htfp.service.cac.router.biz.service.tcp.message.handler;

import com.htfp.service.cac.router.biz.service.tcp.codec.GcsTcpBaseDataFrame;
import io.netty.channel.Channel;

/**
 * @Author sunjipeng
 * @Date 2023/6/14
 * @Description 描述
 */
public interface IMessageHandler {

    /**
     * 执行处理消息
     *
     * @param channel 通道
     * @param gcsTcpBaseDataFrame 消息
     */
    void execute(Channel channel, GcsTcpBaseDataFrame gcsTcpBaseDataFrame);

    /**
     * @return 消息类型
     */
    String getType();

}
