package io.zpz.tool.downloader;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import okhttp3.Response;

import java.util.Optional;

@SuperBuilder
@Getter
public class HttpClientResponse extends FetchResponse<Response> {

    @Override
    public String getSpiderKey() {
        return super.getSpiderKey();
    }

    @Override
    public String getOriginResponseString() {
        return this.getOriginResponse() == null ? "" : Optional.ofNullable(this.getOriginResponse().body())
                .map(responseBody -> {
                    try {
                        String result = responseBody.string();
                        responseBody.close();
                        return result;
                    } catch (Exception e) {
                        return "";
                    }
                }).orElse("");
    }
}
