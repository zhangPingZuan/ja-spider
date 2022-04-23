package io.zpz.tool.spider;

import lombok.experimental.SuperBuilder;

import java.util.regex.Pattern;


@SuperBuilder
public abstract class AbstractSpiderItem<T> implements SpiderItem<T> {

    protected String regex = "";

    @Override
    public boolean match(String url) {
        return Pattern.matches(this.regex, url);
    }

    @Override
    public SpiderItemResult getResults(String content) {
        throw new UnsupportedOperationException("请调用子类");
    }
}
