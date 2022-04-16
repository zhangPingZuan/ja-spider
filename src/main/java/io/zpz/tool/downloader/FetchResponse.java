package io.zpz.tool.downloader;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder
public abstract class FetchResponse<T> {

    private boolean success;

    private int code;

    private T originResponse;

    public String getOriginResponseString() {
        throw new UnsupportedOperationException("not allow to do!!");
    }

}
