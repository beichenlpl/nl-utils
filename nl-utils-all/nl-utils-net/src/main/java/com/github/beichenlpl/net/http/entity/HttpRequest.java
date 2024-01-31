package com.github.beichenlpl.net.http.entity;

import com.github.beichenlpl.net.http.enums.HttpRequestMethod;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lpl
 * @version 1.0
 * @since 2024.01.25
 */
public class HttpRequest {
    private String url;
    private HttpRequestMethod method;
    private Map<String, String> headers = new HashMap<>();
    private Map<String, String> params = new HashMap<>();
    private String body;
    private Map<String, File> files = new HashMap<>();
    private Map<String, String> multiParams = new HashMap<>();
    private int timeout = 30000;

    public HttpRequest withUrl(String url) {
        this.url = url;
        return this;
    }

    public HttpRequest withMethod(HttpRequestMethod method) {
        this.method = method;
        return this;
    }

    public HttpRequest withHeaders(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }

    public HttpRequest withParams(Map<String, String> params) {
        this.params = params;
        return this;
    }

    public HttpRequest withBody(String body) {
        this.body = body;
        return this;
    }

    public HttpRequest withFiles(Map<String, File> files) {
        this.files = files;
        return this;
    }

    public HttpRequest withMultiParams(Map<String, String> multiParams) {
        this.multiParams = multiParams;
        return this;
    }

    public HttpRequest withTimeout(int timeout) {
        this.timeout = timeout;
        return this;
    }

    public String url() {
        return url;
    }

    public HttpRequestMethod method() {
        return method;
    }

    public Map<String, String> headers() {
        return headers;
    }

    public Map<String, String> params() {
        return params;
    }

    public int timeout() {
        return timeout;
    }

    public String body() {
        return body;
    }

    public Map<String, File> files() {
        return files;
    }

    public Map<String, String> multiParams() {
        return multiParams;
    }
}
