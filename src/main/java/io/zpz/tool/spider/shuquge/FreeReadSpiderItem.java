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

@SuperBuilder
@Slf4j
public class FreeReadSpiderItem extends AbstractSpiderItem<DataRecord> {


    @Override
    public SpiderItemResult getResults(String content) {

        List<DataRecord> dataRecordList = new ArrayList<>();
        Set<String> urls = new HashSet<>();

        // 用jsoup解析
        Document document = Jsoup.parse(content);

        // 分类
        Elements links = document.select("body > div.nav > ul > li");
        for (Element link : links) {
//            System.out.println("\nlink : " + link.select("a").attr("href"));
//            System.out.println("text : " + link.text());
            String url = link.select("a").attr("href");

            if (url.startsWith("http")) {

                // 添加数据记录
                DataRecord dataRecord = new DataRecord();
                dataRecord.setUrl(url);
                Map<String, String> map = new HashMap<>();
                map.put("category", link.text());
                dataRecord.setContent(map);
                dataRecord.setDescription("这是一个分类页面");
                dataRecordList.add(dataRecord);

                // 添加新请求
                urls.add(url);
            }
        }

        // 推荐书籍
        // 最近更新小说
        // 最新入库小说
        return new SpiderItemResult(dataRecordList, urls);
    }

    @Override
    public boolean match(String url) {
        return super.match(url);
    }


}
