package io.zpz.tool.engine;

public class DownLoadedEngineEvent extends EngineEvent {

    private String spiderName;

    private String url;

    public DownLoadedEngineEvent(Object source) {
        super(source);
    }

    public DownLoadedEngineEvent(Object source, String spiderName, String url) {
        super(source);
        this.spiderName = spiderName;
        this.url = url;
    }

}
