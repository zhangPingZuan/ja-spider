package io.zpz.tool.windup.repository;

import io.zpz.tool.windup.AbstractFinalProcessor;

import java.io.File;

public class FileFinalProcessor extends AbstractFinalProcessor<File> {

    @Override
    public void addDataRecords(Iterable<File> dataRecords) {
        super.addDataRecords(dataRecords);
    }

    @Override
    public void addDataRecord(File dataRecord) {
        super.addDataRecord(dataRecord);
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void stop() {
        super.stop();
    }
}
