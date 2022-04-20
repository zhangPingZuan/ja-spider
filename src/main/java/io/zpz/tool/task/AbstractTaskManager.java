package io.zpz.tool.task;

import io.zpz.tool.crawling.CrawlingRequest;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

public abstract class AbstractTaskManager implements TaskManager {

    protected final Queue<CrawlingRequest> crawlingRequests = new ArrayDeque<>();


    @Override
    public void addCrawlingRequest(CrawlingRequest crawlingRequest) {
        throw new UnsupportedOperationException("通过实现类来操作");
    }

    @Override
    public void addAllCrawlingRequest(List<CrawlingRequest> crawlingRequests) {
        throw new UnsupportedOperationException("通过实现类来操作");
    }

    @Override
    public CrawlingRequest pollCrawlingRequest() {
        throw new UnsupportedOperationException("通过实现类来操作");
    }

    @Override
    public List<CrawlingRequest> pollCrawlingRequests(Integer size) {
        throw new UnsupportedOperationException("通过实现类来操作");
    }
}
