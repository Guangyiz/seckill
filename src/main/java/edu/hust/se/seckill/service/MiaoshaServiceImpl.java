package edu.hust.se.seckill.service;

import edu.hust.se.seckill.dao.OrderDao;
import edu.hust.se.seckill.domain.MiaoshaOrder;
import edu.hust.se.seckill.domain.MiaoshaUser;
import edu.hust.se.seckill.domain.OrderInfo;
import edu.hust.se.seckill.redis.MiaoshaKey;
import edu.hust.se.seckill.redis.RedisService;
import edu.hust.se.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MiaoshaServiceImpl implements MiaoshaService{
    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    private RedisService redisService;


    @Override
    @Transactional
    public OrderInfo miaosha(MiaoshaUser user, GoodsVo goods) {
        //减库存 下订单 写入秒杀订单
        boolean result = goodsService.reduceStock(goods);
        if(result) {
            //order_info maiosha_order
            return orderService.createOrder(user, goods);
        }else {
            setGoodOver(goods.getId());
            return null;
        }

    }

    @Override
    public long getMiaoshaResult(Long userId, long goodsId) {
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(userId,goodsId);
        if(order != null) {
            return order.getOrderId();
        }else {
            boolean isOver = getGoodOver(goodsId);
            if(isOver) {
                return -1;
            }else {
                return 0;
            }
        }
    }

    private boolean setGoodOver(long goodsId) {
        return redisService.set(MiaoshaKey.isGoodsOver,""+goodsId,true);
    }

    private boolean getGoodOver(long goodsId) {
        return redisService.exists(MiaoshaKey.isGoodsOver,""+goodsId);
    }
}
