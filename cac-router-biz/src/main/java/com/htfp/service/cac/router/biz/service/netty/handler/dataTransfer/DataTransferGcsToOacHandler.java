package com.htfp.service.cac.router.biz.service.netty.handler.dataTransfer;

import com.htfp.service.cac.common.constant.UdpDataFrameConstant;
import com.htfp.service.cac.common.enums.LinkStatusEnum;
import com.htfp.service.cac.common.enums.MappingStatusEnum;
import com.htfp.service.cac.common.enums.dataFrame.DataFrameTypeEnum;
import com.htfp.service.cac.dao.model.entity.UavInfoDO;
import com.htfp.service.cac.dao.model.mapping.GcsIpMappingDO;
import com.htfp.service.cac.dao.model.mapping.UavGcsMappingDO;
import com.htfp.service.cac.dao.model.mapping.UavOacMappingDO;
import com.htfp.service.cac.dao.service.GcsDalService;
import com.htfp.service.cac.dao.service.UavDalService;
import com.htfp.service.cac.router.biz.service.NettyBaseContext;
import com.htfp.service.cac.router.biz.service.netty.codec.GcsUdpDataTransferDataFrame;
import com.htfp.service.cac.router.biz.service.netty.handler.IDataFrameHandler;
import com.htfp.service.oac.app.service.IFlyingService;
import com.htfp.service.oac.biz.model.inner.request.UavDataTransferRequest;
import com.htfp.service.oac.biz.model.inner.response.UavDataTransferResponse;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author sunjipeng
 * @Date 2023/2/14
 * @Description 描述
 */
@Slf4j
@Component
public class DataTransferGcsToOacHandler implements IDataFrameHandler {

    @Resource
    private GcsDalService gcsDalService;

    @Resource
    private UavDalService uavDalService;

    @Resource(name = "flyingServiceImpl")
    private IFlyingService flyingService;

    /**
     * 执行处理消息
     *
     * @param channel     通道
     * @param baseContext 数据帧上下文
     */
    @Override
    public void execute(Channel channel, NettyBaseContext baseContext) {
        GcsUdpDataTransferDataFrame dataFrame = (GcsUdpDataTransferDataFrame) baseContext.getDataFrame();
        try {
            if (dataFrame != null) {
                Long gcsId = Long.valueOf(dataFrame.getGcsId());
                String gcsToken = dataFrame.getGcsToken();
                if (gcsDalService.validateGcsToken(gcsId, gcsToken)) {
                    GcsIpMappingDO queryGcsIpMapping = gcsDalService.queryGcsIpMapping(gcsId, MappingStatusEnum.VALID);
                    if (queryGcsIpMapping != null) {
                        // 地面站断联后又有遥测消息传来
                        if (LinkStatusEnum.DISCONNECT.equals(LinkStatusEnum.getFromCode(queryGcsIpMapping.getLinkStatus()))) {
                            gcsDalService.updateGcsIpMappingLinkStatus(queryGcsIpMapping, LinkStatusEnum.ONLINE);
                            List<UavGcsMappingDO> validUavGcsMappingList = uavDalService.queryUavGcsMapping(queryGcsIpMapping.getGcsId(), MappingStatusEnum.VALID);
                            for (UavGcsMappingDO validUavGcsMapping : validUavGcsMappingList) {
                                UavOacMappingDO disconnectUavOacMapping = uavDalService.queryUavOacMapping(validUavGcsMapping.getUavId(), MappingStatusEnum.VALID, LinkStatusEnum.DISCONNECT);
                                uavDalService.updateUavOacMappingLinkStatus(disconnectUavOacMapping, LinkStatusEnum.ONLINE);
                            }
                            dataFrame.setData(UdpDataFrameConstant.GCS_IS_DISCONNECT);
                            dataFrame.setLength(UdpDataFrameConstant.GCS_IS_DISCONNECT.length());
                        } else {
                            String newData;
                            Long uavId = Long.valueOf(dataFrame.getUavId());
                            UavOacMappingDO queryUavOacMapping = uavDalService.queryUavOacMapping(uavId, MappingStatusEnum.VALID, LinkStatusEnum.ONLINE);
                            UavInfoDO queryUavInfo = uavDalService.queryUavInfo(uavId);
                            // 测试用
                            UavDataTransferRequest uavDataTransferRequest = decodeUavOriginData(dataFrame.getOriginDataBytes(), "lalalalla", queryUavInfo.getCpn());
                            if (queryUavOacMapping != null) {
                                newData = dataDecodeAndTransferToOac(dataFrame.getOriginDataBytes(), queryUavOacMapping.getReportCode(), queryUavInfo.getCpn());
                            } else {
                                newData = UdpDataFrameConstant.RESP + dataFrame.getData();
                            }
                            dataFrame.setData(newData);
                            dataFrame.setLength(newData.length());
                        }
                    } else {
                        dataFrame.setData(UdpDataFrameConstant.GCS_IS_NOT_SIGN_IN);
                        dataFrame.setLength(UdpDataFrameConstant.GCS_IS_NOT_SIGN_IN.length());
                    }
                } else {
                    dataFrame.setData(UdpDataFrameConstant.GCS_ID_OR_TOKEN_VALIDATE_FAILED);
                    dataFrame.setLength(UdpDataFrameConstant.GCS_ID_OR_TOKEN_VALIDATE_FAILED.length());
                }
                channel.writeAndFlush(baseContext);
            }
        } catch (Exception e) {
            log.error("数据透传发生异常, nettyContext= {}", baseContext, e);
            dataFrame.setData(UdpDataFrameConstant.DATA_TRANSFER_EXCEPTION);
            dataFrame.setLength(UdpDataFrameConstant.DATA_TRANSFER_EXCEPTION.length());
            channel.writeAndFlush(baseContext);
        }
    }

    /**
     * 获取消息类型
     *
     * @return 消息类型
     */
    @Override
    public String getType() {
        return DataFrameTypeEnum.DATA_TRANSFER_GCS_TO_OAC.getName();
    }

    String dataDecodeAndTransferToOac(byte[] originDataBytes, String reportCode, String cpn) {
        String newData = UdpDataFrameConstant.DATA_TRANSFER_FAIL;
        try {
            UavDataTransferRequest uavDataTransferRequest = decodeUavOriginData(originDataBytes, reportCode, cpn);
            if (uavDataTransferRequest != null) {
                UavDataTransferResponse uavDataTransferResponse = flyingService.uavDataTransfer(uavDataTransferRequest);
                if (uavDataTransferResponse.getSuccess() && reportCode.equals(uavDataTransferResponse.getReportCode())) {
                    newData = UdpDataFrameConstant.DATA_TRANSFER_SUCCESS;
                }
            } else {
                newData = UdpDataFrameConstant.DATA_DECODE_ERROR;
            }
        } catch (Exception e) {
            log.error("解码和传输数据发生异常, originDataBytes= {}", originDataBytes, e);
            newData = UdpDataFrameConstant.DATA_TRANSFER_EXCEPTION;
        }
        return newData;
    }

    UavDataTransferRequest decodeUavOriginData(byte[] originDataBytes, String reportCode, String cpn) {
        if (originDataBytes[0] == UdpDataFrameConstant.AHEAD_1 && originDataBytes[1] == UdpDataFrameConstant.AHEAD_2) {
            UavDataTransferRequest uavDataTransferRequest = new UavDataTransferRequest();
            uavDataTransferRequest.setCpn(cpn);
            uavDataTransferRequest.setReportCode(reportCode);
            if (originDataBytes[2] == UdpDataFrameConstant.IDENTIFY_A1) {
                int pitchAngle = byteToShortLittle(originDataBytes, 5, 6);
                int rollAngle = byteToShortLittle(originDataBytes, 7, 8);
                uavDataTransferRequest.setPitchAngle(pitchAngle);
                uavDataTransferRequest.setRollAngle(rollAngle);
                return uavDataTransferRequest;
            } else if (originDataBytes[2] == UdpDataFrameConstant.IDENTIFY_A2) {
                int lng = bytes2IntLittle(originDataBytes, 5, 6, 7, 8);
                int lat = bytes2IntLittle(originDataBytes, 9, 10, 11, 12);
                int alt = 10 * (byteToShortLittle(originDataBytes, 13, 14) - UdpDataFrameConstant.FIVE_THOUSAND);
                int relativeHeight = 10 * (byteToShortLittle(originDataBytes, 17, 18) - UdpDataFrameConstant.FIVE_THOUSAND);
                int groundSpeed = byteToShortLittle(originDataBytes, 19, 20);
                uavDataTransferRequest.setLng(lng);
                uavDataTransferRequest.setLat(lat);
                uavDataTransferRequest.setAlt(alt);
                uavDataTransferRequest.setRelativeHeight(relativeHeight);
                uavDataTransferRequest.setGroundSpeed(groundSpeed);
                return uavDataTransferRequest;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * 读取小端byte数组为short
     *
     * @param bytes
     * @param little
     * @param big
     * @return
     */
    public static short byteToShortLittle(byte[] bytes, int little, int big) {
        return (short) (((bytes[big] << 8) | bytes[little] & 0xff));
    }

    /**
     * 读取大端byte数组为short
     *
     * @param bytes
     * @param little
     * @param big
     * @return
     */
    public static short byteToShortBig(byte[] bytes, int little, int big) {
        return (short) (((bytes[little] << 8) | bytes[big] & 0xff));
    }

    /**
     * byte数组到int的转换(小端)
     *
     * @param bytes
     * @param one
     * @param two
     * @param three
     * @param four
     * @return
     */
    public static int bytes2IntLittle(byte[] bytes, int one, int two, int three, int four) {
        int int1 = bytes[one] & 0xff;
        int int2 = (bytes[two] & 0xff) << 8;
        int int3 = (bytes[three] & 0xff) << 16;
        int int4 = (bytes[four] & 0xff) << 24;

        return int1 | int2 | int3 | int4;
    }

    /**
     * byte数组到int的转换(大端)
     *
     * @param bytes
     * @param one
     * @param two
     * @param three
     * @param four
     * @return
     */
    public static int bytes2IntBig(byte[] bytes, int one, int two, int three, int four) {
        int int1 = bytes[four] & 0xff;
        int int2 = (bytes[three] & 0xff) << 8;
        int int3 = (bytes[two] & 0xff) << 16;
        int int4 = (bytes[one] & 0xff) << 24;

        return int1 | int2 | int3 | int4;
    }
}
