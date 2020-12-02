package com.xixi.middle.mq.consumer;

/**
 * @author : xiaoyu
 * @version V1.0
 * @Project: xixi
 * @Package com.xixi.middle.mq.consumer
 * @Description: consumer
 * @date Date : 2020年12月02日 3:44 下午
 */

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BasicConsumer {



    @RabbitListener( queues = "${mq.basic.info.queue.name}",containerFactory = "singleListenerContainer")
    public void consumerMsg(@Payload byte[] msg) {

        try {
            String message = new String(msg,"utf-8");
            log.info("consumer message:{}",message);
        } catch (Exception e) {
            log.error("consumer error msg:{}", msg, e);
        }


    }


}
