package com.htfp.service.cac.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class,
        JdbcTemplateAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class})
@ComponentScan(value = {"com.htfp.service.cac"})
@EnableTransactionManagement
public class CacAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(CacAppApplication.class, args);
    }

}
