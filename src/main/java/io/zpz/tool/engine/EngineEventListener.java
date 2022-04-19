package io.zpz.tool.engine;

import java.util.EventListener;

public interface EngineEventListener<E extends EngineEvent> extends EventListener {

    void onEngineEvent(E event);

}
