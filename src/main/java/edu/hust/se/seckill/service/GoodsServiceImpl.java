package edu.hust.se.seckill.service;

import edu.hust.se.seckill.dao.GoodsDao;
import edu.hust.se.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService{
    @Autowired
    private GoodsDao goodsDao;
    @Override
    public List<GoodsVo> listGoodsVo() {
        return goodsDao.listGoodsVo();
    }

    @Override
    public GoodsVo getGoodsVoByGoodsId(long goodsId) {
        return goodsDao.getGoodsVoByGoodsId(goodsId);
    }

    @Override
    public boolean  reduceStock(GoodsVo goods) {
        int res =  goodsDao.reduceStock(goods.getId());
        return res > 0;
    }
}
