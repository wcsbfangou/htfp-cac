package com.htfp.service.cac.router.biz.service.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author sunjipeng
 * @Date 2022-06-11 14:58
 */
@Slf4j
public class GcsUdpDataTransferEncoder extends MessageToByteEncoder<GcsUdpDataTransferDataFrame> {
    @Override
    protected void encode(ChannelHandlerContext ctx, GcsUdpDataTransferDataFrame msg, ByteBuf out) throws Exception {

    }
}
