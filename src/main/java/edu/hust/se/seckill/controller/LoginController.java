package edu.hust.se.seckill.controller;

import edu.hust.se.seckill.result.CodeMsg;
import edu.hust.se.seckill.result.Result;
import edu.hust.se.seckill.service.MiaoshaUserService;
import edu.hust.se.seckill.service.UserService;
import edu.hust.se.seckill.vo.LoginVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequestMapping("login")
public class LoginController {

    private static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private MiaoshaUserService userService;

    @RequestMapping("/to_login")
    public String toLogin(){
        return "login";
    }

    @RequestMapping("/do_login")
    @ResponseBody
    public Result<Boolean> doLogin(HttpServletResponse response,@Valid LoginVo loginVo){
        logger.info(loginVo.toString());
        userService.login(response,loginVo);
        return Result.success(true);

    }
}
