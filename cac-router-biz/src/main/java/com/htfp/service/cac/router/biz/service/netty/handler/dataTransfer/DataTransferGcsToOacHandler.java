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
                            if (queryUavOacMapping != null) {
                                newData = dataDecodeAndTransferToOac(dataFrame.getData(), queryUavOacMapping.getReportCode(), queryUavInfo.getCpn());
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

    String dataDecodeAndTransferToOac(String data, String reportCode, String cpn) {
        String newData = UdpDataFrameConstant.DATA_TRANSFER_FAIL;
        try {
            UavDataTransferRequest uavDataTransferRequest = decodeUavOriginData(data, reportCode, cpn);
            if (uavDataTransferRequest != null) {
                UavDataTransferResponse uavDataTransferResponse = flyingService.uavDataTransfer(uavDataTransferRequest);
                if (uavDataTransferResponse.getSuccess() && reportCode.equals(uavDataTransferResponse.getReportCode())) {
                    newData = UdpDataFrameConstant.DATA_TRANSFER_SUCCESS;
                }
            }
        } catch (Exception e) {
            log.error("解码和传输数据发生异常, data= {}", data, e);
            newData = UdpDataFrameConstant.DATA_TRANSFER_EXCEPTION;
        }
        return newData;
    }

    UavDataTransferRequest decodeUavOriginData(String data, String reportCode, String cpn){
        UavDataTransferRequest uavDataTransferRequest = new UavDataTransferRequest();
        return uavDataTransferRequest;
    }
}
