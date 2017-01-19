package com.dubin.redislock.interceptor;

import com.dubin.redislock.annotation.CacheLock;
import com.dubin.redislock.annotation.LockedObject;

/**
 * Created by dubin on 17/1/18.
 */
public interface SecKillInterface {
    @CacheLock(lockedPrefix = "TEST_PREFIX")
    public void secKill(String arg1, @LockedObject Long arg2);
}
