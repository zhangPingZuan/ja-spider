package io.zpz.tool.engine;

import io.zpz.tool.crawling.CrawlingRequest;
import io.zpz.tool.downloader.Downloader;
import io.zpz.tool.schedule.Scheduler;

public interface CentralEngine {

    /**
     * 获取调度器，调度器应该是自己的一个属性，我需要靠它进行调度。
     */
    Scheduler getScheduler();

    /**
     * 获取下载器
     */
    Downloader getDownloader();

    /**
     * 参考spring中的多播器
     */
    EngineEventMulticaster getEngineEventMulticaster();

    /**
     * 接收crawling request
     */
    void receiveCrawlingRequest(CrawlingRequest request);

    /**
     * 引擎启动
     */
    void start();

    /**
     * 引擎关闭
     */
    void stop();
}
