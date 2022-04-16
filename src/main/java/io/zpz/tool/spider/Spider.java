package io.zpz.tool.spider;

/**
 * 这里描述蜘蛛的功能
 */
public interface Spider {

    /**
     * 蜘蛛名称
     */
    String getName();


    /**
     * 解析请求
     */
    void parse();

    /**
     * 提交url
     */
    void commitToEngine();


}
