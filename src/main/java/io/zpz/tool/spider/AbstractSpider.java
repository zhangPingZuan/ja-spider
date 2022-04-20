package io.zpz.tool.spider;

import io.zpz.tool.engine.EngineEvent;
import io.zpz.tool.engine.core.ResolvableType;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public abstract class AbstractSpider<E extends EngineEvent> implements Spider<E> {

    protected final String name = UUID.randomUUID().toString();
    protected final Map<String, Set<String>> xpathes = new HashMap<>();

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void parse() {
        throw new UnsupportedOperationException("请在子类中实现");
    }

    @Override
    public void addXpath(String url, String xpath) {
        throw new UnsupportedOperationException("请在子类中实现");
    }

    @Override
    public void addXpathCollection(String url, Set<String> xpaths) {
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
