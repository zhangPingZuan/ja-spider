package io.zpz.tool.spider.shuquge;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class FreeReadCategorySpiderItem extends AbstractSpiderItem<DataRecord> {

    @Override
    public boolean match(String url) {
        if (Pattern.matches("https://www.shuquge.com/category/\\w+.html", url)) {
            log.info("#### 解析分类页面:{}", url);
        }
        return Pattern.matches("https://www.shuquge.com/category/\\w+.html", url);
    }

    @Override
    public SpiderItemResult<DataRecord> getResults(String content, String originUrl) {

        List<DataRecord> dataRecordList = new ArrayList<>();
        Set<String> urls = new HashSet<>();

        // 用jsoup解析
        Document document = Jsoup.parse(content);

        // book页面
        Elements books = document.select("body > div.wrap > div.up > div.l.bd > ul > li");
        String category = "";
        for (Element book : books) {
            String url = book.select("span.s2 > a").attr("href");
            if (url.startsWith("http")) {

                category = book.select("span.s1").first().text().substring(1, 3);
                // 添加数据记录
                DataRecord dataRecord = new DataRecord();
                dataRecord.setUrl(originUrl);
                Map<String, Object> map = new HashMap<>();
                map.put("book", book.select("span.s2 > a").text());
                map.put("author", book.select("span.s4").text());
                map.put("bookUrl", url);
                try {
                    dataRecord.setContent(new ObjectMapper().writeValueAsString(map));
                } catch (JsonProcessingException e) {
                    dataRecord.setContent("序列化异常");
                }
                dataRecord.setDescription("分类页面解析出来的一个书籍数据");
                dataRecordList.add(dataRecord);

                //添加新请求
                urls.add(url);
            }
        }
        // 下一页 body > div.wrap > div.up > div.l.bd > ul > div > a
        Elements pages = document.select("body > div.wrap > div.up > div.l.bd > ul > div > a");
        for (Element page : pages) {
            if (page.text().equals("下一页")) {
                String categoryUrl = "https://www.shuquge.com" + page.attr("href");
                DataRecord dataRecord = new DataRecord();
                dataRecord.setUrl(originUrl);
                Map<String, Object> map = new HashMap<>();
                map.put("category", category);
                map.put("categoryUrl", categoryUrl);
                try {
                    dataRecord.setContent(new ObjectMapper().writeValueAsString(map));
                } catch (JsonProcessingException e) {
                    dataRecord.setContent("序列化异常");
                }
                dataRecord.setDescription("这是一个分类页面");
                dataRecordList.add(dataRecord);
                urls.add(categoryUrl);
            }
        }

        return new SpiderItemResult<>(dataRecordList, urls);
    }
}
