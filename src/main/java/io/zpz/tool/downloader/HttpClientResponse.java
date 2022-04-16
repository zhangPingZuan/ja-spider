package io.zpz.tool.downloader;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import okhttp3.Response;

import java.util.Optional;

@SuperBuilder
@Getter
public class HttpClientResponse extends FetchResponse{

    @Override
    public String getOriginResponseString() {
        if (super.getOriginResponse() instanceof Response) {
            Response response = (Response) this.getOriginResponse();
            return Optional.ofNullable(response.body()).map(responseBody -> {
                try {
                    return responseBody.string();
                } catch (Exception e) {
                    return "";
                }
            }).orElse("");
        } else throw new RuntimeException("不是okhttp3.Response");
    }
}
