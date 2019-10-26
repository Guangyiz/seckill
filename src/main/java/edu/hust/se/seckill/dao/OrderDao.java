package edu.hust.se.seckill.dao;

import edu.hust.se.seckill.domain.MiaoshaOrder;
import edu.hust.se.seckill.domain.OrderInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderDao {

    MiaoshaOrder getMiaoshaOrderByUserIdGoodsId(@Param("id") Long id, @Param("goodsId") long goodsId);

    long insert(OrderInfo orderInfo);

    int insertMiaoshaOrder(MiaoshaOrder miaoshaOrder);

    OrderInfo getOrderById(long orderId);
}
