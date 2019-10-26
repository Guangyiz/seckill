package edu.hust.se.seckill.rabbitmq;

import edu.hust.se.seckill.domain.MiaoshaUser;
import lombok.Data;

@Data
public class MiaoshaMessage {
    private MiaoshaUser miaoshaUser;
    private Long goodsId;
}
