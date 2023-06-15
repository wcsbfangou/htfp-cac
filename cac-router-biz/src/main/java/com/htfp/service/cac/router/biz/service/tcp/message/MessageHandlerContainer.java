package com.htfp.service.cac.router.biz.service.tcp.message;

import com.htfp.service.cac.router.biz.service.tcp.message.handler.IMessageHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author sunjipeng
 * @Date 2023/6/14
 * @Description 描述
 */
@Slf4j
public class MessageHandlerContainer implements InitializingBean {

    /**
     * 消息类型与 IMessageHandler 的映射
     */
    private final Map<String, IMessageHandler> handlers = new HashMap<>();

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void afterPropertiesSet() throws Exception {
        // 通过 ApplicationContext 获得所有 MessageHandler Bean
        applicationContext.getBeansOfType(IMessageHandler.class).values()
                .forEach(messageHandler -> handlers.put(messageHandler.getType(), messageHandler));
        log.info("[afterPropertiesSet][IMessageHandler数量：{}]", handlers.size());
    }

    /**
     * 获得类型对应的 IMessageHandler
     *
     * @param type 类型
     * @return IMessageHandler
     */
    public IMessageHandler getMessageHandler(String type) {
        IMessageHandler handler = handlers.get(type);
        if (handler == null) {
            throw new IllegalArgumentException(String.format("类型(%s) 找不到匹配的 IMessageHandler 处理器", type));
        }
        return handler;
    }

}
