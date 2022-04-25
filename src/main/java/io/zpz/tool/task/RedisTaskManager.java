package io.zpz.tool.task;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.zpz.tool.crawling.CrawlingRequest;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

@Slf4j
public class RedisTaskManager extends AbstractTaskManager {

    private final Jedis jedis;
    private final String CRAWLED_URL = "CRAWLED_URL::";
    private final String CRAWLED_SPIDER = "CRAWLED_SPIDER::";
    private final String CRAWLING_REQUEST = "#### CRAWLING_REQUEST ####";
    private final ObjectMapper objectMapper = new ObjectMapper();

    public RedisTaskManager(String redisUrl, String password) {
        this.jedis = new Jedis(redisUrl);
        if (password != null) {
            jedis.auth(password);
        }
    }

    @Override
    protected void finalize() throws Throwable {
        jedis.close();
        super.finalize();
    }

    @Override
    public void addCrawlingRequest(CrawlingRequest crawlingRequest) {
        if ("nil".equals(jedis.get(CRAWLED_URL + crawlingRequest.getUrl()))) {
            jedis.set(CRAWLED_URL + crawlingRequest.getUrl(), CRAWLED_SPIDER + crawlingRequest.getUrl());
            try {
                jedis.lpush(CRAWLING_REQUEST, objectMapper.writeValueAsString(crawlingRequest));
            } catch (JsonProcessingException e) {
                log.error("RedisTaskManager.addCrawlingRequest serialize error !!!");
            }
        }
    }

    @Override
    public void addAllCrawlingRequest(List<CrawlingRequest> crawlingRequests) {
        crawlingRequests.forEach(this::addCrawlingRequest);
    }

    @Override
    public CrawlingRequest pollCrawlingRequest() {
        String value = jedis.lpop(CRAWLING_REQUEST);
        if (value == null) return null;
        try {
            return objectMapper.readValue(value, CrawlingRequest.class);
        } catch (JsonProcessingException e) {
            log.error("RedisTaskManager.pollCrawlingRequest deserialize error !!!");
        }
        return null;
    }

    @Override
    public List<CrawlingRequest> pollCrawlingRequests(Integer size) {
        List<String> values = jedis.lpop(CRAWLING_REQUEST, size);
        if (values == null || values.size() == 0) return new ArrayList<>();
        return values.stream().map(value -> {
            try {
                return objectMapper.readValue(value, CrawlingRequest.class);
            } catch (JsonProcessingException e) {
                log.error("RedisTaskManager.pollCrawlingRequests deserialize error !!!");
            }
            return null;
        }).filter(Objects::nonNull).collect(toList());
    }

}
