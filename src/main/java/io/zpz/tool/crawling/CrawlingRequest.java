package io.zpz.tool.crawling;

public interface CrawlingRequest {

    String getUrl();

    /**
     * spider-key
     */
    String getSpiderKey();

}
