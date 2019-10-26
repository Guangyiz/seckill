package edu.hust.se.seckill.redis;

public class UserKey extends BasePrefix{
    public static final int TOKEN_EXPIRE = 3600*24 * 2;
    private UserKey(String prefix) {
        super(prefix);
    }
    public static UserKey getById = new UserKey("id");
    public static UserKey getByName = new UserKey("name");
    public static UserKey TOKEN = new UserKey("tk");
}
