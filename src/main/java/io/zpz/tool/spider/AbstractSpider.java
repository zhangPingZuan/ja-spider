package io.zpz.tool.spider;

import io.zpz.tool.crawling.CrawlingResponse;
import io.zpz.tool.engine.EngineEvent;
import io.zpz.tool.engine.core.ResolvableType;
import io.zpz.tool.task.TaskManager;
import io.zpz.tool.util.StringUtils;
import io.zpz.tool.windup.FinalProcessor;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public abstract class AbstractSpider<E extends EngineEvent, R> implements Spider<E, R> {

    protected String spiderKey = "";
    protected final Set<SpiderItem<R>> spiderItems = new HashSet<>();

    @Override
    public void setSpiderKey(String spiderKey) {
        this.spiderKey = spiderKey;
    }

    @Override
    public String getSpiderKey() {
        return StringUtils.isEmpty(this.spiderKey) ? UUID.randomUUID().toString() : this.spiderKey;
    }


    @Override
    public void parse(CrawlingResponse<?> crawlingResponse) {
        throw new UnsupportedOperationException("请在子类中实现");
    }

    @Override
    public TaskManager getTaskManager() {
        throw new UnsupportedOperationException("请在子类中实现");
    }

    @Override
    public FinalProcessor<R> getFinalProcessor() {
        throw new UnsupportedOperationException("请在子类中实现");
    }

    @Override
    public void addSpiderItem(SpiderItem<R> spiderItem) {
        spiderItems.add(spiderItem);
    }

    @Override
    public void onEngineEvent(E event) {

    }

    @Override
    public boolean supportsEventType(ResolvableType resolvableType) {
        return false;
    }

    @Override
    public boolean supportsSourceType(Class<?> sourceType) {
        return false;
    }
}
