package com.github.beichenlpl.net.socket;

import java.io.IOException;

/**
 * @author lpl
 * @version 1.0
 * @since 2024.01.24
 */
public interface UDPServerHandler {

    Integer bufferSize();

    void successInfo(String successInfo);

    void handle(byte[] data) throws IOException;
}
