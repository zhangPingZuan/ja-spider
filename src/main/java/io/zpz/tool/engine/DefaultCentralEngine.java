package io.zpz.tool.engine;

import io.zpz.tool.crawling.CrawlingRequest;
import io.zpz.tool.downloader.Downloader;
import io.zpz.tool.downloader.FetchRequest;
import io.zpz.tool.downloader.FetchResponse;
import io.zpz.tool.downloader.HttpClientRequest;
import io.zpz.tool.schedule.Scheduler;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

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
            List<FetchRequest> fetchRequestList = this.crawlingRequests.stream()
                    .map(crawlingRequest -> HttpClientRequest.builder()
                            .url(crawlingRequest.getUrl())
                            .headers(new HashMap<>())
                            .build()).collect(Collectors.toList());

            List<FetchResponse<?>> fetchResponses = fetchRequestList.stream().map(this.downloader::fetch)
                    .collect(Collectors.toList());

            // 用多播器广播一下
            fetchResponses.forEach(fetchResponse -> this.engineEventMulticaster.multicast(new DownLoadedEngineEvent(fetchResponse.getOriginResponse())));

        }
    }

    @Override
    public void stop() {
        curThread.get().interrupt();
    }
}
