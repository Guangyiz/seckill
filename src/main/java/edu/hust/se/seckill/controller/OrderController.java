package edu.hust.se.seckill.controller;

import edu.hust.se.seckill.domain.MiaoshaUser;
import edu.hust.se.seckill.domain.OrderInfo;
import edu.hust.se.seckill.redis.RedisService;
import edu.hust.se.seckill.result.CodeMsg;
import edu.hust.se.seckill.result.Result;
import edu.hust.se.seckill.service.GoodsService;
import edu.hust.se.seckill.service.MiaoshaUserService;
import edu.hust.se.seckill.service.OrderService;
import edu.hust.se.seckill.vo.GoodsVo;
import edu.hust.se.seckill.vo.OrderDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("order")
public class OrderController {
    @Autowired
    MiaoshaUserService userService;

    @Autowired
    RedisService redisService;

    @Autowired
    OrderService orderService;

    @Autowired
    GoodsService goodsService;

    @RequestMapping("/detail")
    @ResponseBody
    public Result<OrderDetailVo> info(Model model, MiaoshaUser user,
                                      @RequestParam("orderId") long orderId) {
        if(user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        OrderInfo order = orderService.getOrderById(orderId);
        if(order == null) {
            return Result.error(CodeMsg.ORDER_NOT_EXIST);
        }
        long goodsId = order.getGoodsId();
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        OrderDetailVo vo = new OrderDetailVo();
        vo.setOrder(order);
        vo.setGoods(goods);
        return Result.success(vo);
    }

}
