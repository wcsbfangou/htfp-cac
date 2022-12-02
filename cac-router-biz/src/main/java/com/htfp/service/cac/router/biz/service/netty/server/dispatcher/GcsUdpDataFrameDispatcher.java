package com.htfp.service.cac.router.biz.service.netty.server.dispatcher;

import com.htfp.service.cac.router.biz.service.NettyBaseContext;
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
public class GcsUdpDataFrameDispatcher extends SimpleChannelInboundHandler<NettyBaseContext> {

    @Resource
    private DataFrameHandlerContainer dataFrameHandlerContainer;

    // TODO: 2022/6/20 线程池优化
    private final ExecutorService executor = Executors.newFixedThreadPool(200);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, NettyBaseContext nettyBaseContext) throws Exception {

        IDataFrameHandler dataFrameHandler = dataFrameHandlerContainer.getDataFrameHandler(nettyBaseContext.getDataFrameTypeEnum() != null ? nettyBaseContext.getDataFrameTypeEnum().getName() : null);
        // 执行逻辑
        executor.submit(new Runnable() {

            @Override
            public void run() {
                // noinspection unchecked
                dataFrameHandler.execute(ctx.channel(), nettyBaseContext);
            }

        });
    }
}
