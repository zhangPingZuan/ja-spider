package io.zpz.tool.downloader;

import io.zpz.tool.crawling.CrawlingResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder
public abstract class FetchResponse<T> implements CrawlingResponse<T> {

    private boolean success;

    private int code;

    private T originResponse;

    private String url;

    private String spiderName;

    public String getOriginResponseString() {
        throw new UnsupportedOperationException("not allow to do!!");
    }

}
