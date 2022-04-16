package io.zpz.tool.windup;

public interface DatabaseFinalProcessor {

    void saveAll(Iterable<?> records);

    boolean exist();

    void save();

}
