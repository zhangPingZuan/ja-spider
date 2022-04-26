package io.zpz.tool.windup;

import io.zpz.tool.windup.entity.DataRecord;

import java.util.ArrayList;
import java.util.List;

public class FIleFinalProcessor implements FinalProcessor {

    @Override
    public <T> void process(Iterable<T> records) {
        List<DataRecord> dataRecords = new ArrayList<>();
        records.forEach(record -> {
            if (record instanceof DataRecord)
                dataRecords.add((DataRecord) record);
            // 如果是其他类。
        });

        // todo 写到文件中

    }
}
