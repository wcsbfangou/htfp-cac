package com.htfp.service.cac.router.biz.service.netty.server;

import com.htfp.service.cac.router.biz.service.netty.server.dispatcher.DataFrameHandlerContainer;
import com.htfp.service.cac.router.biz.service.netty.server.dispatcher.GcsUdpDataFrameDispatcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author sunjipeng
 * @Date 2022-06-11 16:33
 */
@Configuration
public class NettyServerConfig {
    @Bean
    public GcsUdpDataFrameDispatcher gcsUdpDataFrameDispatcher() {
        return new GcsUdpDataFrameDispatcher();
    }

    @Bean
    public DataFrameHandlerContainer dataFrameHandlerContainer() {
        return new DataFrameHandlerContainer();
    }
}
