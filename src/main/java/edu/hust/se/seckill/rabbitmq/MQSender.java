package edu.hust.se.seckill.rabbitmq;

import edu.hust.se.seckill.redis.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MQSender {
    private static Logger logger = LoggerFactory.getLogger(MQSender.class);

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void sengMiaoshaMessage(Object value){
        String msg = RedisService.beanToString(value);
        logger.info("send msg:{}",msg);
        amqpTemplate.convertAndSend(MQConfig.MiaoshaQUEUE,msg);
    }
//
//    public void sendMessage(Object value){
//        String msg = RedisService.beanToString(value);
//        logger.info("send msg:{}",msg);
//        amqpTemplate.convertAndSend(MQConfig.QUEUE,msg);
//    }
//
//    public void sendTopic(Object value){
//        String msg = RedisService.beanToString(value);
//        logger.info("send topic msg:{}",msg);
//        amqpTemplate.convertAndSend(MQConfig.TOPIC_EXCHANGE,MQConfig.ROUTING_KEY1,msg+1);
//        amqpTemplate.convertAndSend(MQConfig.TOPIC_EXCHANGE,MQConfig.ROUTING_KEY2,msg+2);
//    }
}
