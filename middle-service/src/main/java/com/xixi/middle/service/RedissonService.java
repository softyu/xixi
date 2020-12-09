package com.xixi.middle.service;

import com.xixi.middle.dao.model.SysLog;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author : xiaoyu
 * @version V1.0
 * @Project: xixi
 * @Package com.xixi.middle.service
 * @Description: redisson
 * @date Date : 2020年12月08日 11:28 上午
 */
@Service
public class RedissonService {

    @Autowired
    RedissonClient redissonClient;

    @Autowired
    RedissonPub redissonPub;

    @Autowired
    DelayRedissonPub delayRedissonPub;

    final static String bloomFilterKey = "bloomFilterKey";

    public Object filter(Long userId) {

        RBloomFilter<Long> bloomFilter = redissonClient.getBloomFilter(bloomFilterKey);

        bloomFilter.tryInit(1000L, 0.01);

        for (int i = 1; i <= 1000; i++) {
            bloomFilter.add(Long.valueOf(i));
        }

        return bloomFilter.contains(userId);
    }

    /**
     * pub-sub
     *
     * @param userId
     * @return
     */
    public Object pubSub(String userId) {
        SysLog sysLog = new SysLog();
        sysLog.setUserId(userId);
        sysLog.setData("send message system log");
        sysLog.setMemo("send message system log");
        sysLog.setCreateTime(new Date());
        redissonPub.sub(sysLog);
        return true;
    }

    /**
     * delay-mq
     *
     * @param userId
     * @return
     */
    public Object delayMq(String userId) {
        SysLog sysLog = new SysLog();
        sysLog.setUserId(userId);
        sysLog.setData("send message system log 10");
        sysLog.setMemo("send message system log 10");
        sysLog.setCreateTime(new Date());
        delayRedissonPub.pub(sysLog,10L);


        SysLog sysLog20 = new SysLog();
        sysLog20.setUserId(userId);
        sysLog20.setData("send message system log 20");
        sysLog20.setMemo("send message system log 20");
        sysLog20.setCreateTime(new Date());
        delayRedissonPub.pub(sysLog20,20L);


        SysLog sysLog30 = new SysLog();
        sysLog30.setUserId(userId);
        sysLog30.setData("send message system log 30");
        sysLog30.setMemo("send message system log 30");
        sysLog30.setCreateTime(new Date());
        delayRedissonPub.pub(sysLog30,30L);
        return true;
    }
}
