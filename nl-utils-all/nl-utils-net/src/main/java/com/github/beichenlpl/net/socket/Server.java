package com.github.beichenlpl.net.socket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author lpl
 * @version 1.0
 * @since 2024.01.24
 */
public class Server {

    private Integer port;

    private String acceptInfo = "accept";

    private String successInfo = "success";

    private Boolean tcpIsStop = false;

    private Boolean udpIsStop = false;

    private ServerSocket serverSocket;

    private DatagramSocket datagramSocket;

    protected Server port(Integer port) {
        this.port = port;
        return this;
    }

    protected Server acceptInfo(String acceptInfo) {
        this.acceptInfo = acceptInfo;
        return this;
    }

    protected Server successInfo(String successInfo) {
        this.successInfo = successInfo;
        return this;
    }

    protected Server buildTCPServer() throws IOException {
        serverSocket = new ServerSocket(port);
        return this;
    }

    protected Server buildUDPServer() throws IOException {
        datagramSocket = new DatagramSocket(port);
        return this;
    }

    protected void runTCPServer(TCPServerHandler tcpServerHandler) throws IOException {
        tcpIsStop = false;
        while (true) {
            tcpServerHandler.acceptInfo(acceptInfo);
            Socket socket = serverSocket.accept();
            tcpServerHandler.successInfo(successInfo);
            tcpServerHandler.handle(socket.getInputStream(), socket.getOutputStream());

            if (tcpIsStop) {
                serverSocket.close();
                serverSocket = null;
                break;
            }
        }
    }

    protected void stopTCPServer() {
        tcpIsStop = true;
    }

    protected void runUDPServer(UDPServerHandler udpServerHandler) throws IOException {
        udpIsStop = false;
        while (true) {
            byte[] buf = new byte[udpServerHandler.bufferSize()];
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            udpServerHandler.handle(packet.getData());
            udpServerHandler.successInfo(successInfo);

            if (udpIsStop) {
                datagramSocket.close();
                datagramSocket = null;
                break;
            }
        }
    }

    protected void stopUDPServer() {
        udpIsStop = true;
    }
}
