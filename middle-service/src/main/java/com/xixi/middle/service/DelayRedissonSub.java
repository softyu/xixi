package com.xixi.middle.service;

import com.xixi.middle.dao.mapper.SysLogMapper;
import com.xixi.middle.dao.model.SysLog;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBlockingDeque;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.redisson.api.listener.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.Ordered;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @author : xiaoyu
 * @version V1.0
 * @Project: xixi
 * @Package com.xixi.middle.service
 * @Description: delay-sub
 * @date Date : 2020年12月09日 2:26 下午
 */
@Slf4j
@Service
@EnableScheduling
public class DelayRedissonSub {

    final static String delayQueue = "sys_log_topic_delay";

    @Autowired
    RedissonClient redissonClient;

    @Autowired
    SysLogMapper sysLogMapper;


    @Scheduled(cron = "*/1 * * * * ?")
    public void sub() {
        RBlockingDeque<SysLog> blockingDeque = redissonClient.getBlockingDeque(delayQueue);
        try {
            SysLog sysLog = blockingDeque.take();
            if (sysLog != null) {
                log.info("sys log...:{}", sysLog);
                sysLogMapper.insertSelective(sysLog);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
