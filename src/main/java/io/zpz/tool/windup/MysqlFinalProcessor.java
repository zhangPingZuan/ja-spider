package io.zpz.tool.windup;

import io.zpz.tool.util.SpringUtility;
import io.zpz.tool.windup.entity.DataRecord;
import io.zpz.tool.windup.repository.DataRecordRepository;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;


@Slf4j
public class MysqlFinalProcessor extends AbstractFinalProcessor<DataRecord> {

    private final DataRecordRepository dataRecordRepository = SpringUtility.getBean(DataRecordRepository.class);

    @Override
    public void addDataRecords(Iterable<DataRecord> dataRecords) {
        dataRecords.forEach(super.processorDataQueue::add);
    }

    @Override
    public void addDataRecord(DataRecord dataRecord) {
        super.processorDataQueue.add(dataRecord);
    }

    @Override
    public void start() {
        super.curThread.set(new Thread(() -> {
            while (!curThread.get().isInterrupted()) {
                handleService(10000);
            }
        }));
        super.curThread.get().start();
    }

    private void handleService(Integer size) {
        if (super.processorDataQueue.isEmpty()) {
            // 休息一下
            try {
                log.info("processorDataQueue是空的，我先睡一下1s！！！");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            log.info("##### 开始存储数据 #####");
            List<DataRecord> dataRecordList = IntStream
                    .range(0, super.processorDataQueue.size() > size ? size : super.processorDataQueue.size())
                    .mapToObj(i -> super.processorDataQueue.poll())
                    .collect(toList());
            dataRecordRepository.saveAll(dataRecordList);
        }

    }

    @Override
    public void stop() {
        super.curThread.get().interrupt();
    }
}
