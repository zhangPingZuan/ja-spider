package io.zpz.tool.task;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.zpz.tool.crawling.CrawlingRequest;
import io.zpz.tool.crawling.DefaultCrawlingRequest;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

// GC什么时候去回收？？我java进程退出了，不去回收对象的吗？？？？
@Slf4j
public class RedisTaskManager extends AbstractTaskManager {

    private final Jedis jedis;
    private final String CRAWLED_URL = "CRAWLED_URL::";
    private final String CRAWLED_SPIDER = "CRAWLED_SPIDER::";
    private final String CRAWLING_REQUEST = "#### CRAWLING_REQUEST ####";
    private final ObjectMapper objectMapper = new ObjectMapper();

    public RedisTaskManager(String redisUrl, int port, String password) {
        this.jedis = new Jedis(redisUrl, port);
        if (password != null) {
            jedis.auth(password);
        }
    }

    @Override
    protected void finalize() throws Throwable {
        jedis.close();
        log.info("我把redis给关了");
        super.finalize();
    }

    public static void main(String[] args) {
        RedisTaskManager redisTaskManager = new RedisTaskManager("localhost", 6388, "zpz961216A");
        redisTaskManager.jedis.set("asdasdasd", "asdasd");

        log.info("test:{}", redisTaskManager.jedis.get("SAdasdasdasd"));
    }

    @Override
    public void addCrawlingRequest(CrawlingRequest crawlingRequest) {
        if (jedis.get(CRAWLED_URL + crawlingRequest.getUrl()) == null) {
            jedis.set(CRAWLED_URL + crawlingRequest.getUrl(), CRAWLED_SPIDER + crawlingRequest.getUrl());
            try {
                jedis.lpush(CRAWLING_REQUEST, objectMapper.writeValueAsString(crawlingRequest));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
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
        String value = jedis.rpop(CRAWLING_REQUEST);
        if (value == null) return null;
        try {
            return objectMapper.readValue(value, DefaultCrawlingRequest.class);
        } catch (JsonProcessingException e) {
            log.error("RedisTaskManager.pollCrawlingRequest deserialize error !!!");
        }
        return null;
    }

    @Override
    public List<CrawlingRequest> pollCrawlingRequests(Integer size) {
        List<String> values = jedis.rpop(CRAWLING_REQUEST, size);
        if (values == null || values.size() == 0) return new ArrayList<>();
        return values.stream().map(value -> {
            try {
                return objectMapper.readValue(value, DefaultCrawlingRequest.class);
            } catch (JsonProcessingException e) {
                log.error("RedisTaskManager.pollCrawlingRequests deserialize error !!!");
            }
            return null;
        }).filter(Objects::nonNull).collect(toList());
    }

}
