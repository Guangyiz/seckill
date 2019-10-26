package edu.hust.se.seckill.service;

import edu.hust.se.seckill.domain.MiaoshaUser;
import edu.hust.se.seckill.result.CodeMsg;
import edu.hust.se.seckill.vo.LoginVo;

import javax.servlet.http.HttpServletResponse;

public interface MiaoshaUserService {

    public static final String COOKIE_NAME_TOKEN = "token";

    boolean login(HttpServletResponse response,LoginVo loginVo);

    MiaoshaUser getUserByToken(HttpServletResponse response,String token);
}
