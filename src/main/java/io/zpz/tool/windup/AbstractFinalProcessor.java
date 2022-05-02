package io.zpz.tool.windup;


import lombok.extern.slf4j.Slf4j;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
public class AbstractFinalProcessor<T> implements FinalProcessor<T> {

    protected final Queue<T> processorDataQueue = new ArrayDeque<>();
    protected final AtomicReference<Thread> curThread = new AtomicReference<>();

    @Override
    public void addDataRecords(Iterable<T> dataRecords) {
        throw new UnsupportedOperationException("请实现子类");
    }

    @Override
    public void addDataRecord(T dataRecord) {
        throw new UnsupportedOperationException("请实现子类");
    }


    @Override
    public void start() {
        throw new UnsupportedOperationException("请实现子类");
    }


    @Override
    public void stop() {
        throw new UnsupportedOperationException("请实现子类");
    }
}
