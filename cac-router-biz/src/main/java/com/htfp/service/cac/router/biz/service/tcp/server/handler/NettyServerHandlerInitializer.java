package com.htfp.service.cac.router.biz.service.tcp.server.handler;

import com.htfp.service.cac.router.biz.service.tcp.codec.GcsTcpBaseDataDecoder;
import com.htfp.service.cac.router.biz.service.tcp.codec.GcsTcpBaseDataEncoder;
import com.htfp.service.cac.router.biz.service.tcp.message.MessageDispatcher;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @Author sunjipeng
 * @Date 2023/6/12
 * @Description 描述
 */
@Component
public class NettyServerHandlerInitializer  extends ChannelInitializer<Channel> {

    /**
     * 心跳超时时间
     */
    private static final Integer READ_TIMEOUT_SECONDS = 30;

    @Resource
    private MessageDispatcher messageDispatcher;
    @Resource
    private NettyServerHandler nettyServerHandler;

    @Override
    protected void initChannel(Channel ch) {
        // 获得 Channel 对应的 ChannelPipeline
        ChannelPipeline channelPipeline = ch.pipeline();
        // 添加一堆 NettyServerHandler 到 ChannelPipeline 中
        channelPipeline
                // 空闲检测
                .addLast(new ReadTimeoutHandler(READ_TIMEOUT_SECONDS, TimeUnit.SECONDS))
                // 编码器
                .addLast(new GcsTcpBaseDataEncoder())
                // 解码器
                .addLast(new GcsTcpBaseDataDecoder())
                // 消息分发器
                .addLast(messageDispatcher)
                // 服务端处理器
                .addLast(nettyServerHandler)
        ;
    }
}
