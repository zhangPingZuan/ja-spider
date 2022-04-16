package io.zpz.tool;

import io.zpz.tool.windup.MysqlFinalProcessor;
import io.zpz.tool.windup.entity.DataRecord;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@Setter
@Getter
@Slf4j
@SpringBootApplication
public class MainSpider {
    public static void main(String[] args) {

        SpringApplication.run(MainSpider.class, args);
        MysqlFinalProcessor mysqlFinalProcessor = new MysqlFinalProcessor();
        mysqlFinalProcessor.process(Arrays.asList(DataRecord.builder()
                .url("asdsa")
                .content("{\"sdas\":\"sadsa\"}")
                .description("asdsa")
                .build()));
    }

}
