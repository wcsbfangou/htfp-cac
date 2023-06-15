package com.htfp.service.cac.router.biz.service.tcp.codec;


import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author sunjipeng
 * @Date 2023/6/12
 * @Description 描述
 */
@Slf4j
public class GcsTcpBaseDataEncoder extends MessageToByteEncoder<GcsTcpBaseDataFrame> {

    @Override
    protected void encode(ChannelHandlerContext ctx, GcsTcpBaseDataFrame dataFrame, ByteBuf buf) {

        if (dataFrame != null) {
            buf.writeShort(dataFrame.getMagicCode());
            buf.writeByte(dataFrame.getVersion());
            buf.writeByte(dataFrame.getSerializationAlgorithm());
            buf.writeByte(dataFrame.getType());
            buf.writeByte(dataFrame.getGcsIdLength());
            if (dataFrame.getGcsIdLength() > 0) {
                buf.writeBytes(dataFrame.getGcsId().getBytes(), 0, dataFrame.getGcsIdLength());
            }
            buf.writeByte(dataFrame.getGcsTokenLength());
            if (dataFrame.getGcsTokenLength() > 0) {
                buf.writeBytes(dataFrame.getGcsToken().getBytes(), 0, dataFrame.getGcsTokenLength());
            }
            buf.writeByte(dataFrame.getUavIdLength());
            if (dataFrame.getUavIdLength() > 0) {
                buf.writeBytes(dataFrame.getUavId().getBytes(), 0, dataFrame.getUavIdLength());
            }
            if (dataFrame.getReadableDataBytesLength() > 0) {
                buf.writeBytes(dataFrame.getReadableDataBytes(), 0, dataFrame.getReadableDataBytesLength());
            }
            // TODO: 2023/6/13 测试之后记得删除此log
            log.info("[encode][连接({}) 编码了一条消息({})]", ctx.channel().id(), dataFrame.toString());
        }
    }

}
