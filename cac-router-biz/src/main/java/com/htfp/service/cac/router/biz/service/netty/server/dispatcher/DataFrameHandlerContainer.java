package com.htfp.service.cac.router.biz.service.netty.server.dispatcher;

import com.htfp.service.cac.router.biz.service.netty.handler.IDataFrameHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author sunjipeng
 * @Date 2022-06-11 15:49
 */
@Slf4j
public class DataFrameHandlerContainer implements InitializingBean {

    /**
     * 消息类型与 MessageHandler 的映射
     */
    private final Map<String, IDataFrameHandler> handlers = new HashMap<>();

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * 通过 ApplicationContext 获得所有 DataFrameHandler Bean
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        applicationContext.getBeansOfType(IDataFrameHandler.class).values()
                .forEach(dataFrameHandler -> handlers.put(dataFrameHandler.getType(), dataFrameHandler));
        log.info("DataFrameHandlerContainer的DataFrameHandler数量：{}]", handlers.size());
    }

    /**
     * 获得类型对应的 MessageHandler
     *
     * @param type 类型
     * @return MessageHandler
     */
    public IDataFrameHandler getMessageHandler(String type) {
        IDataFrameHandler handler = handlers.get(type);
        if (handler == null) {
            throw new IllegalArgumentException(String.format("类型(%s) 找不到匹配的 MessageHandler 处理器", type));
        }
        return handler;
    }
}
