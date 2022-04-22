package io.zpz.tool;

import io.zpz.tool.crawling.CrawlingRequest;
import io.zpz.tool.downloader.DefaultDownloader;
import io.zpz.tool.engine.CentralEngine;
import io.zpz.tool.engine.DefaultCentralEngine;
import io.zpz.tool.engine.SimpleEngineEventMulticaster;
import io.zpz.tool.spider.DownLoadedEngineEventSpider;
import io.zpz.tool.spider.FreeReadSpiderItem;
import io.zpz.tool.spider.Spider;
import io.zpz.tool.task.DefaultTaskManager;
import io.zpz.tool.task.TaskManager;
import io.zpz.tool.windup.FinalProcessor;
import io.zpz.tool.windup.MysqlFinalProcessor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Setter
@Getter
@Slf4j
@SpringBootApplication
public class MainSpider {
    public static void main(String[] args) {

        SpringApplication.run(MainSpider.class, args);

        // 配置引擎
        TaskManager taskManager = new DefaultTaskManager();
        CentralEngine centralEngine = DefaultCentralEngine.builder()
                .downloader(new DefaultDownloader())
                .engineEventMulticaster(new SimpleEngineEventMulticaster())
                .taskManager(taskManager)
                .scheduler(null)
                .build();

        // 配置spider
        FinalProcessor finalProcessor = new MysqlFinalProcessor();
        Spider<?> spider = DownLoadedEngineEventSpider.builder()
                .taskManager(taskManager)
                .finalProcessor(finalProcessor)
                .build();
        spider.addSpiderItem(FreeReadSpiderItem.builder()
                .regex("https://www.shuquge.com/")
                .build());

        taskManager.addCrawlingRequest(new CrawlingRequest() {
            @Override
            public String getUrl() {
                return "https://www.shuquge.com/";
            }

            @Override
            public String getSpiderKey() {
                return spider.getSpiderKey();
            }
        });

        // 注册spider
        centralEngine.getEngineEventMulticaster().addEngineEventListener(spider);

        // 启动引擎
        centralEngine.start();
        try {
            Thread.sleep(100000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 关闭引擎
        centralEngine.stop();
    }

}
