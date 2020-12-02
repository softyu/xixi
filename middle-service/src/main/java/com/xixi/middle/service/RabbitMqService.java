package com.xixi.middle.service;

import com.xixi.middle.mq.consumer.BasicConsumer;
import com.xixi.middle.mq.producer.BasicPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : xiaoyu
 * @version V1.0
 * @Project: xixi
 * @Package com.xixi.middle.service
 * @Description: mq
 * @date Date : 2020年12月02日 4:38 下午
 */
@Service
public class RabbitMqService {


    @Autowired
    BasicPublisher basicPublisher;
    
    public Boolean pubMqMsg(String hello) {
        basicPublisher.sendMsg(hello.getBytes());
        return true;
    }


}
