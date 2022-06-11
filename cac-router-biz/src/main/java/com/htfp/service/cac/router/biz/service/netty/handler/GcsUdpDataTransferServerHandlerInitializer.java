package com.htfp.service.cac.router.biz.service.netty.handler;

import com.htfp.service.cac.router.biz.service.netty.server.dispatcher.GcsUdpDataFrameDispatcher;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.dns.DatagramDnsQueryDecoder;
import io.netty.handler.codec.dns.DatagramDnsQueryEncoder;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;


/**
 * @Author sunjipeng
 * @Date 2022-06-11 13:49
 */
public class GcsUdpDataTransferServerHandlerInitializer extends ChannelInitializer<Channel> {

    @Resource
    private GcsUdpDataFrameDispatcher gcsUdpDataFrameDispatcher;

    @Resource
    private NettyServerHandler nettyServerHandler;

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline channelPipeline = ch.pipeline();
        channelPipeline
                // 编码器
                .addLast(new DatagramDnsQueryEncoder())
                // 解码器
                .addLast(new DatagramDnsQueryDecoder())
                // 消息分发器
                .addLast(gcsUdpDataFrameDispatcher)
                // 服务端处理器
                .addLast(nettyServerHandler)
                ;
    }
}
