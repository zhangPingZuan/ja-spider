package io.zpz.tool.windup;

public interface DatabaseFinalProcessor extends FinalProcessor {

    void saveAll();

    boolean exist();

    void save();

}
