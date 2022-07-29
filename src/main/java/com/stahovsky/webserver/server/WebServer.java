package com.stahovsky.webserver.server;

import com.stahovsky.webserver.handler.RequestHandler;
import com.stahovsky.webserver.io.RequestReader;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer {

    private String webAppPath;
    private int port;

    public WebServer() {
    }

    public void start() {
        RequestReader requestReader = new RequestReader(webAppPath);
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            validatePort(port);

            while (true) {
                try (Socket socket = serverSocket.accept();
                     BufferedReader socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                     BufferedWriter socketWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {

                    RequestHandler requestHandler = new RequestHandler(socketReader, socketWriter, requestReader);
                    requestHandler.handle();
                }
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setWebAppPath(String webAppPath) {
        this.webAppPath = webAppPath;
    }

    private void validatePort(int port) {
        if (port > 65535 || port < 0) {
            throw new IllegalArgumentException("Allowed range of values must be from 0 to 65535 !");
        }
    }
}

