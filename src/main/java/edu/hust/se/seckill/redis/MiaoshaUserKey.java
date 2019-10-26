package edu.hust.se.seckill.redis;

public class MiaoshaUserKey extends BasePrefix{
    public static final int TOKEN_EXPIRE = 3600;
    private MiaoshaUserKey(int expireTime,String prefix) {
        super(expireTime,prefix);
    }

    public static MiaoshaUserKey TOKEN = new MiaoshaUserKey(TOKEN_EXPIRE,"tk");
    public static MiaoshaUserKey getById = new MiaoshaUserKey(0, "id");
}
