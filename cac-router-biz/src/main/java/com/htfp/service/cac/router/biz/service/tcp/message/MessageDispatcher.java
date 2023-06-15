package com.htfp.service.cac.router.biz.service.tcp.message;

import com.htfp.service.cac.common.enums.dataFrame.GcsTcpBaseDataFrameTypeEnum;
import com.htfp.service.cac.router.biz.service.tcp.codec.GcsTcpBaseDataFrame;
import com.htfp.service.cac.router.biz.service.tcp.message.handler.IMessageHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author sunjipeng
 * @Date 2023/6/14
 * @Description 描述
 */

@ChannelHandler.Sharable
public class MessageDispatcher extends SimpleChannelInboundHandler<GcsTcpBaseDataFrame> {

    @Autowired
    private MessageHandlerContainer messageHandlerContainer;

    // TODO: 2023/6/15 线程池优化
    private final ExecutorService tcpExecutor = Executors.newFixedThreadPool(200);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GcsTcpBaseDataFrame gcsTcpBaseDataFrame) {
        // 获得 type 对应的 MessageHandler 处理器
        IMessageHandler messageHandler = messageHandlerContainer.getMessageHandler(getGcsTcpBaseDataFrameType(gcsTcpBaseDataFrame) != null ? getGcsTcpBaseDataFrameType(gcsTcpBaseDataFrame).getName() : null);
        // 执行逻辑
        tcpExecutor.submit(new Runnable() {

            @Override
            public void run() {
                // noinspection unchecked
                messageHandler.execute(ctx.channel(), gcsTcpBaseDataFrame);
            }

        });
    }

    private GcsTcpBaseDataFrameTypeEnum getGcsTcpBaseDataFrameType(GcsTcpBaseDataFrame gcsTcpBaseDataFrame) {
        GcsTcpBaseDataFrameTypeEnum gcsTcpBaseDataFrameTypeEnum = GcsTcpBaseDataFrameTypeEnum.getFromMagicCodeAndType(gcsTcpBaseDataFrame.getMagicCode(), gcsTcpBaseDataFrame.getType());
        if (gcsTcpBaseDataFrameTypeEnum != null) {
            return gcsTcpBaseDataFrameTypeEnum;
        } else {
            return null;
        }
    }

}
