package com.htfp.service.cac.router.biz.service.netty.handler.dataTransfer;

import com.htfp.service.cac.common.constant.UdpDataFrameConstant;
import com.htfp.service.cac.common.enums.SubscribeDataEnum;
import com.htfp.service.cac.common.enums.dataFrame.DataFrameTypeEnum;
import com.htfp.service.cac.dao.model.mapping.GcsIpMappingDO;
import com.htfp.service.cac.dao.service.GcsDalService;
import com.htfp.service.cac.router.biz.service.NettyBaseContext;
import com.htfp.service.cac.router.biz.service.netty.codec.GcsUdpDataTransferDataFrame;
import com.htfp.service.cac.router.biz.service.netty.handler.IDataFrameHandler;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author sunjipeng
 * @Date 2022-06-13 16:20
 * @Description 数据透传Handler
 */
@Slf4j
@Component
public class DataTransferGcsToRcsHandler implements IDataFrameHandler {

    @Value("${gcsUdpDataTransfer.rcsPort}")
    private int port;

    @Resource
    private GcsDalService gcsDalService;

    @Override
    public void execute(Channel channel, NettyBaseContext baseContext) {
        GcsUdpDataTransferDataFrame dataFrame = (GcsUdpDataTransferDataFrame) baseContext.getDataFrame();
        try {
            if (dataFrame != null) {
                Long gcsId = Long.valueOf(dataFrame.getGcsId());
                String gcsToken = dataFrame.getGcsToken();
                if (gcsDalService.validateGcsToken(gcsId, gcsToken)) {
                    // TODO: 2022/6/30 本地缓存
                    List<InetSocketAddress> inetSocketAddressList = new ArrayList<>();
                    List<GcsIpMappingDO> gcsIpMappingList = gcsDalService.queryGcsIpMapping(SubscribeDataEnum.SUBSCRIBE);
                    if(CollectionUtils.isNotEmpty(gcsIpMappingList)){
                        for (GcsIpMappingDO gcsIpMapping : gcsIpMappingList) {
                            InetSocketAddress inetSocketAddress = new InetSocketAddress(gcsIpMapping.getGcsIp(), port);
                            inetSocketAddressList.add(inetSocketAddress);
                        }
                        baseContext.setReceiverList(inetSocketAddressList);
                    } else {
                        dataFrame.setData(UdpDataFrameConstant.RCS_IS_NOT_SIGN_IN_OR_HAS_SUBSCRIBED);
                        dataFrame.setLength(UdpDataFrameConstant.RCS_IS_NOT_SIGN_IN_OR_HAS_SUBSCRIBED.length());
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

    @Override
    public String getType() {
        return DataFrameTypeEnum.DATA_TRANSFER_GCS_TO_RCS.getName();
    }
}
