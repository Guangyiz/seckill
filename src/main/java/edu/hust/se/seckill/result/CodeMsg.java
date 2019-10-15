package edu.hust.se.seckill.result;

import lombok.Data;

@Data
public class CodeMsg {
    private int code;
    private String msg;

    //通用异常
    public static CodeMsg SUCCESS = new CodeMsg(0,"success");
    public static CodeMsg SYS_ERROR = new CodeMsg(500,"服务端异常");

    //其他异常

    public CodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
