package com.xixi.middle.service;

import com.xixi.middle.dao.model.SysLog;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : xiaoyu
 * @version V1.0
 * @Project: xixi
 * @Package com.xixi.middle.service
 * @Description: pub
 * @date Date : 2020年12月09日 2:26 下午
 */
@Service
public class RedissonPub {

    final static String topicKey = "sys_log_topic";

    @Autowired
    RedissonClient redissonClient;

    public void sub(SysLog sysLog) {

        RTopic topic = redissonClient.getTopic(topicKey);

        topic.publishAsync(sysLog);
    }
}
