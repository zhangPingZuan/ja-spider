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

import java.util.*;
import java.util.regex.Pattern;

@SuperBuilder
@Slf4j
public class FreeReadChapterSpiderItem extends AbstractSpiderItem<DataRecord> {


    @Override
    public boolean match(String url) {
        return Pattern.matches("https://www.shuquge.com/txt/\\w+/\\d+.html", url);
    }

    @Override
    public SpiderItemResult<DataRecord> getResults(String content, String originUrl) {
        List<DataRecord> dataRecordList = new ArrayList<>();
        Set<String> urls = new HashSet<>();

        // 用jsoup解析
        Document document = Jsoup.parse(content);

        DataRecord dataRecord = new DataRecord();
        dataRecord.setUrl(originUrl);
        dataRecord.setDescription("这是一个章节页面");
        Map<String, Object> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        map.put("chapterName", document.select("#wrapper > div.book.reader > div.content > h1").first().text());
        map.put("chapterContent", document.select("#content").first().text());
        try {
            dataRecord.setContent(mapper.writeValueAsString(map));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            dataRecord.setContent("序列化异常");
        }
        dataRecordList.add(dataRecord);
        return new SpiderItemResult<>(dataRecordList, urls);
    }
}
