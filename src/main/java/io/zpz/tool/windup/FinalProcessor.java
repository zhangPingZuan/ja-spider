package io.zpz.tool.windup;

/**
 * 对外提供接收数据的接口，至于怎么处理，外面不需要关心。
 */
public interface FinalProcessor<T> {

    void addDataRecords(Iterable<T> dataRecords);

    void addDataRecord(T dataRecord);

    void start();

    void stop();

}
