package io.zpz.tool;

import io.zpz.tool.crawling.CrawlingRequest;
import io.zpz.tool.downloader.DefaultDownloader;
import io.zpz.tool.engine.CentralEngine;
import io.zpz.tool.engine.DefaultCentralEngine;
import io.zpz.tool.engine.DownLoadedEngineEvent;
import io.zpz.tool.engine.SimpleEngineEventMulticaster;
import io.zpz.tool.spider.DownLoadedEngineEventSpider;
import io.zpz.tool.spider.Spider;
import io.zpz.tool.spider.shuquge.FreeReadBookSpiderItem;
import io.zpz.tool.spider.shuquge.FreeReadCategorySpiderItem;
import io.zpz.tool.spider.shuquge.FreeReadChapterSpiderItem;
import io.zpz.tool.spider.shuquge.FreeReadSpiderItem;
import io.zpz.tool.task.DefaultTaskManager;
import io.zpz.tool.task.RedisTaskManager;
import io.zpz.tool.task.TaskManager;
import io.zpz.tool.windup.FinalProcessor;
import io.zpz.tool.windup.MysqlFinalProcessor;
import io.zpz.tool.windup.entity.DataRecord;
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

        String url = "https://www.shuquge.com/";

        // 配置引擎
        TaskManager taskManager = new DefaultTaskManager();
        TaskManager redisTaskManager = new RedisTaskManager("localhost", 6388, "zpz961216A");
        CentralEngine centralEngine = DefaultCentralEngine.builder()
                .downloader(new DefaultDownloader())
                .engineEventMulticaster(new SimpleEngineEventMulticaster())
                .taskManager(redisTaskManager)
                .scheduler(null)
                .build();

        // 配置spider
        FinalProcessor<DataRecord> finalProcessor = new MysqlFinalProcessor();
        finalProcessor.start();
        Spider<DownLoadedEngineEvent, DataRecord> spider = DownLoadedEngineEventSpider.builder()
                .taskManager(redisTaskManager)
                .finalProcessor(finalProcessor)
                .build();
        spider.setSpiderKey("spider-1");
        spider.addSpiderItem(FreeReadSpiderItem.builder()
                .regex(url)
                .build());
        spider.addSpiderItem(FreeReadCategorySpiderItem.builder()
                .regex("https://www.shuquge.com/category/\\w+.html")
                .build());
        spider.addSpiderItem(FreeReadBookSpiderItem.builder()
                .regex("https://www.shuquge.com/txt/\\w+/index.html")
                .build());
        spider.addSpiderItem(FreeReadChapterSpiderItem.builder()
                .regex("https://www.shuquge.com/txt/\\w+/\\d+.html")
                .build());

        redisTaskManager.addCrawlingRequest(new CrawlingRequest() {
            @Override
            public String getUrl() {
                return url;
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
        finalProcessor.stop();
        // 关闭引擎
        centralEngine.stop();
    }

}
