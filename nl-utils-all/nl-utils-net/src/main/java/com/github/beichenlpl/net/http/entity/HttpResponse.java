package com.github.beichenlpl.net.http.entity;

import com.github.beichenlpl.net.http.enums.HttpResponseCode;

import java.util.List;
import java.util.Map;
/**
 * @author lpl
 * @version 1.0
 * @since 2024.01.25
 */
public class HttpResponse {
    private Map<String, List<String>> headers;
    private String body;
    private HttpResponseCode code;
    public HttpResponse withCode(HttpResponseCode code) {
        this.code = code;
        return this;
    }

    public HttpResponse withBody(String body) {
        this.body = body;
        return this;
    }

    public HttpResponse withHeaders(Map<String, List<String>> headers) {
        this.headers = headers;
        return this;
    }

    public HttpResponseCode code() {
        return code;
    }

    public String body() {
        return body;
    }

    public Map<String, List<String>> headers() {
        return headers;
    }
}

