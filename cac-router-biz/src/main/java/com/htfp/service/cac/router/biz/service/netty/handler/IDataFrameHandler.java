package com.htfp.service.cac.router.biz.service.netty.handler;

import com.htfp.service.cac.router.biz.service.NettyBaseContext;
import io.netty.channel.Channel;

/**
 * @Author sunjipeng
 * @Date 2022-06-11 15:30
 * @Description Handler接口
 */
public interface IDataFrameHandler {

    /**
     * 执行处理消息
     *
     * @param channel   通道
     * @param nettyBaseContext 数据帧上下文
     */
    void execute(Channel channel, NettyBaseContext nettyBaseContext);

    /**
     * 获取消息类型
     *
     * @return 消息类型
     */
    String getType();
}
