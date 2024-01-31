package com.github.beichenlpl.net.http;

import com.github.beichenlpl.net.http.entity.HttpRequest;
import com.github.beichenlpl.net.http.entity.HttpResponse;
import com.github.beichenlpl.net.http.enums.HttpRequestMethod;
import com.github.beichenlpl.net.http.utils.HttpUtils;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * @author lpl
 * @version 1.0
 * @since 2024.01.25
 */
public class HttpClient {

    private static final HttpClient HTTP_CLIENT = new HttpClient();

    private final HttpRequest request;

    private HttpClient(){
        request = new HttpRequest();
    }

    public static HttpClient build() {
        return HTTP_CLIENT;
    }

    public HttpClient url(String url) {
        request.withUrl(url);
        return this;
    }

    public HttpClient headers(Map<String, String> headers) {
        request.withHeaders(headers);
        return this;
    }

    public HttpClient addHeader(String key, String value) {
        request.headers().put(key, value);
        return this;
    }

    public HttpClient params(Map<String, String> params) {
        request.withParams(params);
        return this;
    }

    public HttpClient addParam(String key, String value) {
        request.params().put(key, value);
        return this;
    }

    public HttpClient body(String body) {
        request.withBody(body);
        return this;
    }

    public HttpClient withFiles(Map<String, File> files) {
        request.withFiles(files);
        return this;
    }

    public HttpClient addFile(String key, File file) {
        request.files().put(key, file);
        return this;
    }

    public HttpClient timeout(int timeout) {
        request.withTimeout(timeout);
        return this;
    }

    public HttpResponse GET() throws IOException {
        request.withMethod(HttpRequestMethod.GET);
        return HttpUtils.execute(request);
    }

    public HttpResponse POST() throws IOException {
        request.withMethod(HttpRequestMethod.POST);
        return HttpUtils.execute(request);
    }

    public HttpResponse PUT() throws IOException {
        request.withMethod(HttpRequestMethod.PUT);
        return HttpUtils.execute(request);
    }

    public HttpResponse DELETE() throws IOException {
        request.withMethod(HttpRequestMethod.DELETE);
        return HttpUtils.execute(request);
    }
}
