package com.xixi.middle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author : xiaoyu
 * @version V1.0
 * @Project: xixi
 * @Package com.xixi.middle
 * @Description: application
 * @date Date : 2020年11月11日 5:36 下午
 */
@SpringBootApplication
public class XixiApplication {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(XixiApplication.class);
        springApplication.run(args);
    }
}
