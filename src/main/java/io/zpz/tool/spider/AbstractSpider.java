package io.zpz.tool.spider;

import io.zpz.tool.crawling.CrawlingResponse;
import io.zpz.tool.engine.EngineEvent;
import io.zpz.tool.engine.core.ResolvableType;
import io.zpz.tool.task.TaskManager;
import io.zpz.tool.windup.FinalProcessor;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public abstract class AbstractSpider<E extends EngineEvent> implements Spider<E> {

    protected final String name = UUID.randomUUID().toString();
    protected final Set<SpiderItem<?>> spiderItems = new HashSet<>();

    @Override
    public String getName() {
        return this.name;
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
    public FinalProcessor getFinalProcessor() {
        throw new UnsupportedOperationException("请在子类中实现");
    }

    @Override
    public void addSpiderItem(SpiderItem<?> spiderItem) {
        throw new UnsupportedOperationException("请在子类中实现");
    }

    @Override
    public void onEngineEvent(E event) {
        throw new UnsupportedOperationException("请在子类中实现");
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
