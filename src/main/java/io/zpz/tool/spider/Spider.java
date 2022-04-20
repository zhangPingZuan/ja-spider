package io.zpz.tool.spider;

import io.zpz.tool.engine.CentralEngine;
import io.zpz.tool.engine.EngineEvent;
import io.zpz.tool.engine.EngineEventListener;

import java.util.Set;

/**
 * 这里描述蜘蛛的功能
 */
public interface Spider<E extends EngineEvent> extends EngineEventListener<E> {

    /**
     * 蜘蛛名称，进程中唯一
     */
    String getName();

    /**
     * 解析应答
     */
    void parse();

    /**
     * 提交request到中央引擎
     */
    void commitUrlToEngine(CentralEngine centralEngine);


    /**
     * xpath集合
     */
    void addXpath(String xpath);

    /**
     * xpath集合
     */
    void addXpathCollection(Set<String> xpaths);


}
