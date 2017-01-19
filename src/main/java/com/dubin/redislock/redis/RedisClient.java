package com.dubin.redislock.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created by dubin on 17/1/18.
 */
public class RedisClient {
    public final JedisPool jedisPool;

    public RedisClient(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    public Object getByKey(String key) {
        Jedis jedis = jedisPool.getResource();
        Object o = null;
        try {
            o = jedis.get(key);
        }finally {
            jedis.close();
        }
        return o;
    }

    public Long setnx(String key, String value) {
        Jedis jedis = jedisPool.getResource();
        try {
            Long result = jedis.setnx(key, value);
            System.out.println("setnx key=" + key
                    + ",value=" + value);
            return result;
        }finally {
            jedis.close();
        }
    }

    public Long expire(String key, Integer seconds) {
        Jedis jedis = jedisPool.getResource();
        Long success = 1l;
        try {
            success = jedis.expire(key, seconds);
        } finally {
            jedis.close();
        }
        return success;
    }


    public boolean delKey(String key) {
        Jedis jedis = jedisPool.getResource();
        try {
            System.out.println("del key=" + key);
            jedis.del(key);
            return true;
        } finally {
            jedis.close();
        }
    }
}
