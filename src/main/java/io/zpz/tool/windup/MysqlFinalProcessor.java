package io.zpz.tool.windup;

import io.zpz.tool.util.SpringUtility;
import io.zpz.tool.windup.entity.DataRecord;
import io.zpz.tool.windup.repository.DataRecordRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MysqlFinalProcessor implements FinalProcessor<DataRecord> {

    private final DataRecordRepository dataRecordRepository = SpringUtility.getBean(DataRecordRepository.class);

    @Override
    public void process(Iterable<DataRecord> records) {
        dataRecordRepository.saveAll(records);
    }

}
