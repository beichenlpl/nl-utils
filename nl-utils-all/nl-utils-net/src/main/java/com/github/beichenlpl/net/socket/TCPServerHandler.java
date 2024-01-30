package com.github.beichenlpl.net.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author lpl
 * @version 1.0
 * @since 2024.01.24
 */
public interface TCPServerHandler {

    void acceptInfo(String acceptInfo);

    void successInfo(String successInfo);

    void handle(InputStream in, OutputStream out) throws IOException;
}
