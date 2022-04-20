package io.zpz.tool.spider;

import io.zpz.tool.downloader.FetchResponse;
import io.zpz.tool.engine.DownLoadedEngineEvent;
import io.zpz.tool.engine.core.ResolvableType;
import io.zpz.tool.task.TaskManager;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;

@Slf4j
public class DownLoadedEngineEventSpider extends AbstractSpider<DownLoadedEngineEvent> {

    private TaskManager taskManager;

    @Override
    public void addXpath(String url, String xpath) {
        if (super.xpathes.containsKey(url)) {
            if (super.xpathes.get(url) != null) {
                super.xpathes.get(url).add(xpath);
            } else {
                super.xpathes.put(url, new HashSet<>(Collections.singletonList(xpath)));
            }
        } else {
            super.xpathes.put(url, new HashSet<>(Collections.singletonList(xpath)));
        }
    }

    @Override
    public void addXpathCollection(String url, Set<String> xpaths) {
        if (super.xpathes.containsKey(url)) {
            if (super.xpathes.get(url) != null) {
                super.xpathes.get(url).addAll(xpaths);
            } else {
                super.xpathes.put(url, xpaths);
            }
        } else {
            super.xpathes.put(url, xpaths);
        }
    }

    @Override
    public String getName() {
        return UUID.randomUUID().toString();
    }

    @Override
    public void parse() {
//        super.xpathes.forEach(xpath -> {
//            // 进行过滤
//
//            // 得出CrawlingRequest
//            taskManager.addCrawlingRequest(null);
//
//            // 得出processor request
//        });
    }

    private void handleService(String url, String content) {
        super.xpathes.get(url).forEach(xpath -> {

            // 使用jsoup进行解析。


        });
    }

    @Override
    public void onEngineEvent(DownLoadedEngineEvent event) {
        log.info("#### 接收到广播事件 ####");

        // 解析后，，发现还有url，，继续提交到 centralEngine
        // 真的要继续提交吗？？？那是不是应该是外面控制层的事情了？？？
        // 这边只要把结果保存下
        if (event.getSource() instanceof FetchResponse<?>) {
            FetchResponse<?> fetchResponse = (FetchResponse<?>) event.getSource();

            // 发现不是这个蜘蛛处理的就直接返回
            if (!fetchResponse.getSpiderName().equals(this.name)) return;

            handleService(fetchResponse.getUrl(), fetchResponse.getOriginResponseString());
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
