package com.jiage.rwlock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MyReadWriteLock {
    private Map<String,Object> map = new HashMap<>();

    private ReadWriteLock rwl = new ReentrantReadWriteLock();

    private Lock r = rwl.readLock();
    private Lock w = rwl.writeLock();

    public Object get(String key){
        try {
            r.lock();
            System.out.println(Thread.currentThread().getName()+" read 操作执行");
            Thread.sleep(1500);
            return map.get(key);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        } finally {
        	System.out.println(Thread.currentThread().getName()+" read 操作结束");
            r.unlock();
        }
    }

    public void put(String key,Object value){
        try {
            w.lock();
            System.out.println(Thread.currentThread().getName()+" write 操作执行");
            Thread.sleep(1500);
            map.put(key,value);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
        	System.out.println(Thread.currentThread().getName()+" write 操作结束");
            w.unlock();
        }
    }
}
