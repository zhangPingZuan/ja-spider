package io.zpz.tool.spider;

import lombok.AllArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

@AllArgsConstructor
public class SpiderItem {

    private String regex;

    private Set<String> xpathes;

    public boolean match(String url) {
        return Pattern.matches(this.regex, url);
    }

    ;

    public static void main(String[] args) {
        SpiderItem spiderItem = new SpiderItem("https://www.shuquge.com/txt/\\w+/index.html", new HashSet<>());
        System.out.println(spiderItem.match("https://www.shuquge.com/txt/63542/index.html"));
    }

}
