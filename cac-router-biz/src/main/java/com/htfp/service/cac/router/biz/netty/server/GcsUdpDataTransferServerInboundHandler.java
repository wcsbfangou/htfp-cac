package com.htfp.service.cac.router.biz.netty.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author sunjipeng
 * @Date 2022-06-07 14:33
 */
@Slf4j
public class GcsUdpDataTransferServerInboundHandler extends SimpleChannelInboundHandler<DatagramPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, DatagramPacket packet) throws Exception {
        channelHandlerContext.close();
    }
}
