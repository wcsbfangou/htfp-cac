package com.htfp.service.cac.router.biz.service.netty.server.dispatcher;


import com.htfp.service.cac.common.enums.dataFrame.DataFrameTypeEnum;
import com.htfp.service.cac.router.biz.service.netty.codec.GcsUdpDataTransferDataFrame;
import com.htfp.service.cac.router.biz.service.netty.handler.IDataFrameHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author sunjipeng
 * @Date 2022-06-11 15:47
 * @Description 地面站透传业务的数据帧分发器Handler
 */
@ChannelHandler.Sharable
public class GcsUdpDataFrameDispatcher extends SimpleChannelInboundHandler<GcsUdpDataTransferDataFrame> {

    @Resource
    private DataFrameHandlerContainer dataFrameHandlerContainer;

    private final ExecutorService executor = Executors.newFixedThreadPool(200);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GcsUdpDataTransferDataFrame gcsUdpDataTransferDataFrame) throws Exception {
        // 获得 type 对应的 DataFrameHandler 处理器
        String dataFrameType = getDataFrameType(gcsUdpDataTransferDataFrame);
        IDataFrameHandler dataFrameHandler = dataFrameHandlerContainer.getDataFrameHandler(dataFrameType);
        // 执行逻辑
        executor.submit(new Runnable() {

            @Override
            public void run() {
                // noinspection unchecked
                dataFrameHandler.execute(ctx.channel(), gcsUdpDataTransferDataFrame);
            }

        });
    }

    private String getDataFrameType(GcsUdpDataTransferDataFrame gcsUdpDataTransferDataFrame) {
        DataFrameTypeEnum dataFrameTypeEnum = DataFrameTypeEnum.getFromMagicCodeAndType(gcsUdpDataTransferDataFrame.getMagicCode(), gcsUdpDataTransferDataFrame.getType());
        if (dataFrameTypeEnum != null) {
            return dataFrameTypeEnum.getName();
        } else {
            return null;
        }
    }
}
