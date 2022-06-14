package com.htfp.service.cac.router.biz.service.netty.server;

import com.htfp.service.cac.router.biz.service.netty.codec.BaseDataFrame;
import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @Author sunjipeng
 * @Date 2022-06-11 16:22
 * @Description channel管理
 */
@Slf4j
@Component
public class NettyChannelManager {
    /**
     * {@link Channel#attr(AttributeKey)} 属性中，表示 Channel 对应的用户
     */
    private static final AttributeKey<String> CHANNEL_ATTR_KEY_USER = AttributeKey.newInstance("user");

    /**
     * Channel 映射
     */
    private ConcurrentMap<ChannelId, Channel> channels = new ConcurrentHashMap<>();
    /**
     * 订阅用户与 Channel 的映射。
     *
     * 通过它，可以获取用户对应的 Channel。这样，我们可以向指定用户发送消息。
     */
    private ConcurrentMap<String, Channel> userChannels = new ConcurrentHashMap<>();

    /**
     * 添加 Channel 到 {@link #channels} 中
     *
     * @param channel Channel
     */
    public void add(Channel channel) {
        channels.put(channel.id(), channel);
        log.info("[add][一个连接({})加入]", channel.id());
    }

    /**
     * 将 Channel 从 {@link #channels} 和 {@link #userChannels} 中移除
     *
     * @param channel Channel
     */
    public void remove(Channel channel) {
        // 移除 channels
        channels.remove(channel.id());
        // 移除 userChannels
        if (channel.hasAttr(CHANNEL_ATTR_KEY_USER)) {
            userChannels.remove(channel.attr(CHANNEL_ATTR_KEY_USER).get());
        }
        log.info("[remove][一个连接({})离开]", channel.id());
    }

    /**
     * 添加指定用户到 {@link #userChannels} 中
     *
     * @param user 用户
     * @param channel Channel
     */
    public void addUser(String user, Channel channel) {
        Channel existChannel = channels.get(channel.id());
        if (existChannel == null) {
            log.error("[addUser][连接({}) 不存在]", channel.id());
            return;
        }
        // 设置属性
        channel.attr(CHANNEL_ATTR_KEY_USER).set(user);
        // 添加到 userChannels
        userChannels.put(user, channel);
    }

    /**
     * 将 Channel 从 {@link #userChannels} 中移除
     *
     * @param user String
     */
    public void removeUser(String user) {
        // 移除 userChannels
        userChannels.remove(user);
        log.info("[removeUser][一个用户连接({})离开]", user);
    }

    /**
     * 向指定订阅用户发送消息
     *
     * @param user 用户
     * @param dataFrame 消息体
     */
    public void send(String user, BaseDataFrame dataFrame) {
        // 获得用户对应的 Channel
        Channel channel = userChannels.get(user);
        if (channel == null) {
            log.error("[send][连接不存在]");
            return;
        }
        if (!channel.isActive()) {
            log.error("[send][连接({})未激活]", channel.id());
            return;
        }
        // 发送消息
        channel.writeAndFlush(dataFrame);
    }

    /**
     * 向所有用户发送消息
     *
     * @param dataFrame 消息体
     */
    public void sendAll(BaseDataFrame dataFrame) {
        for (Channel channel : channels.values()) {
            if (!channel.isActive()) {
                log.error("[sendALL][连接({})未激活]", channel.id());
                return;
            }
            // 发送消息
            channel.writeAndFlush(dataFrame);
        }
    }

    /**
     * 向所有订阅用户发送消息
     *
     * @param dataFrame 消息体
     */
    public void sendAllUser(BaseDataFrame dataFrame) {
        for (Channel userChannel : userChannels.values()) {
            if (!userChannel.isActive()) {
                log.error("[sendAllUser][连接({})未激活]", userChannel.id());
                return;
            }
            // 发送消息
            userChannel.writeAndFlush(dataFrame);
        }
    }
}
