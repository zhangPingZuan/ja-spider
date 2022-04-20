package io.zpz.tool.engine;

import io.zpz.tool.engine.core.ResolvableType;

import java.util.EventListener;

public interface EngineEventListener<E extends EngineEvent> extends EventListener {

    void onEngineEvent(E event);

    boolean supportsEventType(ResolvableType resolvableType);

    boolean supportsSourceType(Class<?> sourceType);

}
