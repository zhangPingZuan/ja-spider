package io.zpz.tool.spider;

import io.zpz.tool.crawling.CrawlingResponse;
import io.zpz.tool.engine.EngineEvent;
import io.zpz.tool.engine.EngineEventListener;
import io.zpz.tool.task.TaskManager;
import io.zpz.tool.windup.FinalProcessor;

/**
 * 这里描述蜘蛛的功能
 */
public interface Spider<E extends EngineEvent, R> extends EngineEventListener<E> {

    void setSpiderKey(String spiderKey);

    /**
     * 蜘蛛名称，进程中唯一
     */
    String getSpiderKey();

    /**
     * 解析应答
     */
    void parse(CrawlingResponse<?> crawlingResponse);

    /**
     * 添加taskManager
     */
    TaskManager getTaskManager();

    /**
     * 添加FinalProcessor
     */
    FinalProcessor<R> getFinalProcessor();

    /**
     * 添加spider iter
     */
    void addSpiderItem(SpiderItem<R> spiderItem);

}
