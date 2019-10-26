package edu.hust.se.seckill.redis;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Service
public class RedisService {

    @Autowired
    private JedisPool jedisPool;

    public <T> T get(KeyPrefix keyPrefix, String key,Class<T> clazz) {
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            T t = stringToBean(jedis.get(keyPrefix.getPrefix() + key),clazz);
            return t;
        }finally {
            returnToPool(jedis);
        }
    }

    public <T> boolean set(KeyPrefix keyPrefix, String key,T value) {
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            String str = beanToString(value);
            if(str == null || str.length() <= 0) {
                return false;
            }
            if(keyPrefix.expireSeconds() <= 0) {
                return jedis.set(keyPrefix.getPrefix() + key,str) != null;
            }else {
                return jedis.setex(keyPrefix.getPrefix() + key,keyPrefix.expireSeconds(), str) != null;
            }

        }finally {
            returnToPool(jedis);
        }
    }

    public boolean exists(KeyPrefix keyPrefix,String key) {
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            return jedis.exists(keyPrefix.getPrefix() + key);
        }finally {
            returnToPool(jedis);
        }
    }

    public Long incr(KeyPrefix keyPrefix,String key) {
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            return jedis.incr(keyPrefix.getPrefix() + key);
        }finally {
            returnToPool(jedis);
        }
    }

    public Long decr(KeyPrefix keyPrefix,String key) {
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            return jedis.decr(keyPrefix.getPrefix() + key);
        }finally {
            returnToPool(jedis);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T stringToBean(String str,Class<T> clazz) {
        if(str == null || str.length() <= 0|| clazz == null) {
            return null;
        }
        if(clazz == int.class || clazz == Integer.class) {
            return (T)Integer.valueOf(str);
        }else if(clazz == long.class || clazz == Long.class) {
            return (T)Long.valueOf(str);
        }else {
            return JSON.toJavaObject(JSON.parseObject(str),clazz);
        }
    }

    public static <T> String beanToString(T value) {
        if(value == null) {
            return null;
        }
        Class<?> clazz = value.getClass();
        if(clazz == int.class || clazz == Integer.class) {
            return "" + value;
        }else if(clazz == String.class) {
            return (String)value;
        }else if(clazz == long.class || clazz == Long.class) {
            return "" + value;
        }else {
            return JSON.toJSONString(value);
        }
    }

    public void returnToPool(Jedis jedis) {
        if(jedis != null) {
            jedis.close();
        }
    }


}
