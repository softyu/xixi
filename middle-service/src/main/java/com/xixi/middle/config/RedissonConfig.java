package com.xixi.middle.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.TransportMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * @author : xiaoyu
 * @version V1.0
 * @Project: xixi
 * @Package com.xixi.middle.config
 * @Description: TODO
 * @date Date : 2020年12月07日 5:54 下午
 */
@Configuration
public class RedissonConfig {
    @Autowired
    Environment environment;

    @Bean
    public RedissonClient getRedissonClient() {
        Config config = new Config();
        config.setTransportMode( TransportMode.NIO);

        config.useSingleServer().setAddress(environment.getProperty("redisson.host.config")).setKeepAlive(true);

        return Redisson.create(config);
    }
}
