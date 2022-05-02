package io.zpz.tool.spider;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


@Getter
public class SpiderItemResult<T> {

    private Iterable<T> records = new ArrayList<>();

    private Set<String> newUrls = new HashSet<>();

    public SpiderItemResult(Iterable<T> records, Set<String> newUrls) {
        if (records == null) {
            throw new RuntimeException("records 不能为空");
        }
        if (newUrls == null) {
            throw new RuntimeException("newUrls 不能为空");
        }
        this.records = records;
        this.newUrls = newUrls;
    }

}
