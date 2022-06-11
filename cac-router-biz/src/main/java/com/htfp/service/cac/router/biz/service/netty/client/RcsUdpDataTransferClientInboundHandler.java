package com.htfp.service.cac.router.biz.service.netty.client;

import com.htfp.service.cac.common.enums.GcsTypeEnum;
import com.htfp.service.cac.common.enums.MappingStatusEnum;
import com.htfp.service.cac.dao.model.entity.GcsInfoDO;
import com.htfp.service.cac.dao.model.mapping.GcsIpMappingDO;
import com.htfp.service.cac.dao.service.GcsDalService;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author sunjipeng
 * @Date 2022-06-07 16:40
 */
@Slf4j
public class RcsUdpDataTransferClientInboundHandler extends SimpleChannelInboundHandler<DatagramPacket> {

    @Resource
    GcsDalService gcsDalService;

    @Resource
    RcsUdpDataTransferClient rcsUdpDataTransferClient;

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, DatagramPacket packet) throws Exception {
        try{
            ByteBuf buf = packet.copy().content();
            byte[] req = new byte[buf.readableBytes()];
            buf.readBytes(req);
            sendDataToRcs(req);
        } catch (Exception e){
            log.error("RcsUdpDataTransferClientInboundHandler receive message error", e);
        } finally{
            channelHandlerContext.close();
        }
    }

    private void sendDataToRcs(byte[] req) throws Exception {
        List<GcsInfoDO> gcsInfoDOList = gcsDalService.queryGcsInfo(GcsTypeEnum.RCS);
        for (GcsInfoDO gcsInfoDO : gcsInfoDOList) {
            GcsIpMappingDO gcsIpMappingDO = gcsDalService.queryGcsIpMapping(gcsInfoDO.getGcsId(), MappingStatusEnum.VALID);
            rcsUdpDataTransferClient.sendData(gcsIpMappingDO.getGcsIp(), req);
        }
    }
}
