package com.xixi.middle.mq.producer;

import com.xixi.middle.bo.DeadInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.AbstractJavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

/**
 * @author : xiaoyu
 * @version V1.0
 * @Project: xixi
 * @Package com.xixi.middle.mq.producer
 * @Description: dead-producer
 * @date Date : 2020年12月04日 4:31 下午
 */
@Slf4j
@Service
public class DeadPublisher {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private Environment env;

    public void sendMsg(DeadInfo deadInfo) {
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        rabbitTemplate.setExchange(env.getProperty("mq.producer.basic.exchange.name"));
        rabbitTemplate.setRoutingKey(env.getProperty("mq.producer.basic.routing.key.name"));
        rabbitTemplate.convertAndSend(deadInfo, message -> {
            MessageProperties properties = message.getMessageProperties();
            properties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
            properties.setHeader(AbstractJavaTypeMapper.DEFAULT_CONTENT_CLASSID_FIELD_NAME, DeadInfo.class);
            // 死信队列和消息都设置了时间则取更小的
            properties.setExpiration(String.valueOf(10000));
            return message;
        });
        log.info("死信队列实战==发送消息对象为：{}", deadInfo);
    }
}
