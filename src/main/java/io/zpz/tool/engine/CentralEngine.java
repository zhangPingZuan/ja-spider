package io.zpz.tool.engine;

import io.zpz.tool.downloader.Downloader;
import io.zpz.tool.schedule.Scheduler;
import io.zpz.tool.task.TaskManager;


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
     * 任务管理器
     */
    TaskManager getTaskManager();

    /**
     * 引擎启动
     * 启动的时候，要去拉任务管理器
     */
    void start();

    /**
     * 引擎关闭
     */
    void stop();
}
