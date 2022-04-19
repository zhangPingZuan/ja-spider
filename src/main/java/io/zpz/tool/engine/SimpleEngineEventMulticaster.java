package io.zpz.tool.engine;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.Set;

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

        if (CollectionUtils.isEmpty(listeners)) {
            return;
        }

        this.listeners.forEach(listener -> {
            log.info("##### 广播一下 #####");
            // 得先过滤下对应类型的listener

            // to filter

            // 最后才调用监听器
            doInvokeListener(listener, engineEvent);
        });

    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private void doInvokeListener(EngineEventListener engineEventListener, EngineEvent engineEvent) {
        engineEventListener.onEngineEvent(engineEvent);
    }

}
