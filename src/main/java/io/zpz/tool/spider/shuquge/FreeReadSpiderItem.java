package io.zpz.tool.spider.shuquge;


import io.zpz.tool.spider.AbstractSpiderItem;
import io.zpz.tool.windup.entity.DataRecord;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

@SuperBuilder
@Slf4j
public class FreeReadSpiderItem extends AbstractSpiderItem<DataRecord> {


    @Override
    public Iterable<DataRecord> getResults(String content) {

        List<DataRecord> dataRecordList = new ArrayList<>();
        Document document = Jsoup.parse(content);

        // 分类
        Elements links = document.select("body > div.nav > ul > li");
        for (Element link : links) {
//            System.out.println("\nlink : " + link.select("a").attr("href"));
//            System.out.println("text : " + link.text());
            DataRecord dataRecord = new DataRecord();
            dataRecord.setUrl(link.select("a").attr("href"));
            dataRecord.setContent(link.text());
            dataRecord.setDescription("这是一个分类页面");
            dataRecordList.add(dataRecord);
        }

        // 推荐书籍
        // 最近更新小说
        // 最新入库小说
        return new ArrayList<>();
    }

    @Override
    public boolean match(String url) {
        return super.match(url);
    }


}
