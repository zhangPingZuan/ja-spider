package io.zpz.tool.task;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.zpz.tool.crawling.CrawlingRequest;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

// GC什么时候去回收？？我java进程退出了，不去回收对象的吗？？？？
@Slf4j
public class RedisTaskManager extends AbstractTaskManager {

    private final JedisPool jedisPool;
    private final String CRAWLED_URL = "CRAWLED_URL::";
    private final String CRAWLED_SPIDER = "CRAWLED_SPIDER::";
    private final String CRAWLING_REQUEST = "#### CRAWLING_REQUEST ####";
    private final ObjectMapper objectMapper = new ObjectMapper();

    public RedisTaskManager(String redisUrl, int port, String password) {
        this.jedisPool = new JedisPool(new JedisPoolConfig(), redisUrl, port, Protocol.DEFAULT_TIMEOUT, password);
    }

    @Override
    protected void finalize() throws Throwable {
        jedisPool.close();
        log.info("我把redis给关了");
        super.finalize();
    }

    @Override
    public void addCrawlingRequest(CrawlingRequest crawlingRequest) {
        Jedis jedis = jedisPool.getResource();
        if (jedis.get(CRAWLED_URL + crawlingRequest.getUrl()) == null) {
            jedis.set(CRAWLED_URL + crawlingRequest.getUrl(), CRAWLED_SPIDER + crawlingRequest.getUrl());
            try {
                jedis.lpush(CRAWLING_REQUEST, objectMapper.writeValueAsString(crawlingRequest));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                log.error("RedisTaskManager.addCrawlingRequest serialize error !!!");
            }
        }
        jedis.close();
    }

    @Override
    public void addAllCrawlingRequest(List<CrawlingRequest> crawlingRequests) {
        crawlingRequests.forEach(this::addCrawlingRequest);
    }

    @Override
    public CrawlingRequest pollCrawlingRequest() {
        Jedis jedis = jedisPool.getResource();
        String value = jedis.rpop(CRAWLING_REQUEST);
        jedis.close();
        if (value == null) return null;
        try {
            final JsonNode jsonRoot = objectMapper.readTree(value);
            return new CrawlingRequest() {
                @Override
                public String getUrl() {
                    return jsonRoot.get("url").asText();
                }

                @Override
                public String getSpiderKey() {
                    return jsonRoot.get("spiderKey").asText();
                }
            };
        } catch (JsonProcessingException e) {
            log.error("RedisTaskManager.pollCrawlingRequest deserialize error !!!");
        }
        return null;
    }

    @Override
    public List<CrawlingRequest> pollCrawlingRequests(Integer size) {
        Jedis jedis = jedisPool.getResource();
        List<String> values = jedis.rpop(CRAWLING_REQUEST, size);
        jedis.close();
        if (values == null || values.size() == 0) return new ArrayList<>();
        return values.stream().map(value -> {
            try {
                final JsonNode jsonRoot = objectMapper.readTree(value);
                return new CrawlingRequest() {
                    @Override
                    public String getUrl() {
                        return jsonRoot.get("url").asText();
                    }

                    @Override
                    public String getSpiderKey() {
                        return jsonRoot.get("spiderKey").asText();
                    }
                };
            } catch (JsonProcessingException e) {
                log.error("RedisTaskManager.pollCrawlingRequests deserialize error !!!");
            }
            return null;
        }).filter(Objects::nonNull).collect(toList());
    }

}
