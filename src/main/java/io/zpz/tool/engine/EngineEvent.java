package io.zpz.tool.engine;

import java.util.EventObject;

/**
 * 参考了spring中的事件设计
 */
public abstract class EngineEvent extends EventObject {

    /**
     * use serialVersionUID from Spring 1.2 for interoperability.
     */
    private static final long serialVersionUID = 7099057708183571937L;

    /**
     * System time when the event happened.
     */
    private final long timestamp;

    public EngineEvent(Object source) {
        super(source);
        this.timestamp = System.currentTimeMillis();
    }

    public final long getTimestamp() {
        return this.timestamp;
    }
}
