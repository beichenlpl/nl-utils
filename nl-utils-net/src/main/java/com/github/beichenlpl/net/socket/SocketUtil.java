package com.github.beichenlpl.net.socket;

import java.io.IOException;

/**
 * @author lpl
 * @version 1.0
 * @since 2024.01.24
 */
public class SocketUtil {

    public static void runTCPServer(TCPServerHandler tcpServerHandler, Integer port) throws IOException {
        Server server = new Server().port(port).buildTCPServer();
        server.runTCPServer(tcpServerHandler);
    }

    public static void runTCPServer(TCPServerHandler tcpServerHandler, Integer port, String acceptInfo, String successInfo) throws IOException {
        Server server = new Server().port(port).acceptInfo(acceptInfo).successInfo(successInfo).buildTCPServer();
        server.runTCPServer(tcpServerHandler);
    }

    public static void runUDPServer(UDPServerHandler udpServerHandler, Integer port) throws IOException {
        Server server = new Server().port(port).buildUDPServer();
        server.runUDPServer(udpServerHandler);
    }

    public static void runUDPServer(UDPServerHandler udpServerHandler, Integer port, String successInfo) throws IOException {
        Server server = new Server().port(port).successInfo(successInfo).buildUDPServer();
        server.runUDPServer(udpServerHandler);
    }

    public static void runTCPClient(TCPClientHandler tcpClientHandler, String host, Integer port) throws IOException {
        Client client = new Client().host(host).port(port).buildTCPClient();
        client.runTCPClient(tcpClientHandler);
    }

    public static void runTCPClient(TCPClientHandler tcpClientHandler, String host, Integer port, String connectInfo, String sendInfo) throws IOException {
        Client client = new Client().host(host).port(port).connectInfo(connectInfo).sendInfo(sendInfo).buildTCPClient();
        client.runTCPClient(tcpClientHandler);
    }

    public static void runUDPClient(UDPClientHandler udpClientHandler, String host, Integer port) throws IOException {
        Client client = new Client().host(host).port(port).buildUDPClient();
        client.runUDPClient(udpClientHandler);
    }

    public static void runUDPClient(UDPClientHandler udpClientHandler, String host, Integer port, String sendInfo) throws IOException {
        Client client = new Client().host(host).port(port).sendInfo(sendInfo).buildUDPClient();
        client.runUDPClient(udpClientHandler);
    }
}
