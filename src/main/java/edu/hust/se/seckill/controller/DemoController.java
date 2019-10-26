package edu.hust.se.seckill.controller;

import edu.hust.se.seckill.rabbitmq.MQSender;
import edu.hust.se.seckill.result.CodeMsg;
import edu.hust.se.seckill.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DemoController {

    @Autowired
    private MQSender sender;

    @RequestMapping("/hello")
    @ResponseBody
    public Result<String> demo() {
        return Result.success("hello demo");
    }

    @RequestMapping("/helloerror")
    @ResponseBody
    public Result<String> error() {
        return Result.error(CodeMsg.SERVER_ERROR);
    }

    @RequestMapping("/thymeleaf")
    public String thymeleaf(Model model){
        model.addAttribute("name","zgy");
        return "hello";
    }

//    @RequestMapping("/direct")
//    @ResponseBody
//    public String mqrabbit(Model model){
//        sender.sendMessage("hello,rabbit");
//        return "success";
//    }
//
//    @RequestMapping("/topic")
//    @ResponseBody
//    public String mqrabbittopic(Model model){
//        sender.sendTopic("hello,topic");
//        return "success";
//    }
}
