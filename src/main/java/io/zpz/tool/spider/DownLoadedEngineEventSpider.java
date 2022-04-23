package io.zpz.tool.spider;

import io.zpz.tool.crawling.CrawlingResponse;
import io.zpz.tool.engine.DownLoadedEngineEvent;
import io.zpz.tool.engine.core.ResolvableType;
import io.zpz.tool.task.TaskManager;
import io.zpz.tool.windup.FinalProcessor;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.util.stream.Stream;

@Slf4j
@Builder
public class DownLoadedEngineEventSpider extends AbstractSpider<DownLoadedEngineEvent> {

    private final TaskManager taskManager;
    private final FinalProcessor finalProcessor;

    @Override
    public String getSpiderKey() {
        return super.spiderKey;
    }

    @Override
    public void parse(CrawlingResponse<?> crawlingResponse) {
        // doNothing
        if (!crawlingResponse.getSpiderKey().equals(super.spiderKey)) return;
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
        super.spiderItems.add(spiderItem);
    }

    private void handleService(SpiderItem<?> spiderItem, String content) {

        SpiderItemResult spiderItemResult = spiderItem.getResults(content);
        finalProcessor.process(spiderItemResult.getRecords());

        // 吐出新请求

    }

    @Override
    public void onEngineEvent(DownLoadedEngineEvent event) {
        log.info("#### spider:{} 接收到广播事件 ####", super.getSpiderKey());
        this.parse((CrawlingResponse<?>) event.getSource());
    }

    @Override
    public boolean supportsEventType(ResolvableType resolvableType) {
        return ResolvableType.forClass(DownLoadedEngineEvent.class).isAssignableFrom(resolvableType);
    }

    @Override
    public boolean supportsSourceType(Class<?> sourceType) {
        System.out.println("##### 类名:" + sourceType + "#####");
        return Stream.of(CrawlingResponse.class).anyMatch(clz -> clz.isAssignableFrom(sourceType));
    }


}
