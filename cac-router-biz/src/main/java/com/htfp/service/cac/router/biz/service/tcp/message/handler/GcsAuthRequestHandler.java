package com.htfp.service.cac.router.biz.service.tcp.message.handler;

import com.htfp.service.cac.common.enums.ErrorCodeEnum;
import com.htfp.service.cac.common.enums.dataFrame.GcsTcpBaseDataFrameTypeEnum;
import com.htfp.service.cac.dao.model.entity.GcsInfoDO;
import com.htfp.service.cac.dao.service.GcsDalService;
import com.htfp.service.cac.router.biz.service.tcp.codec.GcsTcpBaseDataFrame;
import com.htfp.service.cac.router.biz.service.tcp.message.response.TcpGcsAuthCacResponse;
import com.htfp.service.cac.router.biz.service.tcp.server.TcpNettyChannelManager;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.nio.ByteBuffer;

/**
 * @Author sunjipeng
 * @Date 2023/6/14
 * @Description 描述
 */
@Slf4j
@Component
public class GcsAuthRequestHandler implements IMessageHandler {

    @Resource
    private TcpNettyChannelManager tcpNettyChannelManager;

    @Resource
    private GcsDalService gcsDalService;


    /**
     * 执行处理消息
     *
     * @param channel             通道
     * @param gcsTcpBaseDataFrame 消息
     */
    @Override
    public void execute(Channel channel, GcsTcpBaseDataFrame gcsTcpBaseDataFrame) {
        TcpGcsAuthCacResponse tcpGcsAuthCacResponse = buildGcsCacResponse(gcsTcpBaseDataFrame);
        if (StringUtils.isNotBlank(gcsTcpBaseDataFrame.getGcsId())) {
            if (StringUtils.isNotBlank(gcsTcpBaseDataFrame.getGcsToken())) {
                GcsInfoDO queryGcsInfo = gcsDalService.queryGcsInfo(Long.valueOf(gcsTcpBaseDataFrame.getGcsId()));
                if (gcsTcpBaseDataFrame.getGcsToken().equals(queryGcsInfo.getToken())) {
                    tcpGcsAuthCacResponse.setSuccess((byte) 1);
                    tcpGcsAuthCacResponse.setCode(ErrorCodeEnum.SUCCESS.getCode());
                    tcpGcsAuthCacResponse.setMessageLength((byte) 0);
                    tcpNettyChannelManager.addUser(gcsTcpBaseDataFrame.getGcsId(), channel);
                } else {
                    tcpGcsAuthCacResponse.setCode(ErrorCodeEnum.WRONG_GCS_TOKEN.getCode());
                    tcpGcsAuthCacResponse.setMessageLength((byte) ErrorCodeEnum.WRONG_GCS_TOKEN.getDesc().getBytes().length);
                    tcpGcsAuthCacResponse.setMessage(ErrorCodeEnum.WRONG_GCS_TOKEN.getDesc());
                }
            } else {
                tcpGcsAuthCacResponse.setCode(ErrorCodeEnum.LACK_OF_GCS_TOKEN.getCode());
                tcpGcsAuthCacResponse.setMessageLength((byte) ErrorCodeEnum.LACK_OF_GCS_TOKEN.getDesc().getBytes().length);
                tcpGcsAuthCacResponse.setMessage(ErrorCodeEnum.LACK_OF_GCS_TOKEN.getDesc());
            }
        } else {
            tcpGcsAuthCacResponse.setCode(ErrorCodeEnum.LACK_OF_GCS_TOKEN.getCode());
            tcpGcsAuthCacResponse.setMessageLength((byte) ErrorCodeEnum.LACK_OF_GCS_TOKEN.getDesc().getBytes().length);
            tcpGcsAuthCacResponse.setMessage(ErrorCodeEnum.LACK_OF_GCS_TOKEN.getDesc());
        }
        buildReadableDataBytes(tcpGcsAuthCacResponse);
        channel.writeAndFlush(tcpGcsAuthCacResponse);
    }

    private TcpGcsAuthCacResponse buildGcsCacResponse(GcsTcpBaseDataFrame gcsTcpBaseDataFrame) {
        TcpGcsAuthCacResponse tcpGcsAuthCacResponse = new TcpGcsAuthCacResponse();
        tcpGcsAuthCacResponse.setMagicCode(gcsTcpBaseDataFrame.getMagicCode());
        tcpGcsAuthCacResponse.setVersion(gcsTcpBaseDataFrame.getVersion());
        tcpGcsAuthCacResponse.setSerializationAlgorithm(gcsTcpBaseDataFrame.getSerializationAlgorithm());
        tcpGcsAuthCacResponse.setType((byte) GcsTcpBaseDataFrameTypeEnum.GCS_AUTH_CAC_RESPONSE.getType().intValue());
        tcpGcsAuthCacResponse.setGcsIdLength(gcsTcpBaseDataFrame.getGcsIdLength());
        tcpGcsAuthCacResponse.setGcsId(gcsTcpBaseDataFrame.getGcsId());
        tcpGcsAuthCacResponse.setGcsTokenLength(gcsTcpBaseDataFrame.getGcsTokenLength());
        tcpGcsAuthCacResponse.setGcsToken(gcsTcpBaseDataFrame.getGcsToken());
        tcpGcsAuthCacResponse.setUavIdLength(gcsTcpBaseDataFrame.getUavIdLength());
        tcpGcsAuthCacResponse.setUavId(gcsTcpBaseDataFrame.getUavId());
        tcpGcsAuthCacResponse.setSuccess((byte) 0);
        return tcpGcsAuthCacResponse;
    }

    private void buildReadableDataBytes(TcpGcsAuthCacResponse tcpGcsAuthCacResponse) {
        int readableDataBytesLength = 6 + tcpGcsAuthCacResponse.getMessageLength();
        byte[] readableDataBytes = new byte[readableDataBytesLength];
        ByteBuffer readableDataBytesByteBuffer = ByteBuffer.allocate(readableDataBytesLength);
        readableDataBytesByteBuffer.put(tcpGcsAuthCacResponse.getSuccess());
        readableDataBytesByteBuffer.putInt(tcpGcsAuthCacResponse.getCode());
        readableDataBytesByteBuffer.put(tcpGcsAuthCacResponse.getMessageLength());
        if (tcpGcsAuthCacResponse.getMessageLength() > 0) {
            readableDataBytesByteBuffer.put(tcpGcsAuthCacResponse.getMessage().getBytes());
        }
        readableDataBytesByteBuffer.flip();
        readableDataBytesByteBuffer.get(readableDataBytes);
        tcpGcsAuthCacResponse.setReadableDataBytesLength(readableDataBytesLength);
        tcpGcsAuthCacResponse.setReadableDataBytes(readableDataBytes);
    }

    /**
     * @return 消息类型
     */
    @Override
    public String getType() {
        return GcsTcpBaseDataFrameTypeEnum.GCS_AUTH_CAC_REQUEST.getName();
    }
}
