package com.htfp.service.cac.router.biz.service.netty.handler;

import com.htfp.service.cac.router.biz.service.netty.codec.GcsUdpDataTransferDecoder;
import com.htfp.service.cac.router.biz.service.netty.codec.GcsUdpDataTransferEncoder;
import com.htfp.service.cac.router.biz.service.netty.server.dispatcher.GcsUdpDataFrameDispatcher;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


/**
 * @Author sunjipeng
 * @Date 2022-06-11 13:49
 * @Description Udp服务端Handler处理链初始化
 */
@Component
public class GcsUdpDataTransferServerHandlerInitializer extends ChannelInitializer<Channel> {

    @Resource
    private GcsUdpDataFrameDispatcher gcsUdpDataFrameDispatcher;

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline channelPipeline = ch.pipeline();
        channelPipeline
                // 编码器
                .addLast(new GcsUdpDataTransferEncoder())
                // 解码器
                .addLast(new GcsUdpDataTransferDecoder())
                // 消息分发器
                .addLast(gcsUdpDataFrameDispatcher)
        ;
    }
}
