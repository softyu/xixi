package com.xixi.middle;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author : xiaoyu
 * @version V1.0
 * @Project: xixi
 * @Package com.xixi.middle
 * @Description: application
 * @date Date : 2020年11月11日 5:36 下午
 */
@MapperScan("com.xiix.middle.dao.mapper")
@ComponentScan(basePackages = {"com.xixi"})
@SpringBootApplication
public class XixiApplication {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(XixiApplication.class);
        springApplication.run(args);
    }
}
