package edu.hust.se.seckill.service;

import edu.hust.se.seckill.domain.User;
import edu.hust.se.seckill.result.CodeMsg;
import edu.hust.se.seckill.vo.LoginVo;

public interface UserService {
    User getById(int id);
}
