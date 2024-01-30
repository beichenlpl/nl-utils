package com.github.beichenlpl.net.socket;

import java.io.IOException;

/**
 * @author lpl
 * @version 1.0
 * @since 2024.01.24
 */
public interface UDPClientHandler {

    void sendInfo(String sendInfo);

    byte[] handle() throws IOException;
}
