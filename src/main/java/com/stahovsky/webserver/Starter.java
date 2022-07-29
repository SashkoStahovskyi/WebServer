package com.stahovsky.webserver;

import com.stahovsky.webserver.server.WebServer;

public class Starter {

    public static void main(String[] args) {  // todo - check all methods for access modifier

        WebServer server = new WebServer();
        server.setPort(3000);
        server.setWebAppPath("src/main/resources/webapp");
        server.start();
    }
}
