package com.github.beichenlpl.net.http.utils;

import com.github.beichenlpl.net.http.entity.HttpRequest;
import com.github.beichenlpl.net.http.entity.HttpResponse;
import com.github.beichenlpl.net.http.enums.HttpResponseCode;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.util.Map;
import java.util.UUID;

/**
 * @author lpl
 * @version 1.0
 * @since 2024.01.30
 */
public class HttpUtils {
    public static HttpResponse execute(HttpRequest request) throws IOException {
        switch (request.method()) {
            case GET:
                return get(request.url(), request.headers(), request.params(), request.timeout());
            case POST:
                return request.files().isEmpty() ?
                        post(request.url(), request.headers(), request.params(), request.body(), request.timeout()) :
                        postFile(request.url(), request.headers(), request.params(), request.files(), request.multiParams(), request.timeout());
            case PUT:
                return request.files().isEmpty() ?
                        put(request.url(), request.headers(), request.params(), request.body(), request.timeout()) :
                        putFile(request.url(), request.headers(), request.params(), request.files(), request.multiParams(), request.timeout());
            case DELETE:
                return delete(request.url(), request.headers(), request.params(), request.timeout());
            default:
                return null;
        }
    }

    public static HttpResponse get(String url, Map<String, String> headers, Map<String, String> params, int timeout) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) buildReqUrl(url, params).openConnection();
        connection.setRequestMethod("GET");
        headers.forEach(connection::setRequestProperty);
        connection.setConnectTimeout(timeout);
        return buildHttpResponse(connection);
    }

    public static HttpResponse post(String url, Map<String, String> headers, Map<String, String> params, String body, int timeout) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) buildReqUrl(url, params).openConnection();
        connection.setRequestMethod("POST");
        headers.forEach(connection::setRequestProperty);
        connection.setConnectTimeout(timeout);
        connection.setDoOutput(true);

        byte[] bodyBytes = body.getBytes();
        try (OutputStream out = connection.getOutputStream()) {
            out.write(bodyBytes);
        }
        return buildHttpResponse(connection);
    }

    public static HttpResponse put(String url, Map<String, String> headers, Map<String, String> params, String body, int timeout) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) buildReqUrl(url, params).openConnection();
        connection.setRequestMethod("PUT");
        headers.forEach(connection::setRequestProperty);
        connection.setConnectTimeout(timeout);
        connection.setDoOutput(true);

        byte[] bodyBytes = body.getBytes();
        try (OutputStream out = connection.getOutputStream()) {
            out.write(bodyBytes);
        }
        return buildHttpResponse(connection);
    }

    public static HttpResponse postFile(String url, Map<String, String> headers, Map<String, String> params, Map<String, File> files, Map<String, String> multipartParams, int timeout) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) buildReqUrl(url, params).openConnection();
        connection.setRequestMethod("POST");
        headers.forEach(connection::setRequestProperty);
        connection.setConnectTimeout(timeout);
        connection.setDoOutput(true);
        buildMultipart(connection, files, multipartParams);
        return buildHttpResponse(connection);
    }

    public static HttpResponse putFile(String url, Map<String, String> headers, Map<String, String> params, Map<String, File> files, Map<String, String> multipartParams, int timeout) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) buildReqUrl(url, params).openConnection();
        connection.setRequestMethod("PUT");
        headers.forEach(connection::setRequestProperty);
        connection.setConnectTimeout(timeout);
        connection.setDoOutput(true);
        buildMultipart(connection, files, multipartParams);
        return buildHttpResponse(connection);
    }

    public static HttpResponse delete(String url, Map<String, String> headers, Map<String, String> params, int timeout) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) buildReqUrl(url, params).openConnection();
        connection.setRequestMethod("DELETE");
        headers.forEach(connection::setRequestProperty);
        connection.setConnectTimeout(timeout);
        return buildHttpResponse(connection);
    }

    private static HttpResponse buildHttpResponse(HttpURLConnection connection) throws IOException {
        connection.connect();

        int code = connection.getResponseCode();
        StringBuilder bodyBuilder = new StringBuilder();
        if (code == HttpResponseCode.OK.code) {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String line;
                while ((line = in.readLine()) != null) {
                    bodyBuilder.append(line);
                }
            }
        }
        HttpResponse httpResponse = new HttpResponse().withCode(HttpResponseCode.get(code))
                .withHeaders(connection.getHeaderFields())
                .withBody(bodyBuilder.toString());

        connection.disconnect();

        return httpResponse;
    }

    private static URL buildReqUrl(String url, Map<String, String> params) throws IOException {
        StringBuilder urlBuilder = new StringBuilder(url);
        if (params != null && !params.isEmpty()) {
            urlBuilder.append('?');
            for (Map.Entry<String, String> entry : params.entrySet()) {
                urlBuilder.append(entry.getKey()).append('=').append(entry.getValue()).append('&');
            }
            urlBuilder.deleteCharAt(urlBuilder.length() - 1);
        }

        return new URL(urlBuilder.toString());
    }

    private static void buildMultipart(HttpURLConnection connection, Map<String, File> files, Map<String, String> multipartParams) throws IOException {
        String boundary = "----" + UUID.randomUUID();
        connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
        try (OutputStream out = connection.getOutputStream()) {
            DataOutputStream dos = new DataOutputStream(out);
            for (Map.Entry<String, String> entry : multipartParams.entrySet()) {
                dos.writeBytes("--" + boundary + "\r\n");
                dos.writeBytes("Content-Disposition: form-data; name=\"" + entry.getKey() + "\"\r\n\r\n");
                dos.writeBytes(entry.getValue() + "\r\n");
            }

            for (Map.Entry<String, File> entry : files.entrySet()) {
                dos.writeBytes("--" + boundary + "\r\n");
                dos.writeBytes("Content-Disposition: form-data; name=\"" + entry.getKey() + "\"; filename=\"" + entry.getValue().getName() + "\"\r\n");
                dos.writeBytes("Content-Type: application/octet-stream\r\n\r\n");
                try (OutputStream fileOut = new BufferedOutputStream(connection.getOutputStream()); InputStream fileIn = Files.newInputStream(entry.getValue().toPath())) {
                    byte[] buffer = new byte[8192];
                    int bytesRead;
                    while ((bytesRead = fileIn.read(buffer)) != -1) {
                        fileOut.write(buffer, 0, bytesRead);
                    }
                }
                dos.writeBytes("\r\n");
            }
            dos.writeBytes("--" + boundary + "--\r\n");
            dos.flush();
        }
        connection.connect();
    }
}
