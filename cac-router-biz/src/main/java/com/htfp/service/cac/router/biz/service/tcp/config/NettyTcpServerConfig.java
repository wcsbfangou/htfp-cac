package com.htfp.service.cac.router.biz.service.tcp.config;

import com.htfp.service.cac.router.biz.service.tcp.message.MessageDispatcher;
import com.htfp.service.cac.router.biz.service.tcp.message.MessageHandlerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author sunjipeng
 * @Date 2023/6/14
 * @Description 描述
 */
@Configuration
public class NettyTcpServerConfig {

    @Bean
    public MessageDispatcher messageDispatcher() {
        return new MessageDispatcher();
    }

    @Bean
    public MessageHandlerContainer messageHandlerContainer() {
        return new MessageHandlerContainer();
    }

}