package io.zpz.tool.windup;

import io.zpz.tool.util.SpringUtility;
import io.zpz.tool.windup.entity.DataRecord;
import io.zpz.tool.windup.repository.DataRecordRepository;
import lombok.extern.slf4j.Slf4j;

import java.util.stream.Collectors;


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
                handleService();
            }
        }));
        super.curThread.get().start();
    }

    private void handleService() {
        if (super.processorDataQueue.isEmpty()) {
            // 休息一下
            try {
                log.info("processorDataQueue是空的，我先睡一下1s！！！");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            dataRecordRepository.saveAll(super.processorDataQueue.stream().parallel().collect(Collectors.toList()));
        }

    }

    @Override
    public void stop() {
        super.curThread.get().interrupt();
    }
}
