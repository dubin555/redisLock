package com.dubin.redislock.annotation;

import java.lang.annotation.*;

/**
 * Created by dubin on 17/1/18.
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LockedComplexObject {
    String field() default "";
}
