package io.zpz.tool.spider.shuquge;

import io.zpz.tool.spider.AbstractSpiderItem;
import io.zpz.tool.spider.SpiderItemResult;
import io.zpz.tool.windup.entity.DataRecord;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;


@SuperBuilder
@Slf4j
public class FreeReadBookSpiderItem extends AbstractSpiderItem<DataRecord> {

    @Override
    public boolean match(String url) {
        return super.match(url);
    }

    @Override
    public SpiderItemResult getResults(String content) {
        return super.getResults(content);
    }
}
