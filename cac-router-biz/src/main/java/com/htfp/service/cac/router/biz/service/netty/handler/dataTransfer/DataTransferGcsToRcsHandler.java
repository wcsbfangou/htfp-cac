package com.htfp.service.cac.router.biz.service.netty.handler.dataTransfer;

import com.htfp.service.cac.common.enums.SubscribeDataEnum;
import com.htfp.service.cac.common.enums.dataFrame.DataFrameTypeEnum;
import com.htfp.service.cac.dao.model.mapping.GcsIpMappingDO;
import com.htfp.service.cac.dao.service.GcsDalService;
import com.htfp.service.cac.router.biz.service.NettyBaseContext;
import com.htfp.service.cac.router.biz.service.netty.codec.GcsUdpDataTransferDataFrame;
import com.htfp.service.cac.router.biz.service.netty.handler.IDataFrameHandler;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
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
        if (dataFrame != null) {
            Long gcsId = Long.valueOf(dataFrame.getGcsId());
            String gcsToken = dataFrame.getGcsToken();
            if (gcsDalService.validateGcsToken(gcsId, gcsToken)) {
                List<InetSocketAddress> inetSocketAddressList = new ArrayList<>();
                List<GcsIpMappingDO> gcsIpMappingList = gcsDalService.queryGcsIpMapping(SubscribeDataEnum.SUBSCRIBE);
                for (GcsIpMappingDO gcsIpMapping : gcsIpMappingList) {
                    InetSocketAddress inetSocketAddress = new InetSocketAddress(gcsIpMapping.getGcsIp(), port);
                    inetSocketAddressList.add(inetSocketAddress);
                }
                baseContext.setReceiverList(inetSocketAddressList);
                channel.writeAndFlush(baseContext);
            }
        }
    }

    @Override
    public String getType() {
        return DataFrameTypeEnum.DATA_TRANSFER_GCS_TO_RCS.getName();
    }
}
