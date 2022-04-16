package io.zpz.tool.downloader;


import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Map;

@Setter
@Getter
@SuperBuilder
public abstract class FetchRequest {

    private String url;

    private Map<String, String> headers;

}
