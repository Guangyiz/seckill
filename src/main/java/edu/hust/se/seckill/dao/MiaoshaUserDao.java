package edu.hust.se.seckill.dao;

import edu.hust.se.seckill.domain.MiaoshaUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MiaoshaUserDao {

    public MiaoshaUser getById(@Param("id")long id);
}
