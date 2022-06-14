package com.htfp.service.cac.router.biz.service.netty.server;

import com.htfp.service.cac.router.biz.service.netty.handler.GcsUdpDataTransferServerHandlerInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

/**
 * @Author sunjipeng
 * @Date 2022-06-07 14:02
 * @Description 地面站数据透传的服务端
 */
@Slf4j
@Component
public class GcsUdpDataTransferServer {

    @Value("${gcsUdpDataTransfer.routerPort}")
    private int port;

    @Resource
    private GcsUdpDataTransferServerHandlerInitializer gcsUdpDataTransferServerHandlerInitializer;

    private EventLoopGroup eventLoopGroup = new NioEventLoopGroup();

    private Channel channel;

    /**
     * 启动 netty server
     */
    @PostConstruct
    public void startServer() {
        Bootstrap serverBootStrap = new Bootstrap();
        try {
            serverBootStrap.group(eventLoopGroup)
                    .channel(NioDatagramChannel.class)
                    .option(ChannelOption.SO_BROADCAST, true)
                    .handler(gcsUdpDataTransferServerHandlerInitializer);
            log.info("GcsUdpDataTransferServer starting");
            ChannelFuture channelFuture = serverBootStrap.bind(port).sync();
            if (channelFuture.isSuccess()) {
                channel = channelFuture.channel();
                log.info("GcsUdpDataTransferServer started at port:{}", port);
            }
        } catch (Exception e) {
            eventLoopGroup.shutdownGracefully();
            log.error("GcsUdpDataTransferServer start error", e);
        }
    }

    /**
     * 关闭netty server
     */
    @PreDestroy
    public void shutdown() {
        if (channel != null) {
            channel.close();
        }
        eventLoopGroup.shutdownGracefully();
    }
}
