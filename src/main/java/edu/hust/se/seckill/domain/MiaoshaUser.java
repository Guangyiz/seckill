package edu.hust.se.seckill.domain;

import lombok.Data;

import java.util.Date;

@Data
public class MiaoshaUser {
    private Long id;
    private String nickname;
    private String password;
    private String salt;
    private String head;
    private Date createTime;
    private Date updateTime;
    private Integer loginCount;
}
