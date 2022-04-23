package io.zpz.tool.task;

import io.zpz.tool.crawling.CrawlingRequest;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

/**
 * 不搞数据库的一个taskManager
 */
public class DefaultTaskManager extends AbstractTaskManager {

    private Set<String> loadUrls = new HashSet<>();

    @Override
    public void addCrawlingRequest(CrawlingRequest crawlingRequest) {
        if (!loadUrls.contains(crawlingRequest.getUrl())) {
            super.crawlingRequests.add(crawlingRequest);
        }
    }

    @Override
    public void addAllCrawlingRequest(List<CrawlingRequest> crawlingRequests) {
        if (crawlingRequests == null || crawlingRequests.size() == 0) return;
        super.crawlingRequests.addAll(crawlingRequests.stream().filter(cr -> !loadUrls.contains(cr.getUrl()))
                .collect(Collectors.toSet()));
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
