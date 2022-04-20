package io.zpz.tool.task;

import io.zpz.tool.crawling.CrawlingRequest;

import java.util.List;

public interface TaskManager {

    void addCrawlingRequest(CrawlingRequest crawlingRequest);

    void addAllCrawlingRequest(List<CrawlingRequest> crawlingRequests);

    CrawlingRequest pollCrawlingRequest();

    List<CrawlingRequest> pollCrawlingRequests(Integer size);

}
