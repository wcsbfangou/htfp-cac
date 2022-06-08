package com.htfp.service.cac.app;

import com.htfp.service.cac.router.biz.netty.server.GcsUdpDataTransferServer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;

/**
 * @Author sunjipeng
 * @Date 2022-05-12 14:02
 */
@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class,
        JdbcTemplateAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class})
@ComponentScan(value = {"com.htfp.service.cac"})
@EnableTransactionManagement
@PropertySource({"classpath:/nettySetting.properties"})
public class CacAppApplication implements CommandLineRunner {

    @Resource
    GcsUdpDataTransferServer gcsUdpDataTransferServer;

    public static void main(String[] args) {
        SpringApplication.run(CacAppApplication.class, args);
    }

    @Async
    @Override
    public void run(String... arg) throws Exception {
        gcsUdpDataTransferServer.startServer();
    }
}
