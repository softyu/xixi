package com.xixi.middle.service;

import com.xixi.middle.dao.model.SysLog;
import org.redisson.api.RBlockingDeque;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author : xiaoyu
 * @version V1.0
 * @Project: xixi
 * @Package com.xixi.middle.service
 * @Description: delay-pub
 * @date Date : 2020年12月09日 2:26 下午
 */
@Service
public class DelayRedissonPub {

    final static String delayQueue = "sys_log_topic_delay";

    @Autowired
    RedissonClient redissonClient;

    public void pub(SysLog sysLog,long ttl) {

        // block-queue
        RBlockingDeque<Object> blockingDeque = redissonClient.getBlockingDeque(delayQueue);

        // delay-queue
        RDelayedQueue<Object> delayedQueue = redissonClient.getDelayedQueue(blockingDeque);

        delayedQueue.offer(sysLog,ttl, TimeUnit.SECONDS);
    }
}
