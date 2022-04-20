package io.zpz.tool.spider;

import io.zpz.tool.engine.CentralEngine;
import io.zpz.tool.engine.DownLoadedEngineEvent;
import io.zpz.tool.engine.core.ResolvableType;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;

@Slf4j
public class DownLoadedEngineEventSpider extends AbstractSpider<DownLoadedEngineEvent> {

    @Override
    public void addXpath(String xpath) {
        super.xpathes.add(xpath);
    }

    @Override
    public void addXpathCollection(Set<String> xpaths) {
        super.xpathes.addAll(xpaths);
    }

    @Override
    public String getName() {
        return UUID.randomUUID().toString();
    }

    @Override
    public void parse() {
        super.xpathes.forEach(xpath -> {
            // 进行过滤

            // 得出CrawlingRequest

            // 得出processor request
        });
    }

    @Override
    public void commitUrlToEngine(CentralEngine centralEngine) {

    }

    @Override
    public void onEngineEvent(DownLoadedEngineEvent event) {
        log.info("#### 接收到广播事件 ####");

        // 解析后，，发现还有url，，继续提交到 centralEngine


    }

    @Override
    public boolean supportsEventType(ResolvableType resolvableType) {
        return ResolvableType.forClass(DownLoadedEngineEvent.class).isAssignableFrom(resolvableType);
    }

    @Override
    public boolean supportsSourceType(Class<?> sourceType) {
        System.out.println("##### 类名:" + sourceType + "#####");
        return Stream.of(Response.class, Object.class).anyMatch(clz -> clz.isAssignableFrom(sourceType));
    }


}
