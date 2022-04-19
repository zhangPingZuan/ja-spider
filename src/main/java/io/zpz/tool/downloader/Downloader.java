package io.zpz.tool.downloader;

public interface Downloader {

    /**
     * 这里必须写 FetchResponse<?>，你不知道FetchResponse里面到底是什么东西
     */
    FetchResponse<?> fetch(FetchRequest fetchRequest);

}
