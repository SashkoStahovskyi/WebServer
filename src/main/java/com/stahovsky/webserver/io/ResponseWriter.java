package com.stahovsky.webserver.io;

import com.stahovsky.webserver.entity.Response;

import java.io.BufferedWriter;
import java.io.IOException;

public class ResponseWriter {

    private final static String HTTP_VERSION = "HTTP/1.1";
    private final static String LINE_END = "\r\n";

    public void writeResponse(BufferedWriter socketWriter, Response response) throws IOException {
        socketWriter.write(HTTP_VERSION + response.getHttpStatus());
        addLineEnd(socketWriter);
        socketWriter.write(response.getContent());
    }

    public void writeErrorResponse(BufferedWriter socketWriter, Response response) throws IOException {
        socketWriter.write(HTTP_VERSION + response.getHttpStatus());
        addLineEnd(socketWriter);
    }

    private void addLineEnd(BufferedWriter socketWriter) throws IOException {
        socketWriter.write(LINE_END);
        socketWriter.write(LINE_END);
    }
}
