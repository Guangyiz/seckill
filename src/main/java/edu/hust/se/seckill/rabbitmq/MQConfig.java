package edu.hust.se.seckill.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class MQConfig {
    //direct 模式
    public static final String QUEUE = "queue";

    //topic 模式
    public static final String TOPIC_QUEUE1 = "topic.queue1";
    public static final String TOPIC_QUEUE2 = "topic.queue2";
    public static final String TOPIC_EXCHANGE = "topic.exchange";
    public static final String ROUTING_KEY1 = "topic.key1";
    public static final String ROUTING_KEY2 = "topic.#";//#0个或多个单词

    //fanout 模式

    //header 模式

    //秒杀接入
    public static final String MiaoshaQUEUE = "miaoshaqueue";

    @Bean
    public Queue miaoshaQueue(){
        return new Queue(MiaoshaQUEUE,true);
    }

//    @Bean
//    public Queue queue(){
//        return new Queue(QUEUE,true);
//    }
//
//    @Bean
//    public Queue topicQueue1(){
//        return new Queue(TOPIC_QUEUE1,true);
//    }
//
//    @Bean
//    public Queue topicQueue2(){
//        return new Queue(TOPIC_QUEUE2,true);
//    }
//
//    @Bean
//    public TopicExchange topicExchange(){
//        return new TopicExchange(TOPIC_EXCHANGE);
//    }
//
//    @Bean
//    public Binding topicBinding1(){
//        return BindingBuilder.bind(topicQueue1()).to(topicExchange()).with(ROUTING_KEY1);
//    }
//
//    @Bean
//    public Binding topicBinding2(){
//        return BindingBuilder.bind(topicQueue2()).to(topicExchange()).with(ROUTING_KEY2);
//    }
}
