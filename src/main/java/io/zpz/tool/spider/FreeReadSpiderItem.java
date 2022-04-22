package io.zpz.tool.spider;


import io.zpz.tool.windup.entity.DataRecord;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;

@SuperBuilder
@Slf4j
public class FreeReadSpiderItem extends AbstractSpiderItem<DataRecord> {


    @Override
    public Iterable<DataRecord> getResults(String content) {
        log.info("###### FreeReadSpiderItem:{}", content);
        return new ArrayList<>();
    }

    @Override
    public boolean match(String url) {
        return super.match(url);
    }


}
