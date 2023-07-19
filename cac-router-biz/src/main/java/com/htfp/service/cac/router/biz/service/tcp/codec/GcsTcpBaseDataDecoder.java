package com.htfp.service.cac.router.biz.service.tcp.codec;

import com.htfp.service.cac.common.constant.UdpDataFrameConstant;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @Author sunjipeng
 * @Date 2023/6/12
 * @Description 描述
 */
@Slf4j
public class GcsTcpBaseDataDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {

        GcsTcpBaseDataFrame gcsTcpBaseDataFrame = new GcsTcpBaseDataFrame();
        // 标记当前读取位置
        in.markReaderIndex();
        // 判断是否大于最小长度
        if (in.readableBytes() <= UdpDataFrameConstant.TCP_BASE_DATA_FRAME_MIN_LENGTH) {
            in.resetReaderIndex();
            log.error("[GcsTcpBaseDataDecoder][连接({}) 解析消息失败，数据可读长度小于数据帧最小长度in={}]", ctx.channel().id(), in.toString());
            return;
        }
        gcsTcpBaseDataFrame.setMagicCode(in.readShort());
        gcsTcpBaseDataFrame.setVersion(in.readByte());
        gcsTcpBaseDataFrame.setSerializationAlgorithm(in.readByte());
        gcsTcpBaseDataFrame.setType(in.readByte());
        // 获取地面站编号长度以及地面站编号
        byte gcsIdLengthByte = in.readByte();
        int gcsIdLength = Byte.toUnsignedInt(gcsIdLengthByte);
        if (in.readableBytes() < gcsIdLength) {
            in.resetReaderIndex();
            log.error("[GcsTcpBaseDataDecoder][连接({}) 解析消息失败，数据剩余可读长度小于gcsId长度，in={}]", ctx.channel().id(), in.toString());
            return;
        }
        byte[] gcsIdByteArray = new byte[gcsIdLength];
        in.readBytes(gcsIdByteArray);
        gcsTcpBaseDataFrame.setGcsIdLength(gcsIdLengthByte);
        gcsTcpBaseDataFrame.setGcsId(new String(gcsIdByteArray));
        // 获取地面站Token长度以及地面站Token
        byte gcsTokenLengthByte = in.readByte();
        int gcsTokenLength = Byte.toUnsignedInt(gcsTokenLengthByte);
        if (in.readableBytes() < gcsTokenLength) {
            in.resetReaderIndex();
            log.error("[GcsTcpBaseDataDecoder][连接({}) 解析消息失败，数据剩余可读长度小于gcsToken长度，in={}]", ctx.channel().id(), in.toString());
            return;
        }
        byte[] gcsTokenByteArray = new byte[gcsTokenLength];
        in.readBytes(gcsTokenByteArray);
        gcsTcpBaseDataFrame.setGcsTokenLength(gcsTokenLengthByte);
        gcsTcpBaseDataFrame.setGcsToken(new String(gcsTokenByteArray));

        // 获取无人机编号长度以及无人机编号
        byte uavIdLengthByte = in.readByte();
        int uavIdLength = Byte.toUnsignedInt(uavIdLengthByte);
        if (in.readableBytes() < uavIdLength) {
            in.resetReaderIndex();
            log.error("[GcsTcpBaseDataDecoder][连接({}) 解析消息失败，数据剩余可读长度小于uavId长度，in={}]", ctx.channel().id(), in.toString());
            return;
        }
        if (uavIdLength == 0) {
            gcsTcpBaseDataFrame.setUavIdLength(uavIdLengthByte);
            gcsTcpBaseDataFrame.setUavId(null);
        } else {
            byte[] uavIdByteArray = new byte[uavIdLength];
            in.readBytes(uavIdByteArray);
            gcsTcpBaseDataFrame.setUavIdLength(uavIdLengthByte);
            gcsTcpBaseDataFrame.setUavId(new String(uavIdByteArray));
        }
        // 读取剩余字节
        gcsTcpBaseDataFrame.setReadableDataBytesLength(in.readableBytes());
        if (in.readableBytes() > 0) {
            byte[] readableDataBytes = new byte[in.readableBytes()];
            in.readBytes(readableDataBytes);
            gcsTcpBaseDataFrame.setReadableDataBytes(readableDataBytes);
        }
        out.add(gcsTcpBaseDataFrame);
        // log.info("[GcsTcpBaseDataDecoder][连接({}) 解析到一条消息({})]", ctx.channel().id(), gcsTcpBaseDataFrame.toString());
    }
}
