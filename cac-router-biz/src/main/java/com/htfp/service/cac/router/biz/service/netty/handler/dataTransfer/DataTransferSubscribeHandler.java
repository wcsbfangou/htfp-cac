package com.htfp.service.cac.router.biz.service.netty.handler.dataTransfer;

import com.htfp.service.cac.common.enums.ErrorCodeEnum;
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
 * @Date 2022-06-13 16:18
 */
@Slf4j
@Component
public class DataTransferSubscribeHandler implements IDataFrameHandler<GcsUdpDataTransferDataFrame> {

    @Resource
    private NettyChannelManager nettyChannelManager;

    @Resource
    private GcsDalService gcsDalService;

    @Override
    public void execute(Channel channel, GcsUdpDataTransferDataFrame dataFrame) {
        Long rcsId = Long.valueOf(dataFrame.getGcsId());
        String rcsToken = dataFrame.getGcsToken();
        if(gcsDalService.validateRcsToken(rcsId, rcsToken)){
            // 将用户和 Channel 绑定
            nettyChannelManager.addUser(dataFrame.getGcsId(), channel);
        } else {
            dataFrame.setData(ErrorCodeEnum.GCS_ID_VALIDATE_FAIL.getDesc());
        }
        channel.writeAndFlush(dataFrame);
    }

    @Override
    public String getType() {
        return DataFrameTypeEnum.DATA_TRANSFER_SUBSCRIBE.getName();
    }
}
