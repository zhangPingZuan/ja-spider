package io.zpz.tool.spider.shuquge;

import io.zpz.tool.spider.AbstractSpiderItem;
import io.zpz.tool.spider.SpiderItemResult;
import io.zpz.tool.windup.entity.DataRecord;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.*;
import java.util.regex.Pattern;


@SuperBuilder
@Slf4j
public class FreeReadBookSpiderItem extends AbstractSpiderItem<DataRecord> {

    @Override
    public boolean match(String url) {
        if (Pattern.matches("https://www.shuquge.com/txt/\\w+.html", url)) {
            log.info("#### 这是小说的书籍页面:{}，会解析出章节数据。", url);
        }
        return Pattern.matches("https://www.shuquge.com/txt/\\w+.html", url);
    }

    @Override
    public SpiderItemResult getResults(String content, String originUrl) {

        List<DataRecord> dataRecordList = new ArrayList<>();
        Set<String> urls = new HashSet<>();

        // 用jsoup解析
        Document document = Jsoup.parse(content);

        // 书本信息
        Map<String, String> bookInfo = new HashMap<>();
        Elements elements = document.select("body > div.book > div.info > div.small > span");
        for (Element element : elements) {
            if (element.text().contains("作者")) {
                bookInfo.put("authorName", element.text());
            }
            if (element.text().contains("分类")) {
                bookInfo.put("category", element.text());
            }
            if (element.text().contains("状态")) {
                bookInfo.put("bookStatus", element.text());
            }
            if (element.text().contains("字数")) {
                bookInfo.put("wordNumber", element.text());
            }
        }
        // 章节信息
//        StringUtils.remove(originUrl, ".html") + "/"
        Elements chapters = document.select("body > div.book > div.info > div.small > span");


        DataRecord dataRecord = new DataRecord();
        Map<String, Object> map = new HashMap<>();
        map.put("bookInfo", bookInfo);
        dataRecord.setUrl(originUrl);
        dataRecord.setContent(map);
        dataRecord.setDescription("这是一个书籍详情数据");
        dataRecordList.add(dataRecord);
        return new SpiderItemResult(dataRecordList, urls);
    }
}
