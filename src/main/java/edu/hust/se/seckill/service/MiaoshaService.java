package edu.hust.se.seckill.service;

import edu.hust.se.seckill.domain.MiaoshaUser;
import edu.hust.se.seckill.domain.OrderInfo;
import edu.hust.se.seckill.vo.GoodsVo;

public interface MiaoshaService {
    OrderInfo miaosha(MiaoshaUser user, GoodsVo goods);

    long getMiaoshaResult(Long userId, long goodsId);
}
