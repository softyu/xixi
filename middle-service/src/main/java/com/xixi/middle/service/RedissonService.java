package com.xixi.middle.service;

import org.redisson.api.RBloomFilter;
import org.redisson.api.RLock;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    final static String bloomFilterKey = "bloomFilterKey";

    public Object filter(Long userId) {

        RBloomFilter<Long> bloomFilter = redissonClient.getBloomFilter(bloomFilterKey);

        bloomFilter.tryInit(1000L, 0.01);

        for (int i = 1; i <= 1000; i++) {
            bloomFilter.add(Long.valueOf(i));
        }

        return bloomFilter.contains(userId);
    }
}
