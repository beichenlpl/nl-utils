package com.github.baichenlpl.net.http.test;

import com.github.beichenlpl.net.http.HttpClient;
import com.github.beichenlpl.net.http.entity.HttpResponse;
import org.junit.Test;

/**
 * @author lpl
 * @version 1.0
 * @since 2024.01.30
 */
public class HttpClientTest {

    @Test
    public void test0() throws Exception {
        HttpResponse response = HttpClient.build().url("https://www.baidu.com/")
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/97.0.4692.71 Safari/537.36")
                .GET();
        System.out.println(response.code().code + " " + response.code().message);
        System.out.println(response.body());
    }
}
