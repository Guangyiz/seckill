package edu.hust.se.seckill.service;

import edu.hust.se.seckill.domain.MiaoshaOrder;
import edu.hust.se.seckill.domain.MiaoshaUser;
import edu.hust.se.seckill.domain.OrderInfo;
import edu.hust.se.seckill.vo.GoodsVo;

public interface OrderService {

    MiaoshaOrder getMiaoshaOrderByUserIdGoodsId(Long id, long goodsId);

    OrderInfo createOrder(MiaoshaUser user, GoodsVo goods);

    OrderInfo getOrderById(long orderId);
}
