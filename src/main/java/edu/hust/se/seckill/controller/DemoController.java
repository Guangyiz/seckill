package edu.hust.se.seckill.controller;

import edu.hust.se.seckill.result.CodeMsg;
import edu.hust.se.seckill.result.Result;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DemoController {
    @RequestMapping("/hello")
    @ResponseBody
    public Result<String> demo() {
        return Result.success("hello demo");
    }

    @RequestMapping("/helloerror")
    @ResponseBody
    public Result<String> error() {
        return Result.error(CodeMsg.SYS_ERROR);
    }

    @RequestMapping("/thymeleaf")
    public String thymeleaf(Model model){
        model.addAttribute("name","zgy");
        return "hello";
    }
}
