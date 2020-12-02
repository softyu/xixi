package com.xixi.middle.mq.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

/**
 * @author : xiaoyu
 * @version V1.0
 * @Project: xixi
 * @Package com.xixi.middle.mq.producer
 * @Description: publisher
 * @date Date : 2020年12月02日 3:45 下午
 */
@Slf4j
@Service
public class BasicPublisher {
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    Environment env;


    public void sendMsg(byte[] message) {

        try {

            rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());

            rabbitTemplate.setExchange(env.getProperty("mq.basic.info.exchange.name"));

            rabbitTemplate.setRoutingKey(env.getProperty("mq.basic.info.routing.key.name"));

            Message msg = MessageBuilder.withBody(message).build();

            rabbitTemplate.convertAndSend(msg);
        } catch (Exception e) {
            log.error("send error msg:{}", message, e);
        }


    }


}
