package io.zpz.tool.engine;

import io.zpz.tool.engine.core.ResolvableType;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class SimpleEngineEventMulticaster implements EngineEventMulticaster {

    private final Set<EngineEventListener<?>> listeners = new HashSet<>();

    @Override
    public void addEngineEventListener(EngineEventListener<?> engineEventListener) {
        listeners.add(engineEventListener);
    }

    @Override
    public void removeEngineEventListener(EngineEventListener<?> engineEventListener) {
        listeners.remove(engineEventListener);
    }

    @Override
    public void removeAllListeners() {
        listeners.clear();
    }

    @Override
    public void multicast(EngineEvent engineEvent) {

        if (listeners.size() == 0) {
            return;
        }

        // to filter
        ResolvableType type = resolveDefaultEventType(engineEvent);

        // 精简版过滤
        getApplicationListeners(engineEvent, type)
                .forEach(listener -> {
                    log.info("##### 广播一下 #####");
                    // 最后才调用监听器
                    doInvokeListener(listener, engineEvent);
                });

    }

    private ResolvableType resolveDefaultEventType(EngineEvent event) {
        return ResolvableType.forInstance(event);
    }

    protected Collection<EngineEventListener<?>> getApplicationListeners(
            EngineEvent event, ResolvableType eventType) {

        Object source = event.getSource();
        Class<?> sourceType = (source != null ? source.getClass() : null);
        List<EngineEventListener<?>> allListeners = new ArrayList<>();

        // Add programmatically registered listeners, including ones coming
        // from ApplicationListenerDetector (singleton beans and inner beans).
        for (EngineEventListener<?> listener : listeners) {
            if (supportsEvent(listener, eventType, sourceType)) {
                allListeners.add(listener);
            }
        }
        return allListeners;
    }

    protected boolean supportsEvent(EngineEventListener<?> listener, ResolvableType eventType, Class<?> sourceType) {

        return (listener.supportsEventType(eventType) && listener.supportsSourceType(sourceType));
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private void doInvokeListener(EngineEventListener engineEventListener, EngineEvent engineEvent) {
        engineEventListener.onEngineEvent(engineEvent);
    }


}
