package io.zpz.tool.engine;

import io.zpz.tool.downloader.Downloader;
import io.zpz.tool.downloader.FetchRequest;
import io.zpz.tool.downloader.FetchResponse;
import io.zpz.tool.downloader.HttpClientRequest;
import io.zpz.tool.schedule.Scheduler;
import io.zpz.tool.task.TaskManager;
import io.zpz.tool.util.UserAgentUtil;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Builder
@Slf4j
public class DefaultCentralEngine implements CentralEngine {

    private final Downloader downloader;

    private final Scheduler scheduler;

    private final TaskManager taskManager;

    private final EngineEventMulticaster engineEventMulticaster;

    /**
     * 防止Builder进行改造
     */
    private final AtomicReference<Thread> curThread = new AtomicReference<>();

    private final Integer DEFAULT_SIZE = 50;


    @Override
    public TaskManager getTaskManager() {
        return this.taskManager;
    }

    @Override
    public EngineEventMulticaster getEngineEventMulticaster() {
        return this.engineEventMulticaster;
    }

    @Override
    public Scheduler getScheduler() {
        return this.scheduler;
    }

    @Override
    public Downloader getDownloader() {
        return this.downloader;
    }

    @Override
    public void start() {

        // 开启一个线程去处理请求
        curThread.set(new Thread(() -> {
            while (!curThread.get().isInterrupted()) {
                handleSpiderService();
            }
            log.info("curThread status:{}", curThread.get().isInterrupted());
            log.info("####finish####");
        }));
        curThread.get().start();
    }

    private void handleSpiderService() {

        if (this.scheduler != null) {
            // 通过scheduler处理业务
        } else {

            // 将crawling request转化成fetch request
            List<FetchRequest> fetchRequestList = this.taskManager.pollCrawlingRequests(DEFAULT_SIZE).stream()
                    .map(crawlingRequest -> HttpClientRequest.builder()
                            .url(crawlingRequest.getUrl())
                            .spiderKey(crawlingRequest.getSpiderKey())
                            .headers(UserAgentUtil.getNormalAgent())
                            .build()).collect(Collectors.toList());

            if (CollectionUtils.isEmpty(fetchRequestList)) {
                // 休息一下
                try {
                    log.info("fetchRequestList是空的，我先睡一下5s！！！");
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // todo delete 避免把人家网站搞崩溃
//            delay500ms();

            // 将拿到response
            List<FetchResponse<?>> fetchResponses = fetchRequestList.parallelStream().map(this.downloader::fetch)
                    .collect(Collectors.toList());


            // 用多播器广播一下
            fetchResponses.forEach(fetchResponse -> {
                DownLoadedEngineEvent downLoadedEngineEvent = new DownLoadedEngineEvent(fetchResponse);
                this.engineEventMulticaster.multicast(downLoadedEngineEvent);
            });

        }
    }

    private void delay500ms() {

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        curThread.get().interrupt();
    }
}
