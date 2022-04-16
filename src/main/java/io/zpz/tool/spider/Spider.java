package io.zpz.tool.spider;

import io.zpz.tool.engine.CentralEngine;

/**
 * 这里描述蜘蛛的功能
 */
public interface Spider {

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


}
