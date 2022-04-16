package io.zpz.tool.windup;

public interface FinalProcessor<T> {

    void process(Iterable<T> records);


}
