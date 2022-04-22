package io.zpz.tool.crawling;

public interface CrawlingResponse<T> {

    T getOriginResponse();

    String getOriginResponseString();

    String getUrl();

    String getSpiderKey();

}
