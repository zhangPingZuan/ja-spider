package io.zpz.tool;

import io.zpz.tool.downloader.DefaultDownloader;
import io.zpz.tool.engine.CentralEngine;
import io.zpz.tool.engine.DefaultCentralEngine;
import io.zpz.tool.engine.SimpleEngineEventMulticaster;
import io.zpz.tool.spider.DownLoadedEngineEventSpider;
import io.zpz.tool.spider.Spider;
import io.zpz.tool.windup.FinalProcessor;
import io.zpz.tool.windup.MysqlFinalProcessor;
import io.zpz.tool.windup.entity.DataRecord;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;

@Setter
@Getter
@Slf4j
@SpringBootApplication
public class MainSpider {
    public static void main(String[] args) {

        SpringApplication.run(MainSpider.class, args);
        CentralEngine centralEngine = DefaultCentralEngine.builder()
                .downloader(new DefaultDownloader())
                .engineEventMulticaster(new SimpleEngineEventMulticaster())
                .scheduler(null)
                .build();
        // 注册 spider
        Spider<?> spider = new DownLoadedEngineEventSpider();
        centralEngine.getEngineEventMulticaster().addEngineEventListener(spider);
        centralEngine.start();

        FinalProcessor<DataRecord> finalProcessor = new MysqlFinalProcessor();
        finalProcessor.process(new ArrayList<>());

        centralEngine.stop();
    }

}
