package com.liubike.common.queue;

public interface CmdQueue<T> {

    T poll();

    boolean add(T entity);
}
