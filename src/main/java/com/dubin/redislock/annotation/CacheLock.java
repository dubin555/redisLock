package com.dubin.redislock.annotation;

import java.lang.annotation.*;

/**
 * Created by dubin on 17/1/18.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CacheLock {
    String lockedPrefix() default "";
    long timeOut() default 2000;
    int expireTime() default 100000;
}
