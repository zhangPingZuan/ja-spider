package io.zpz.tool.engine;

import io.zpz.tool.crawling.CrawlingRequest;
import io.zpz.tool.downloader.Downloader;
import io.zpz.tool.schedule.Scheduler;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

@Builder
@Slf4j
public class DefaultCentralEngine implements CentralEngine {

    private final Downloader downloader;

    private final Scheduler scheduler;

    private final EngineEventMulticaster engineEventMulticaster;

    private final Set<CrawlingRequest> crawlingRequests = new HashSet<>();

    /**
     * 防止Builder进行改造
     */
    private final AtomicReference<Thread> curThread = new AtomicReference<>();

    public static void main(String[] args) {
        DefaultCentralEngine.builder()
                .engineEventMulticaster(null)
                .scheduler(null)
                .build();
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
    public void receiveCrawlingRequest(CrawlingRequest request) {
        this.crawlingRequests.add(request);
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


        }
    }

    @Override
    public void stop() {
        curThread.get().interrupt();
    }
}
