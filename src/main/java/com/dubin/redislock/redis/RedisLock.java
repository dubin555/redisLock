package com.dubin.redislock.redis;

import java.util.Random;

/**
 * Created by dubin on 17/1/18.
 */
public class RedisLock {

    public static final long MILLI_NANO_TIME = 1000 * 1000L;

    public static final String LOCKED = "TRUE";

    public static final Random RANDOM = new Random();

    private String key;
    private RedisClient redisClient;
    private boolean lock = true;

    public RedisLock(String purpose, String key) {
        this.key = purpose + "_" + key + "_lock";
        this.redisClient = RedisFactory.getDefaultClient();
    }

    public RedisLock(String purpose, String key, RedisClient redisClient) {
        this.key = purpose + "_" + key + "_lock";
        this.redisClient = redisClient;
    }

    public boolean lock(long timeout, int expire) {
        long nanoTime = System.nanoTime();
        timeout *= MILLI_NANO_TIME;

        try {
            while (System.nanoTime() - nanoTime < timeout) {
                if (this.redisClient.setnx(key, LOCKED) == 1) {
                    this.redisClient.expire(key, expire);
                    this.lock = true;
                    return this.lock;
                }
                System.out.println("等待锁。。。");
                Thread.sleep(3, RANDOM.nextInt(30));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void unlock() {
        try {
            if (this.lock) {
                this.redisClient.delKey(key);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

}
