package io.zpz.tool;

import io.zpz.tool.engine.CentralEngine;
import io.zpz.tool.engine.DefaultCentralEngine;
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
//        MysqlFinalProcessor mysqlFinalProcessor = new MysqlFinalProcessor();
//        mysqlFinalProcessor.process(Arrays.asList(DataRecord.builder()
//                .url("asdsa")
//                .content("{\"sdas\":\"sadsa\"}")
//                .description("asdsa")
//                .build()));
        CentralEngine centralEngine = DefaultCentralEngine.builder()
                .build();
        centralEngine.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈");
        centralEngine.stop();

        try {
            Thread.sleep(10000);
            log.info("main线程结束了");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
