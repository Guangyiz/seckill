package edu.hust.se.seckill.service;

import edu.hust.se.seckill.dao.MiaoshaUserDao;
import edu.hust.se.seckill.domain.MiaoshaUser;
import edu.hust.se.seckill.exception.GlobalException;
import edu.hust.se.seckill.result.CodeMsg;
import edu.hust.se.seckill.util.MD5Util;
import edu.hust.se.seckill.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MiaoshaUserServiceImpl implements MiaoshaUserService{

    @Autowired
    private MiaoshaUserDao miaoshaUserDao;

    public MiaoshaUser getById(Long id) {
        return miaoshaUserDao.getById(id);
    }

    @Override
    public boolean login(LoginVo loginVo) {
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
        return true;
    }
}
