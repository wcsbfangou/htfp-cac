package com.htfp.service.cac.router.biz.service.netty.codec;

import com.htfp.service.cac.common.constant.UdpDataFrameConstant;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.CorruptedFrameException;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @Author sunjipeng
 * @Date 2022-06-11 14:26
 */
@Slf4j
public class GcsUdpDataTransferDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        GcsUdpDataTransferDataFrame gcsUdpDataTransferDataFrame = new GcsUdpDataTransferDataFrame();
        // 标记当前读取位置
        in.markReaderIndex();
        // 判断是否大于最小长度
        if (in.readableBytes() <= UdpDataFrameConstant.DATA_FRAME_MIN_LENGTH) {
            log.error("[decode][连接({}) 解析消息失败，数据可读长度小于数据帧最小长度in={}]", ctx.channel().id(), in.toString());
            return;
        }
        gcsUdpDataTransferDataFrame.setMagicCode(in.readShort());
        gcsUdpDataTransferDataFrame.setVersion(in.readByte());
        gcsUdpDataTransferDataFrame.setSerializationAlgorithm(in.readByte());
        gcsUdpDataTransferDataFrame.setType(in.readByte());
        // 获取地面站编号长度以及地面站编号
        byte gcsIdLengthByte = in.readByte();
        int gcsIdLength = Byte.toUnsignedInt(gcsIdLengthByte);
        if (in.readableBytes() < gcsIdLength) {
            in.resetReaderIndex();
            log.error("[decode][连接({}) 解析消息失败，数据剩余可读长度小于gcsId长度，in={}]", ctx.channel().id(), in.toString());
            return;
        }
        byte[] gcsIdByteArray = new byte[gcsIdLength];
        in.readBytes(gcsIdByteArray);
        gcsUdpDataTransferDataFrame.setGcsIdLength(gcsIdLengthByte);
        gcsUdpDataTransferDataFrame.setGcsId(new String(gcsIdByteArray));
        // 获取地面站Token长度以及地面站Token
        byte gcsTokenLengthByte = in.readByte();
        int gcsTokenLength = Byte.toUnsignedInt(gcsTokenLengthByte);
        if (in.readableBytes() < gcsTokenLength) {
            in.resetReaderIndex();
            log.error("[decode][连接({}) 解析消息失败，数据剩余可读长度小于gcsToken长度，in={}]", ctx.channel().id(), in.toString());
            return;
        }
        byte[] gcsTokenByteArray = new byte[gcsTokenLength];
        in.readBytes(gcsTokenByteArray);
        gcsUdpDataTransferDataFrame.setGcsTokenLength(gcsTokenLengthByte);
        gcsUdpDataTransferDataFrame.setGcsToken(new String(gcsTokenByteArray));

        gcsUdpDataTransferDataFrame.setSequenceId(in.readShort());

        // 获取数据长度以及数据
        int dataLength = in.readInt();
        if (dataLength < 0) {
            throw new CorruptedFrameException("negative dataLength: " + dataLength);
        }
        if (in.readableBytes() < dataLength) {
            in.resetReaderIndex();
            log.error("[decode][连接({}) 解析消息失败，数据剩余可读长度小于data长度，in={}]", ctx.channel().id(), in.toString());
            return;
        }
        byte[] dataByteArray = new byte[dataLength];
        in.readBytes(dataByteArray);
        gcsUdpDataTransferDataFrame.setLength(dataLength);
        gcsUdpDataTransferDataFrame.setData(new String(dataByteArray));
        out.add(gcsUdpDataTransferDataFrame);
        // TODO: 2022/6/13 测试之后记得删除此log
        log.info("[decode][连接({}) 解析到一条消息({})]", ctx.channel().id(), gcsUdpDataTransferDataFrame.toString());
    }
}
