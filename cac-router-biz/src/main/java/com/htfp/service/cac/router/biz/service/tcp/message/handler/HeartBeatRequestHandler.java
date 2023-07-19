package com.htfp.service.cac.router.biz.service.tcp.message.handler;

import com.htfp.service.cac.common.enums.dataFrame.GcsTcpBaseDataFrameTypeEnum;
import com.htfp.service.cac.router.biz.service.tcp.codec.GcsTcpBaseDataFrame;
import com.htfp.service.cac.router.biz.service.tcp.message.response.TcpHeartBeatResponse;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.nio.ByteBuffer;

/**
 * @Author sunjipeng
 * @Date 2023/6/14
 * @Description 描述
 */
@Slf4j
@Component
public class HeartBeatRequestHandler implements IMessageHandler {
    /**
     * 执行处理消息
     *
     * @param channel 通道
     * @param GcsTcpBaseDataFrame 消息
     */
    @Override
    public void execute(Channel channel, GcsTcpBaseDataFrame GcsTcpBaseDataFrame) {
        TcpHeartBeatResponse tcpHeartBeatResponse = buildHeartBeatResponse(GcsTcpBaseDataFrame);
        buildReadableDataBytes(tcpHeartBeatResponse);
        channel.writeAndFlush(tcpHeartBeatResponse);
    }

    private TcpHeartBeatResponse buildHeartBeatResponse(GcsTcpBaseDataFrame gcsTcpBaseDataFrame) {
        TcpHeartBeatResponse tcpHeartBeatResponse = new TcpHeartBeatResponse();
        tcpHeartBeatResponse.setMagicCode(gcsTcpBaseDataFrame.getMagicCode());
        tcpHeartBeatResponse.setVersion(gcsTcpBaseDataFrame.getVersion());
        tcpHeartBeatResponse.setSerializationAlgorithm(gcsTcpBaseDataFrame.getSerializationAlgorithm());
        tcpHeartBeatResponse.setType((byte) GcsTcpBaseDataFrameTypeEnum.HEART_BEAT_RESPONSE.getType().intValue());
        tcpHeartBeatResponse.setGcsIdLength(gcsTcpBaseDataFrame.getGcsIdLength());
        tcpHeartBeatResponse.setGcsId(gcsTcpBaseDataFrame.getGcsId());
        tcpHeartBeatResponse.setGcsTokenLength(gcsTcpBaseDataFrame.getGcsTokenLength());
        tcpHeartBeatResponse.setGcsToken(gcsTcpBaseDataFrame.getGcsToken());
        tcpHeartBeatResponse.setUavIdLength(gcsTcpBaseDataFrame.getUavIdLength());
        tcpHeartBeatResponse.setUavId(gcsTcpBaseDataFrame.getUavId());
        tcpHeartBeatResponse.setCurrentTime(System.currentTimeMillis());
        return tcpHeartBeatResponse;
    }

    private void buildReadableDataBytes(TcpHeartBeatResponse tcpHeartBeatResponse) {
        byte[] readableDataBytes = ByteBuffer.allocate(Long.SIZE / Byte.SIZE).putLong(tcpHeartBeatResponse.getCurrentTime()).array();
        tcpHeartBeatResponse.setReadableDataBytesLength(Long.SIZE / Byte.SIZE);
        tcpHeartBeatResponse.setReadableDataBytes(readableDataBytes);
    }

    /**
     * @return 消息类型
     */
    @Override
    public String getType() {
        return GcsTcpBaseDataFrameTypeEnum.HEART_BEAT_REQUEST.getName();
    }
}
