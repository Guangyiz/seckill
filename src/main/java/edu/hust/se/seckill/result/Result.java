package edu.hust.se.seckill.result;

import lombok.Data;

@Data
public class Result<T> {
    private int code;
    private String msg;
    private T data;

    /**
     * 成功
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(data);
    }

    public static <T> Result<T> error(CodeMsg msg) {
        return new Result<>(msg);
    }

    private Result(T data){
        this.code = 0;
        this.msg = "success";
        this.data = data;
    }

    private Result(CodeMsg msg) {
        if(msg == null) {
            return;
        }
        this.code = msg.getCode();
        this.msg = msg.getMsg();
    }
}
