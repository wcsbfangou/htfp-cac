package com.htfp.service.cac.app;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Author sunjipeng
 * @Date 2022-05-12 14:02
 * @Description 启动类
 */

@SpringBootApplication
@ComponentScan(value = {"com.htfp.service.cac", "com.htfp.service.oac"})
@MapperScan(value = {"com.htfp.service.cac.dao.mapper"})
@EnableTransactionManagement
@PropertySource({"classpath:/nettySetting.properties"})
public class CacAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(CacAppApplication.class, args);
    }

}
