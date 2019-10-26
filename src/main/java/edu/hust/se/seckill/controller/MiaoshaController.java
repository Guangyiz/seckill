package edu.hust.se.seckill.controller;

import edu.hust.se.seckill.domain.MiaoshaOrder;
import edu.hust.se.seckill.domain.MiaoshaUser;
import edu.hust.se.seckill.domain.OrderInfo;
import edu.hust.se.seckill.rabbitmq.MQConfig;
import edu.hust.se.seckill.rabbitmq.MQSender;
import edu.hust.se.seckill.rabbitmq.MiaoshaMessage;
import edu.hust.se.seckill.redis.RedisService;
import edu.hust.se.seckill.result.CodeMsg;
import edu.hust.se.seckill.result.Result;
import edu.hust.se.seckill.service.GoodsService;
import edu.hust.se.seckill.service.MiaoshaService;
import edu.hust.se.seckill.service.OrderService;
import edu.hust.se.seckill.vo.GoodsVo;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import static edu.hust.se.seckill.redis.GoodsKey.getMiaoshaGoodsStock;

@Controller
@RequestMapping("/miaosha")
public class MiaoshaController implements InitializingBean {

    @Autowired
    private GoodsService goodsService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private MiaoshaService miaoshaService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private MQSender mqSender;
    //系统初始化
    @Override
    public void afterPropertiesSet() throws Exception {
        List<GoodsVo> goodsVos = goodsService.listGoodsVo();
        if(goodsVos == null) {
            return ;
        }else {
            for (GoodsVo goodsVo: goodsVos) {
                redisService.set(getMiaoshaGoodsStock, ""+goodsVo.getId(),goodsVo.getStockCount());
            }

        }
    }

    @RequestMapping("/do_miaosha")
    @ResponseBody
    public Result<Integer> doMiaosha(Model model, MiaoshaUser user,
                       @RequestParam("goodsId")long goodsId) {
        if (user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        long stock = redisService.decr(getMiaoshaGoodsStock,""+goodsId);

        //判断库存
        if(stock <= 0) {
            return Result.error(CodeMsg.MIAO_SHA_OVER);
        }

        //判断是否已经秒杀到了
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
        if (order != null) {
            return Result.error(CodeMsg.REPEATE_MIAOSHA);
        }
        MiaoshaMessage miaoshaMessage = new MiaoshaMessage();
        miaoshaMessage.setGoodsId(goodsId);
        miaoshaMessage.setMiaoshaUser(user);
        mqSender.sengMiaoshaMessage(miaoshaMessage);

        return Result.success(0);//排队中


//        if (order != null) {
//            return Result.error(CodeMsg.REPEATE_MIAOSHA);
//        }
//        //判断库存
//        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
//        if (goods.getStockCount() <= 0) {
//            return Result.error(CodeMsg.MIAO_SHA_OVER);
//        }
//        //判断是否已经秒杀到了
//        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
//        if (order != null) {
//            return Result.error(CodeMsg.REPEATE_MIAOSHA);
//        }
//        //减库存 下订单 写入秒杀订单
//        OrderInfo orderInfo = miaoshaService.miaosha(user, goods);
//        model.addAttribute("orderInfo", orderInfo);
//        model.addAttribute("goods", goods);
//        return Result.success(orderInfo);
    }

    /**
     * 秒杀成功
     * orderId:成功
     * -1：秒杀失败
     * 0：秒杀排队
     * @param model
     * @param user
     * @param goodsId
     * @return
     */
    @RequestMapping("/result")
    @ResponseBody
    public Result<Long> miaoshaResult(Model model, MiaoshaUser user,
                                     @RequestParam("goodsId")long goodsId) {
        if (user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }

        long result = miaoshaService.getMiaoshaResult(user.getId(), goodsId);
        return Result.success(result);
    }
}
