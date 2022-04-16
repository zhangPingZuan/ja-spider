package io.zpz.tool.downloader;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.httpclient.HttpStatus;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Getter
public class DefaultDownloader implements Downloader {

    private final OkHttpClient okHttpClient = new OkHttpClient().newBuilder().build();

    public FetchResponse fetch(FetchRequest fetchRequest) {
        Request.Builder builder = new Request.Builder();
        fetchRequest.getHeaders().forEach(builder::addHeader);
        builder.url(fetchRequest.getUrl());
        try {
            Response response = okHttpClient.newCall(builder.build()).execute();
            return HttpClientResponse.builder()
                    .code(response.code())
                    .success(response.isSuccessful())
                    .originResponse(response)
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return HttpClientResponse.builder()
                .code(HttpStatus.SC_BAD_REQUEST)
                .success(Boolean.FALSE)
                .originResponse(Optional.empty())
                .build();
    }


    public static void main(String[] args) {
        Map<String, String> headers = new HashMap<>();
        Request request = new Request.Builder()
                .url("https://www.mianfeixiaoshuoyueduwang.com/")
                .method("GET", null)
                .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
                .addHeader("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8")
                .addHeader("Connection", "keep-alive")
                .addHeader("Cookie", "__gads=ID=b9c336d14203189e-22163ccdf1d1004a:T=1649899169:RT=1649899169:S=ALNI_MY7Tn6LES4f2E4XtkShJhrA0gnsqA")
                .addHeader("Sec-Fetch-Dest", "document")
                .addHeader("Sec-Fetch-Mode", "navigate")
                .addHeader("Sec-Fetch-Site", "none")
                .addHeader("Sec-Fetch-User", "?1")
                .addHeader("Upgrade-Insecure-Requests", "1")
                .addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/100.0.4896.88 Safari/537.36")
                .addHeader("sec-ch-ua", "\" Not A;Brand\";v=\"99\", \"Chromium\";v=\"100\", \"Google Chrome\";v=\"100\"")
                .addHeader("sec-ch-ua-mobile", "?0")
                .addHeader("sec-ch-ua-platform", "\"macOS\"")
                .build();
//        try {
//            Response response = new OkHttpClient().newBuilder().build().newCall(request).execute();
//            log.info(response.body().string());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        request.headers().forEach(header -> headers.put(header.getFirst(), header.getSecond()));
        FetchResponse response = new DefaultDownloader().fetch(HttpClientRequest.builder()
                .url("https://www.mianfeixiaoshuoyueduwang.com/")
                .headers(headers)
                .build());
        log.info("result:{}", response.getOriginResponseString());
    }

}
