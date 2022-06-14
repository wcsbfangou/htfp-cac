package com.htfp.service.cac.router.biz.service.netty.handler.dataTransfer;

import com.htfp.service.cac.common.enums.dataFrame.DataFrameTypeEnum;
import com.htfp.service.cac.dao.service.GcsDalService;
import com.htfp.service.cac.router.biz.service.netty.codec.GcsUdpDataTransferDataFrame;
import com.htfp.service.cac.router.biz.service.netty.handler.IDataFrameHandler;
import com.htfp.service.cac.router.biz.service.netty.server.NettyChannelManager;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author sunjipeng
 * @Date 2022-06-13 16:20
 * @Description 数据透传Handler
 */
@Slf4j
@Component
public class DataTransferGcsToRcsHandler implements IDataFrameHandler<GcsUdpDataTransferDataFrame> {

    @Resource
    private NettyChannelManager nettyChannelManager;

    @Resource
    private GcsDalService gcsDalService;

    @Override
    public void execute(Channel channel, GcsUdpDataTransferDataFrame dataFrame) {
        if (dataFrame != null) {
            Long gcsId = Long.valueOf(dataFrame.getGcsId());
            String gcsToken = dataFrame.getGcsToken();
            if (gcsDalService.validateGcsToken(gcsId, gcsToken)) {
                nettyChannelManager.sendAllUser(dataFrame);
            }
        }
    }

    @Override
    public String getType() {
        return DataFrameTypeEnum.DATA_TRANSFER_GCS_TO_RCS.getName();
    }
}
