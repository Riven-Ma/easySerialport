package com.liubike.common;

import java.util.concurrent.LinkedBlockingQueue;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MyThreadPool {

    private static final ThreadPoolExecutor threadPool = new ThreadPoolExecutor(30, 64, 30,
            TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(128),
            new ThreadPoolExecutor.DiscardOldestPolicy());


    public static ThreadPoolExecutor getThreadPool() {
        return threadPool;
    }

}
