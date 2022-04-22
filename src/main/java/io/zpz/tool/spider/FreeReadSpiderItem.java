package io.zpz.tool.spider;


import io.zpz.tool.windup.entity.DataRecord;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;

@SuperBuilder
public class FreeReadSpiderItem extends AbstractSpiderItem<DataRecord> {


    @Override
    public Iterable<DataRecord> getResults(String content) {

        return new ArrayList<>();
    }

    @Override
    public boolean match(String url) {
        return super.match(url);
    }


}
