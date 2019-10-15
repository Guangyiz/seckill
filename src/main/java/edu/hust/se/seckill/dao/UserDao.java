package edu.hust.se.seckill.dao;

import edu.hust.se.seckill.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserDao {
    public User getById(@Param("id")int id	);
}
