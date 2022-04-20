package io.zpz.tool.spider;

import io.zpz.tool.engine.CentralEngine;
import io.zpz.tool.engine.EngineEvent;
import io.zpz.tool.engine.core.ResolvableType;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class AbstractSpider<E extends EngineEvent> implements Spider<E> {

    protected final String name = UUID.randomUUID().toString();
    protected final Set<String> xpathes = new HashSet<>();

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void parse() {

    }

    @Override
    public void commitUrlToEngine(CentralEngine centralEngine) {

    }

    @Override
    public void addXpath(String xpath) {

    }

    @Override
    public void addXpathCollection(Set<String> xpaths) {

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
