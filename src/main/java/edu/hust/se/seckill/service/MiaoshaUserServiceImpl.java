package edu.hust.se.seckill.service;

import edu.hust.se.seckill.dao.MiaoshaUserDao;
import edu.hust.se.seckill.domain.MiaoshaUser;
import edu.hust.se.seckill.domain.User;
import edu.hust.se.seckill.exception.GlobalException;
import edu.hust.se.seckill.redis.MiaoshaUserKey;
import edu.hust.se.seckill.redis.RedisService;
import edu.hust.se.seckill.redis.UserKey;
import edu.hust.se.seckill.result.CodeMsg;
import edu.hust.se.seckill.util.MD5Util;
import edu.hust.se.seckill.util.UUIDUtil;
import edu.hust.se.seckill.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Service
public class MiaoshaUserServiceImpl implements MiaoshaUserService{

    @Autowired
    private MiaoshaUserDao miaoshaUserDao;

    @Autowired
    private RedisService redisService;

    public MiaoshaUser getById(Long id) {
        //取缓存
        MiaoshaUser user = redisService.get(MiaoshaUserKey.getById, ""+id, MiaoshaUser.class);
        if(user != null) {
            return user;
        }
        //取数据库
        user = miaoshaUserDao.getById(id);
        if(user != null) {
            redisService.set(MiaoshaUserKey.getById, ""+id, user);
        }
        return user;
    }

    @Override
    public boolean login(HttpServletResponse response,LoginVo loginVo) {
        if(loginVo == null) {
            throw new GlobalException(CodeMsg.MOBILE_PASSWORD_EMPTY);
        }
        String mobile = loginVo.getMobile();
        String formPass = loginVo.getPassword();

        //判断手机号是否存在
        MiaoshaUser user = getById(Long.parseLong(mobile));
        if(user == null) {
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST) ;
        }
        String dbPass = user.getPassword();
        String saltDB = user.getSalt();
        String calcPass = MD5Util.formPassToDBPass(formPass, saltDB);
        if(!calcPass.equals(dbPass)) {
            throw new GlobalException(CodeMsg.PASSWORD_ERROR) ;
        }
        //生成cookie
        String token = UUIDUtil.uuid();
        addCookie(response,token,user);

        return true;
    }

    @Override
    public MiaoshaUser getUserByToken(HttpServletResponse response,String token) {
        if(token ==null) {
            return null;
        }
        MiaoshaUser user = redisService.get(MiaoshaUserKey.TOKEN, token, MiaoshaUser.class);
        if(user != null) {
            addCookie(response,token,user);
        }

        return user;
    }

    private void addCookie(HttpServletResponse response,String token, MiaoshaUser user){

        redisService.set(MiaoshaUserKey.TOKEN,token,user);
        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN, token);
        cookie.setMaxAge(MiaoshaUserKey.TOKEN.expireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
