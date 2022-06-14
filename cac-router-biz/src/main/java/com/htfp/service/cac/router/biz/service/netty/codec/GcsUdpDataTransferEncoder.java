package com.htfp.service.cac.router.biz.service.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author sunjipeng
 * @Date 2022-06-11 14:58
 * @Description 地面站数据透传编码器
 */
@Slf4j
public class GcsUdpDataTransferEncoder extends MessageToByteEncoder<GcsUdpDataTransferDataFrame> {
    @Override
    protected void encode(ChannelHandlerContext ctx, GcsUdpDataTransferDataFrame dataFrame, ByteBuf out) throws Exception {
        if (dataFrame != null) {
            out.writeShort(dataFrame.getMagicCode());
            out.writeByte(dataFrame.getVersion());
            out.writeByte(dataFrame.getSerializationAlgorithm());
            out.writeByte(dataFrame.getType());
            out.writeByte(dataFrame.getGcsIdLength());
            out.writeBytes(dataFrame.getGcsId().getBytes(), 0, dataFrame.getGcsIdLength());
            out.writeByte(dataFrame.getGcsTokenLength());
            out.writeBytes(dataFrame.getGcsToken().getBytes(), 0, dataFrame.getGcsTokenLength());
            out.writeShort(dataFrame.getSequenceId());
            out.writeInt(dataFrame.getLength());
            out.writeBytes(dataFrame.getData().getBytes());
            // TODO: 2022/6/13 测试之后记得删除此log
            log.info("[encode][连接({}) 编码了一条消息({})]", ctx.channel().id(), dataFrame.toString());
        }
    }

}
