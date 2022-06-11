package com.htfp.service.cac.router.biz.service.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.DatagramPacket;
import java.net.InetSocketAddress;

/**
 * @Author sunjipeng
 * @Date 2022-06-07 16:32
 */
@Slf4j
@Component
public class RcsUdpDataTransferClient {

    @Value("${gcsUdpDataTransfer.rcsPort}")
    private int port;

    public void sendData(String rcsIp, byte[] req) throws Exception{
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        try{
            bootstrap.group(eventLoopGroup)
                    .channel(NioDatagramChannel.class)
                    .option(ChannelOption.SO_BROADCAST, true)
                    .handler(new RcsUdpDataTransferClientInboundHandler());
            Channel channel = bootstrap.bind(0).sync().channel();
            InetSocketAddress inetSocketAddress = new InetSocketAddress(rcsIp, port);
            DatagramPacket datagramPacket = new DatagramPacket(req, req.length, inetSocketAddress);
            channel.writeAndFlush(datagramPacket).sync();
            channel.closeFuture().sync();
        } catch (Exception e) {
            log.error("RcsUdpDataTransferClient error", e);
        } finally {
            eventLoopGroup.shutdownGracefully();
        }
    }

}
