package com.dubin.redislock.util;

import com.alibaba.fastjson.JSON;

/**
 * Created by dubin on 17/1/18.
 */
public class Util {
    public static String beanToJson(Object o) {
        return JSON.toJSONString(o);
    }

    public static <T> T jsonToBean(String json, Class<T> cls) {
        return JSON.parseObject(json, cls);
    }
}
