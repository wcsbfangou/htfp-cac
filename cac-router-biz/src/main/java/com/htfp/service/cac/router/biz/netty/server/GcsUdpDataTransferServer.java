package com.htfp.service.cac.router.biz.netty.server;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Author sunjipeng
 * @Date 2022-06-07 14:02
 */
@Slf4j
@Component
public class GcsUdpDataTransferServer {

    @Value("${gcsUdpDataTransfer.routerPort}")
    private int port;

    public void startServer(){

        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();

        try{
            Bootstrap serverBootStrap = new Bootstrap();
            serverBootStrap.group(eventLoopGroup)
                    .channel(NioDatagramChannel.class)
                    .option(ChannelOption.SO_BROADCAST, true)
                    .handler(new GcsUdpDataTransferServerInboundHandler());
            log.info("GcsUdpDataTransferServer start");
            ChannelFuture channelFuture = serverBootStrap.bind(port).sync();
            channelFuture.channel().closeFuture().sync();
        }catch(Exception e){
            log.error("GcsUdpDataTransferServer start error", e);
        } finally {
            log.info("GcsUdpDataTransferServer close");
            eventLoopGroup.shutdownGracefully();
        }
    }
}
