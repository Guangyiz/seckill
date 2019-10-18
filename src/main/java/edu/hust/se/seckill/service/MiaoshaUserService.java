package edu.hust.se.seckill.service;

import edu.hust.se.seckill.domain.MiaoshaUser;
import edu.hust.se.seckill.result.CodeMsg;
import edu.hust.se.seckill.vo.LoginVo;

public interface MiaoshaUserService {

    boolean login(LoginVo loginVo);
}
