package io.zpz.tool.spider;

import io.zpz.tool.engine.CentralEngine;
import io.zpz.tool.engine.DownLoadedEngineEvent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DownLoadedEngineEventSpider implements Spider<DownLoadedEngineEvent> {


    @Override
    public String getName() {
        return null;
    }

    @Override
    public void parse() {

    }

    @Override
    public void commitUrlToEngine(CentralEngine centralEngine) {

    }

    @Override
    public void onEngineEvent(DownLoadedEngineEvent event) {
        log.info("#### 接收到广播事件 ####");

        // 解析后，，发现还有url，，继续提交到 centralEngine


    }
}
