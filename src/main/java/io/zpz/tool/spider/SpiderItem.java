package io.zpz.tool.spider;

public interface SpiderItem<T> {

    boolean match(String url);

    SpiderItemResult<T> getResults(String content, String originUrl);
}
