package com.htfp.service.cac.router.biz.service.netty.handler.dataTransfer;

import com.htfp.service.cac.common.constant.UdpDataFrameConstant;
import com.htfp.service.cac.common.enums.ErrorCodeEnum;
import com.htfp.service.cac.common.enums.dataFrame.DataFrameTypeEnum;
import com.htfp.service.cac.dao.service.GcsDalService;
import com.htfp.service.cac.router.biz.service.NettyBaseContext;
import com.htfp.service.cac.router.biz.service.netty.codec.GcsUdpDataTransferDataFrame;
import com.htfp.service.cac.router.biz.service.netty.handler.IDataFrameHandler;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author sunjipeng
 * @Date 2022-06-13 16:19
 * @Description 取消数据透传Handler
 */
@Slf4j
@Component
public class DataTransferCancelSubscribeHandler implements IDataFrameHandler {

    @Resource
    private SubscribeManager subscribeManager;

    @Resource
    private GcsDalService gcsDalService;

    @Override
    public void execute(Channel channel, NettyBaseContext baseContext) {
        GcsUdpDataTransferDataFrame dataFrame = (GcsUdpDataTransferDataFrame) baseContext.getDataFrame();
        if (dataFrame != null) {
            Long rcsId = Long.valueOf(dataFrame.getGcsId());
            String rcsToken = dataFrame.getGcsToken();
            if (gcsDalService.validateRcsToken(rcsId, rcsToken)) {
                // 远程地面站取消订阅数据报文
                Boolean cancelSubscribe = subscribeManager.cancelSubscribe(dataFrame.getGcsId());
                if(cancelSubscribe){
                    String newData = UdpDataFrameConstant.RESP + dataFrame.getData();
                    dataFrame.setData(newData);
                    dataFrame.setLength(newData.length());
                } else {
                    dataFrame.setData(UdpDataFrameConstant.RCS_IS_NOT_SIGN_IN_OR_HAS_CANCELED_SUBSCRIBE);
                    dataFrame.setLength(UdpDataFrameConstant.RCS_IS_NOT_SIGN_IN_OR_HAS_CANCELED_SUBSCRIBE.length());
                }
            } else {
                dataFrame.setData(ErrorCodeEnum.GCS_ID_VALIDATE_FAIL.getDesc());
                dataFrame.setLength(ErrorCodeEnum.GCS_ID_VALIDATE_FAIL.getDesc().length());
            }
            channel.writeAndFlush(baseContext);
        }
    }

    @Override
    public String getType() {
        return DataFrameTypeEnum.DATA_TRANSFER_CANCEL_SUBSCRIBE.getName();
    }
}
