package com.htfp.service.cac.router.biz.service.netty.handler;

import com.htfp.service.cac.router.biz.service.netty.codec.BaseDataFrame;
import io.netty.channel.Channel;

/**
 * @Author sunjipeng
 * @Date 2022-06-11 15:30
 */
public interface IDataFrameHandler<T extends BaseDataFrame> {

    /**
     * 执行处理消息
     *
     * @param channel 通道
     * @param dataFrame 数据帧
     */
    void execute(Channel channel, T dataFrame);

    /**
     * 获取消息类型
     * @return 消息类型
     */
    String getType();
}
