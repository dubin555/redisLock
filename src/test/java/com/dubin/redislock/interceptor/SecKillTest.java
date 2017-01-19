package com.dubin.redislock.interceptor;

import com.dubin.redislock.redis.RedisClient;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.lang.reflect.Proxy;
import java.util.concurrent.CountDownLatch;

/**
 * Created by dubin on 17/1/18.
 */
public class SecKillTest {
    private static Long commidityId1 = 100001L;
    private static Long commidityId2 = 100002L;
    private RedisClient redisClient;

    public static String HOST = "127.0.0.1";
    private JedisPool jedisPool;

    @Before
    public synchronized void beforeTest() throws IOException {
        jedisPool = new JedisPool("127.0.0.1");
    }

    @Test
    public void testSecKill() {
        int threadCount = 1000;
        int splitPoint = 500;
        final CountDownLatch endCount = new CountDownLatch(500);
        final CountDownLatch beginCount = new CountDownLatch(1);
        final SecKillInterface testClass = new SecKillImpl();

        Thread[] threads = new Thread[threadCount];
        for(int i = 0; i < splitPoint; i++) {
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        beginCount.await();
                        SecKillInterface proxy = (SecKillInterface) Proxy.newProxyInstance(SecKillInterface.class.getClassLoader(),
                                new Class[]{SecKillInterface.class}, new CacheLockInterceptor(testClass));
                        proxy.secKill("test", commidityId1);
                        endCount.countDown();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            threads[i].start();
        }

        long startTime = System.currentTimeMillis();
        beginCount.countDown();

        try {
            endCount.await();
            System.out.println(SecKillImpl.inventory.get(commidityId1));
            System.out.println("error count" + CacheLockInterceptor.ERROR_COUNT);
            System.out.println("total cost " + (System.currentTimeMillis() - startTime));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
