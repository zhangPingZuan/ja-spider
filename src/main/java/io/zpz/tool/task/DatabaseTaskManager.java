package io.zpz.tool.task;

import io.zpz.tool.crawling.CrawlingRequest;

import java.util.ArrayList;
import java.util.List;

public class DatabaseTaskManager extends AbstractTaskManager {


    @Override
    public void addCrawlingRequest(CrawlingRequest crawlingRequest) {
        // do nothing
    }

    @Override
    public void addAllCrawlingRequest(List<CrawlingRequest> crawlingRequests) {
        // do nothing
    }

    @Override
    public CrawlingRequest pollCrawlingRequest() {
        // 直接从数据库里面拉信息
        // todo
        return null;
    }

    @Override
    public List<CrawlingRequest> pollCrawlingRequests(Integer size) {
        // 直接从数据库里面拉信息
        // todo
        return new ArrayList<>();
    }
}
