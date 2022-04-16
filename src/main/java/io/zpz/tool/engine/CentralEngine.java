package io.zpz.tool.engine;

import io.zpz.tool.downloader.Downloader;
import io.zpz.tool.schedule.Scheduler;
import io.zpz.tool.spider.Spider;
import io.zpz.tool.windup.FinalProcessor;

import java.util.Set;

public interface CentralEngine {

    /**
     * 获取调度器
     */
    Scheduler getScheduler();

    /**
     * 获取下载器
     */
    Downloader getDownloader();

    /**
     * 获取spider
     */
    Set<Spider> getSpiders();

    /**
     * 最后处理
     */
    FinalProcessor getFinalProcessor();

    /**
     * 引擎启动
     */
    void start();
}
