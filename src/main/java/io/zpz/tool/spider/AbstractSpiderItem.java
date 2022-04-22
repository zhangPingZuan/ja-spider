package io.zpz.tool.spider;

import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.regex.Pattern;


@SuperBuilder
public abstract class AbstractSpiderItem<T> implements SpiderItem<T> {

    protected String regex = "";
    protected final Iterable<T> results = new ArrayList<>();

    @Override
    public boolean match(String url) {
        return Pattern.matches(this.regex, url);
    }

    @Override
    public Iterable<T> getResults(String content) {
        return results;
    }
}
