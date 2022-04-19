package io.zpz.tool.engine;


/**
 * 多播器
 */
public interface EngineEventMulticaster {

    /**
     * 注册监听对象
     */
    void addEngineEventListener(EngineEventListener<?> engineEventListener);


    /**
     * 移除监听对象
     */
    void removeEngineEventListener(EngineEventListener<?> engineEventListener);

    /**
     * 移除所有的监听对象
     */
    void removeAllListeners();

    /**
     * 广播事件
     */
    void multicast(EngineEvent engineEvent);


}
