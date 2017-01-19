package com.dubin.redislock.interceptor;

import com.dubin.redislock.annotation.LockedObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dubin on 17/1/18.
 */
public class SecKillImpl implements SecKillInterface {
    static Map<Long, Long> inventory;
    static {
        inventory = new HashMap<>();
        inventory.put(100001L, 10000L);
        inventory.put(100002L, 10000L);
    }

    @Override
    public void secKill(String arg1, @LockedObject Long arg2) {
        reduceInventory(arg2);
    }

    private Long reduceInventory(Long commodityId) {
        inventory.put(commodityId, inventory.get(commodityId) - 1);
        System.out.println("剩余库存:" + inventory.get(commodityId));
        return inventory.get(commodityId);
    }
}
