package io.zpz.tool.crawling;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DefaultCrawlingRequest implements CrawlingRequest {

    private String url;

    private String spiderKey;

}
