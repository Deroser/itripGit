package com.kgc.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;

/**
 * redisAPI
 */
@Component
public class RedisAPI {
    @Resource
    private JedisPool jedisPool;

    public JedisPool getJedisPool() {
        return jedisPool;
    }

    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    public RedisAPI() {
    }

    public RedisAPI(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    /**
     * 存字符串数据
     * @param key
     * @param value
     * @return
     */
    public boolean set(String key,String value){
        try {
            Jedis jedis = jedisPool.getResource();
            jedis.set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 存数据 过期时间
     * @param key
     * @param seconds 过期时间
     * @param value
     * @return
     */
    public boolean set(String key,int seconds,String value){
        try {
            Jedis jedis = jedisPool.getResource();
            jedis.setex(key,seconds, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 判断key是否存在
     * @param key
     * @return
     */
    public boolean exist(String key){
        try {
            Jedis jedis = jedisPool.getResource();
            return jedis.exists(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 通过key获取数据
     * @param key
     * @return
     */
    public String get(String key){
        String value = null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            value = jedis.get(key);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            returnResouces(jedisPool,jedis);
        }
        return value;
    }
    /**
     * 返还连接池
     */
    public static void returnResouces(JedisPool jedisPool,Jedis jedis){
        if (jedis != null){
            jedisPool.returnResource(jedis);
        }
    }

    /**
     * 查询当前key的过期时间，当key不存在的时候返回-2，当key存在但没有设置过期时间时返回-1，
     * 否则以秒为单位返回key的剩余时间
     * @param key
     * @return
     */
    public Long ttl(String key){
        try {
            Jedis jedis = jedisPool.getResource();
            return jedis.ttl(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (long) -2;
    }
    //删除
    public void delete(String key){
        try {
            Jedis jedis = jedisPool.getResource();
            jedis.del(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
