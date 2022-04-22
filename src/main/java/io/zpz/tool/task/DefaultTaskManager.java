package io.zpz.tool.task;

import io.zpz.tool.crawling.CrawlingRequest;

import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

/**
 * 不搞数据库的一个taskManager
 */
public class DefaultTaskManager extends AbstractTaskManager {

    @Override
    public void addCrawlingRequest(CrawlingRequest crawlingRequest) {
        super.crawlingRequests.add(crawlingRequest);
    }

    @Override
    public void addAllCrawlingRequest(List<CrawlingRequest> crawlingRequests) {
        super.crawlingRequests.addAll(crawlingRequests);
    }

    @Override
    public CrawlingRequest pollCrawlingRequest() {
        return super.crawlingRequests.poll();
    }

    @Override
    public List<CrawlingRequest> pollCrawlingRequests(Integer size) {
        return IntStream
                .range(0, super.crawlingRequests.size() > size ? size : super.crawlingRequests.size())
                .mapToObj(i -> super.crawlingRequests.poll())
                .collect(toList());
    }


}
