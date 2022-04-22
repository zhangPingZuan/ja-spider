package io.zpz.tool.spider;

import io.zpz.tool.crawling.CrawlingResponse;
import io.zpz.tool.downloader.FetchResponse;
import io.zpz.tool.engine.DownLoadedEngineEvent;
import io.zpz.tool.engine.core.ResolvableType;
import io.zpz.tool.task.TaskManager;
import io.zpz.tool.windup.FinalProcessor;
import io.zpz.tool.windup.entity.DataRecord;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;
import java.util.stream.Stream;

@Slf4j
@Builder
public class DownLoadedEngineEventSpider extends AbstractSpider<DownLoadedEngineEvent, DataRecord> {

    private final TaskManager taskManager;
    private final FinalProcessor<DataRecord> finalProcessor;

    @Override
    public String getName() {
        return UUID.randomUUID().toString();
    }

    @Override
    public void parse(CrawlingResponse<?> crawlingResponse) {
        // doNothing
        if (!crawlingResponse.getSpiderName().equals(super.name)) return;
        this.spiderItems.stream().filter(spiderItem -> spiderItem.match(crawlingResponse.getUrl()))
                .forEach(spiderItem -> {
                    // handle
                    handleService(spiderItem, crawlingResponse.getOriginResponseString());
                });
    }

    @Override
    public TaskManager getTaskManager() {
        return super.getTaskManager();
    }


    @Override
    public void addSpiderItem(SpiderItem<?> spiderItem) {
        super.addSpiderItem(spiderItem);
    }

    private void handleService(SpiderItem<?> spiderItem, String content) {
        Iterable<?> dataRecords = spiderItem.getResults(content);

    }

    @Override
    public void onEngineEvent(DownLoadedEngineEvent event) {
        log.info("#### 接收到广播事件 ####");

        // 解析后，，发现还有url，，继续提交到 centralEngine
        // 真的要继续提交吗？？？那是不是应该是外面控制层的事情了？？？
        // 这边只要把结果保存下
        if (event.getSource() instanceof FetchResponse<?>) {
            FetchResponse<?> fetchResponse = (FetchResponse<?>) event.getSource();

        } else throw new RuntimeException("不支持的事件源");
    }

    @Override
    public boolean supportsEventType(ResolvableType resolvableType) {
        return ResolvableType.forClass(DownLoadedEngineEvent.class).isAssignableFrom(resolvableType);
    }

    @Override
    public boolean supportsSourceType(Class<?> sourceType) {
        System.out.println("##### 类名:" + sourceType + "#####");
        return Stream.of(FetchResponse.class).anyMatch(clz -> clz.isAssignableFrom(sourceType));
    }


}
