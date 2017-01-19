package com.dubin.redislock.exception;

/**
 * Created by dubin on 17/1/18.
 */
public class CacheLockException extends RuntimeException {
    public CacheLockException(String s) {
        super(s);
    }
}
