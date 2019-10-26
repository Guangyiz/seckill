package edu.hust.se.seckill.service;

import edu.hust.se.seckill.vo.GoodsVo;

import java.util.List;

public interface GoodsService {
    List<GoodsVo> listGoodsVo();

    GoodsVo getGoodsVoByGoodsId(long goodsId);

    boolean reduceStock(GoodsVo goods);
}
