package com.xixi.middle.service;

import com.xixi.middle.dao.mapper.SysLogMapper;
import com.xixi.middle.dao.model.SysLog;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.redisson.api.listener.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Service;

/**
 * @author : xiaoyu
 * @version V1.0
 * @Project: xixi
 * @Package com.xixi.middle.service
 * @Description: sub
 * @date Date : 2020年12月09日 2:26 下午
 */
@Slf4j
@Service
public class RedissonSub implements ApplicationRunner, Ordered {

    final static String topicKey = "sys_log_topic";

    @Autowired
    RedissonClient redissonClient;

    @Autowired
    SysLogMapper sysLogMapper;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        RTopic topic = redissonClient.getTopic(topicKey);

        topic.addListener(SysLog.class, new MessageListener<SysLog>() {
            @Override
            public void onMessage(CharSequence channel, SysLog msg) {
                log.info("system log:{}", msg);
                sysLogMapper.insertSelective(msg);
            }
        });
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
