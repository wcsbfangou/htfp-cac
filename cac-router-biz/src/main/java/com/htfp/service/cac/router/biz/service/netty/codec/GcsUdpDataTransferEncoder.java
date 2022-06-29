package com.htfp.service.cac.router.biz.service.netty.codec;

import com.htfp.service.cac.common.constant.UdpDataFrameConstant;
import com.htfp.service.cac.router.biz.service.NettyBaseContext;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageEncoder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * @Author sunjipeng
 * @Date 2022-06-11 14:58
 * @Description 地面站数据透传编码器
 */
@Slf4j
public class GcsUdpDataTransferEncoder extends MessageToMessageEncoder<NettyBaseContext> {

    @Override
    protected void encode(ChannelHandlerContext ctx, NettyBaseContext baseContext, List<Object> list) throws Exception {
        GcsUdpDataTransferDataFrame dataFrame = (GcsUdpDataTransferDataFrame) baseContext.getDataFrame();
        ByteBuf buf = ctx.alloc().buffer(UdpDataFrameConstant.DATA_FRAME_MIN_LENGTH + dataFrame.getGcsIdLength() + dataFrame.getGcsTokenLength() + dataFrame.getLength());
        if (dataFrame != null) {
            buf.writeShort(dataFrame.getMagicCode());
            buf.writeByte(dataFrame.getVersion());
            buf.writeByte(dataFrame.getSerializationAlgorithm());
            buf.writeByte(dataFrame.getType());
            buf.writeByte(dataFrame.getGcsIdLength());
            buf.writeBytes(dataFrame.getGcsId().getBytes(), 0, dataFrame.getGcsIdLength());
            buf.writeByte(dataFrame.getGcsTokenLength());
            buf.writeBytes(dataFrame.getGcsToken().getBytes(), 0, dataFrame.getGcsTokenLength());
            buf.writeShort(dataFrame.getSequenceId());
            buf.writeInt(dataFrame.getLength());
            buf.writeBytes(dataFrame.getData().getBytes());
            // TODO: 2022/6/13 测试之后记得删除此log
            log.info("[encode][连接({}) 编码了一条消息({})]", ctx.channel().id(), dataFrame.toString());
            list.add(new DatagramPacket(buf, baseContext.getOriginSender()));
            if (CollectionUtils.isNotEmpty(baseContext.getReceiverList())) {
                for (InetSocketAddress inetSocketAddress : baseContext.getReceiverList()) {
                    buf.retain();
                    list.add(new DatagramPacket(buf, inetSocketAddress));
                }
            }
        }
    }
}
