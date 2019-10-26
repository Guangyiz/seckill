package edu.hust.se.seckill.vo;

import edu.hust.se.seckill.domain.OrderInfo;
import lombok.Data;

@Data
public class OrderDetailVo {
    private GoodsVo goods;
    private OrderInfo order;
}
