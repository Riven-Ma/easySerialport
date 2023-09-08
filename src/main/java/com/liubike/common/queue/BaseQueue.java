package com.liubike.common.queue;

import java.util.concurrent.BlockingDeque;

public abstract class BaseQueue<T> {

    public BaseQueue() {
    }

    public T poll(BlockingDeque<T> queue) {
        try {
            return queue.takeFirst();
        } catch (Exception var3) {
            return null;
        }
    }

    public boolean add(BlockingDeque<T> queue, T entity) {
        try {
            if (!queue.offerLast(entity)) {
                return false;
            } else {
                return true;
            }
        } catch (Exception var4) {
            return false;
        }
    }
}
