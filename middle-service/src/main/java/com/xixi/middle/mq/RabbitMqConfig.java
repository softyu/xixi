package com.xixi.middle.mq;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Maps;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.sound.midi.Track;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : xiaoyu
 * @version V1.0
 * @Project: xixi
 * @Package com.xixi.middle.mq
 * @Description: mq
 * @date Date : 2020年12月02日 2:12 下午
 */
@Slf4j
@Configuration
public class RabbitMqConfig {

    /**
     * 字段装配工厂
     */
    @Autowired
    CachingConnectionFactory cachingConnectionFactory;

    /**
     * 自动装配消息监听器
     */
    @Autowired
    SimpleRabbitListenerContainerFactoryConfigurer simpleRabbitListenerContainerFactoryConfigurer;

    @Autowired
    Environment env;


    @Bean(name = "singleListenerContainer")
    public SimpleRabbitListenerContainerFactory singleContainer() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(cachingConnectionFactory);
        // 手动提交 no  auto manual
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);

        factory.setMessageConverter(new Jackson2JsonMessageConverter());


        factory.setConcurrentConsumers(1);
        factory.setMaxConcurrentConsumers(1);
        factory.setPrefetchCount(1);

        return factory;
    }


    @Bean(name = "multiListenerContainer")
    public SimpleRabbitListenerContainerFactory multiContainer() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();

        simpleRabbitListenerContainerFactoryConfigurer.configure(factory, cachingConnectionFactory);

        factory.setMessageConverter(new Jackson2JsonMessageConverter());


        factory.setAcknowledgeMode(AcknowledgeMode.NONE);
        factory.setConcurrentConsumers(10);
        factory.setMaxConcurrentConsumers(15);
        factory.setPrefetchCount(10);

        return factory;
    }


    @Bean(name = "rabbitTemplate")
    public RabbitTemplate getRabbitTemplate() {

        cachingConnectionFactory.setPublisherConfirms(true);

        cachingConnectionFactory.setPublisherReturns(true);

        RabbitTemplate rabbitTemplate = new RabbitTemplate(cachingConnectionFactory);
        rabbitTemplate.setMandatory(true);

        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> log.info("send success:{},{},{}", correlationData, ack, cause));


        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            log.info("send error:{},{},{},{},{}", message, replyCode, replyText, exchange, routingKey);
        });

        return rabbitTemplate;
    }


    @Bean
    public Queue basicQueue() {
        return new Queue(env.getProperty("mq.basic.info.queue.name"));

    }

    /**
     * create exchange
     *
     * @return
     */
    @Bean
    public DirectExchange basicExchange() {
        return new DirectExchange(env.getProperty("mq.basic.info.exchange.name"));
    }


    /**
     * create binding
     *
     * @return
     */
    @Bean
    public Binding basicBinding() {
        return BindingBuilder.bind(basicQueue()).to(basicExchange()).with(env.getProperty("mq.basic.info.routing.key.name"));
    }


    // ===========================死信队列==============================================

    /**
     * 创建死信队列
     *
     * @return
     */
    @Bean
    public Queue basicDeadQueue() {
        Map<String, Object> map = new HashMap<>();
        // 死信交换机
        map.put("x-dead-letter-exchange", env.getProperty("mq.dead.exchange.name"));
        // 死信路由
        map.put("x-dead-letter-routing-key", env.getProperty("mq.dead.routing.key.name"));
        // 设置ttl ,此处设置为10秒
        map.put("x-message-ttl", 10000);
        return new Queue(env.getProperty("mq.dead.queue.name"), true, false, false, map);
    }

    /**
     * 创建交换机，面向生产者
     *
     * @return
     */
    @Bean
    public TopicExchange basicProducerExchange() {
        return new TopicExchange(env.getProperty("mq.producer.basic.exchange.name"), true, false);
    }

    /**
     * 创建死信队列绑定
     *
     * @return
     */
    @Bean
    public Binding basicProducerBinding() {
        return BindingBuilder.bind(basicDeadQueue()).to(basicProducerExchange()).with(env.getProperty("mq.producer.basic.routing.key.name"));
    }

    /**
     * 真正的消费者队列
     *
     * @return
     */
    @Bean
    public Queue realConsumerQueue() {
        return new Queue(env.getProperty("mq.consumer.queue.name"), true);
    }

    @Bean
    public TopicExchange basicDeadExchange() {
        return new TopicExchange(env.getProperty("mq.dead.exchange.name"), true, false);
    }

    @Bean
    public Binding basicDeadBinding() {
        return BindingBuilder.bind(realConsumerQueue()).to(basicDeadExchange()).with(env.getProperty("mq.dead.routing.key.name"));
    }

}
