package io.zpz.tool.windup;

import io.zpz.tool.util.SpringUtility;
import io.zpz.tool.windup.entity.DataRecord;
import io.zpz.tool.windup.repository.DataRecordRepository;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class MysqlFinalProcessor implements FinalProcessor {

    private final DataRecordRepository dataRecordRepository = SpringUtility.getBean(DataRecordRepository.class);

    @Override
    public <T> void process(Iterable<T> records) {
        List<DataRecord> dataRecords = new ArrayList<>();
        records.forEach(record -> {
            if (record instanceof DataRecord)
                dataRecords.add((DataRecord) record);
            // 如果是其他类。
        });
        dataRecordRepository.saveAll(dataRecords);
    }

    public static void main(String[] args) {
        MysqlFinalProcessor dataRecordMysqlFinalProcessor = new MysqlFinalProcessor();
        List<DataRecord> dataRecordList = new ArrayList<>();
        dataRecordMysqlFinalProcessor.process(dataRecordList);
    }

}
