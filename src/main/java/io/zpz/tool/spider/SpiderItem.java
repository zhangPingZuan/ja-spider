package io.zpz.tool.spider;

public interface SpiderItem<T> {

    boolean match(String url);

    Iterable<T> getResults(String content);

}
