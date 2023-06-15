package com.htfp.service.cac.router.biz.service.tcp.server;

import com.htfp.service.cac.router.biz.service.tcp.codec.GcsTcpBaseDataFrame;
import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @Author sunjipeng
 * @Date 2023/6/14
 * @Description 描述
 */
@Slf4j
@Component
public class TcpNettyChannelManager {

    /**
     * {@link Channel#attr(AttributeKey)} 属性中，表示 Channel 对应的用户
     */
    private static final AttributeKey<String> CHANNEL_ATTR_KEY_USER = AttributeKey.newInstance("user");

    /**
     * Channel 映射
     */
    private ConcurrentMap<ChannelId, Channel> channelMap = new ConcurrentHashMap<>();
    /**
     * 用户与 Channel 的映射。
     *
     * 通过它，可以获取用户对应的 Channel。这样，我们可以向指定用户发送消息。
     */
    private ConcurrentMap<String, Channel> userChannelMap = new ConcurrentHashMap<>();

    /**
     * 添加 Channel 到 {@link #channelMap} 中
     *
     * @param channel Channel
     */
    public void add(Channel channel) {
        channelMap.put(channel.id(), channel);
        log.info("[add][一个连接({})加入]", channel.id());
    }

    /**
     * 添加指定用户到 {@link #userChannelMap} 中
     *
     * @param user 用户
     * @param channel Channel
     */
    public void addUser(String user, Channel channel) {
        Channel existChannel = channelMap.get(channel.id());
        if (existChannel == null) {
            log.error("[addUser][连接({}) 不存在]", channel.id());
            return;
        }
        // 设置属性
        channel.attr(CHANNEL_ATTR_KEY_USER).set(user);
        // 添加到 userChannels
        userChannelMap.put(user, channel);
    }

    /**
     * 将 Channel 从 {@link #channelMap} 和 {@link #userChannelMap} 中移除
     *
     * @param channel Channel
     */
    public void remove(Channel channel) {
        // 移除 channels
        channelMap.remove(channel.id());
        // 移除 userChannels
        if (channel.hasAttr(CHANNEL_ATTR_KEY_USER)) {
            userChannelMap.remove(channel.attr(CHANNEL_ATTR_KEY_USER).get());
        }
        log.info("[remove][一个连接({})离开]", channel.id());
    }

    /**
     * 向指定用户发送消息
     *
     * @param user 用户
     * @param gcsTcpBaseDataFrame 消息体
     */
    public void send(String user, GcsTcpBaseDataFrame gcsTcpBaseDataFrame) {
        // 获得用户对应的 Channel
        Channel channel = userChannelMap.get(user);
        if (channel == null) {
            log.error("[send][连接不存在]");
            return;
        }
        if (!channel.isActive()) {
            log.error("[send][连接({})未激活]", channel.id());
            return;
        }
        // 发送消息
        channel.writeAndFlush(gcsTcpBaseDataFrame);
    }

    /**
     * 向所有用户发送消息
     *
     * @param gcsTcpBaseDataFrame 消息体
     */
    public void sendAll(GcsTcpBaseDataFrame gcsTcpBaseDataFrame) {
        for (Channel channel : channelMap.values()) {
            if (!channel.isActive()) {
                log.error("[send][连接({})未激活]", channel.id());
                return;
            }
            // 发送消息
            channel.writeAndFlush(gcsTcpBaseDataFrame);
        }
    }

}
