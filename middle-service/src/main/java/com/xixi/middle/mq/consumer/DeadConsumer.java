package com.xixi.middle.mq.consumer;

import com.alibaba.fastjson.JSON;
import com.xixi.middle.bo.DeadInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

/**
 * @author : xiaoyu
 * @version V1.0
 * @Project: xixi
 * @Package com.xixi.middle.mq.consumer
 * @Description: dead-consumer
 * @date Date : 2020年12月04日 4:30 下午
 */
@Slf4j
@Service
public class DeadConsumer {

    @RabbitListener(queues = "${mq.consumer.queue.name}",containerFactory = "singleListenerContainer")
    public void consumerMsg(@Payload DeadInfo info) {
        log.info("真正队列监听到消息：{}", JSON.toJSONString(info));
    }
}
