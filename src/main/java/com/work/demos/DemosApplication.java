package com.work.demos;

import com.bailian.servicetk.core.EnableAllTool;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableAllTool
@EnableTransactionManagement

@SpringBootApplication
@MapperScan("com.work.demos.mybatis.spider.mapper")
public class DemosApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemosApplication.class, args);
    }

}
