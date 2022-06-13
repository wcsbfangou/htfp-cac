package com.htfp.service.cac.router.biz.service.netty.handler.dataTransfer;

import com.htfp.service.cac.common.enums.dataFrame.DataFrameTypeEnum;
import com.htfp.service.cac.router.biz.service.netty.codec.GcsUdpDataTransferDataFrame;
import com.htfp.service.cac.router.biz.service.netty.handler.IDataFrameHandler;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Author sunjipeng
 * @Date 2022-06-13 16:20
 */
@Slf4j
@Component
public class DataTransferGcsToRcsHandler implements IDataFrameHandler<GcsUdpDataTransferDataFrame> {
    @Override
    public void execute(Channel channel, GcsUdpDataTransferDataFrame dataFrame) {

    }

    @Override
    public String getType() {
        return DataFrameTypeEnum.DATA_TRANSFER_GCS_TO_RCS.getName();
    }
}
