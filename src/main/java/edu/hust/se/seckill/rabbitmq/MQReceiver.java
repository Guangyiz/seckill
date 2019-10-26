package edu.hust.se.seckill.rabbitmq;

import edu.hust.se.seckill.domain.OrderInfo;
import edu.hust.se.seckill.redis.RedisService;
import edu.hust.se.seckill.service.GoodsService;
import edu.hust.se.seckill.service.MiaoshaService;
import edu.hust.se.seckill.vo.GoodsVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MQReceiver {
    private static Logger logger = LoggerFactory.getLogger(MQReceiver.class);

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private MiaoshaService miaoshaService;

    @Autowired
    private AmqpTemplate amqpTemplate;

//    @RabbitListener(queues = MQConfig.QUEUE)
//    public void receive(String msg){
//        logger.info("receive msg:{}",msg);
//    }
//
//    @RabbitListener(queues = MQConfig.TOPIC_QUEUE1)
//    public void receiveTopic1(String msg){
//        logger.info("receive queue1 msg:{}",msg);
//    }
//
//    @RabbitListener(queues = MQConfig.TOPIC_QUEUE2)
//    public void receiveTopic2(String msg){
//        logger.info("receive queue2 msg:{}",msg);
//    }

    @RabbitListener(queues = MQConfig.MiaoshaQUEUE)
    public void receive(String msg){
        logger.info("receive msg:{}",msg);
        MiaoshaMessage miaoshaMessage = RedisService.stringToBean(msg,MiaoshaMessage.class);
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(miaoshaMessage.getGoodsId());
        if(goods.getStockCount() < 0 ) {
            return;
        }
        miaoshaService.miaosha(miaoshaMessage.getMiaoshaUser(), goods);

    }


}
