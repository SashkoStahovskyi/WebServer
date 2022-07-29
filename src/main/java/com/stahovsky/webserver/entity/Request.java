package com.stahovsky.webserver.entity;

import java.util.HashMap;
import java.util.Map;

public class Request {

    private final Map<String, String> headers = new HashMap<>();
    private String httpVersion;
    private HttpMethod method;
    private String uri;

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getUri() {
        return uri;
    }

    public void setHeaders(String key, String value) {
        this.headers.put(key, value);
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHttpMethod(HttpMethod method) {
        this.method = method;
    }

    public HttpMethod getHttpMethod() {
        return method;
    }

    public void setHttpVersion(String httpVersion) {
        this.httpVersion = httpVersion;
    }

    public String getHttpVersion() {
        return httpVersion;
    }
}


