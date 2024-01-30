package com.github.beichenlpl.net.socket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

/**
 * @author lpl
 * @version 1.0
 * @since 2024.01.24
 */
public class Client {

    private String host;

    private Integer port;

    private String connectInfo = "connect success!";

    private String sendInfo = "send success!";

    private Socket socket;

    private DatagramSocket datagramSocket;

    protected Client host(String host) {
        this.host = host;
        return this;
    }

    protected Client port(Integer port) {
        this.port = port;
        return this;
    }

    protected Client connectInfo(String connectInfo) {
        this.connectInfo = connectInfo;
        return this;
    }

    protected Client sendInfo(String sendInfo) {
        this.sendInfo = sendInfo;
        return this;
    }

    protected Client buildTCPClient() throws IOException {
        socket = new Socket(host, port);
        return this;
    }

    protected Client buildUDPClient() throws IOException {
        datagramSocket = new DatagramSocket(port);
        return this;
    }

    protected void runTCPClient(TCPClientHandler tcpClientHandler) throws IOException {
        tcpClientHandler.connectInfo(connectInfo);
        tcpClientHandler.handle(socket.getOutputStream(), socket.getInputStream());
        tcpClientHandler.sendInfo(sendInfo);
        socket.close();
        socket = null;
    }

    protected void runUDPClient(UDPClientHandler udpClientHandler) throws IOException {
        byte[] bytes = udpClientHandler.handle();
        DatagramPacket packet = new DatagramPacket(bytes, bytes.length, InetAddress.getByName(host), port);
        datagramSocket.send(packet);
        udpClientHandler.sendInfo(sendInfo);
        datagramSocket.close();
        datagramSocket = null;
    }
}
