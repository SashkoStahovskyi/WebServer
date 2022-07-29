package com.stahovsky.webserver.handler;

import com.stahovsky.webserver.entity.HttpStatus;
import com.stahovsky.webserver.entity.Request;
import com.stahovsky.webserver.entity.Response;
import com.stahovsky.webserver.exception.ServerException;
import com.stahovsky.webserver.io.RequestParser;
import com.stahovsky.webserver.io.RequestReader;
import com.stahovsky.webserver.io.ResponseWriter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class RequestHandler {

    private final BufferedReader socketReader;
    private final BufferedWriter socketWriter;
    private final RequestReader requestReader;


    public RequestHandler(BufferedReader socketReader, BufferedWriter socketWriter, RequestReader requestReader) {
        this.socketReader = socketReader;
        this.socketWriter = socketWriter;
        this.requestReader = requestReader;
    }

    public void handle() throws IOException {
        RequestParser requestParser = new RequestParser();
        Response response = new Response();
        ResponseWriter responseWriter = new ResponseWriter();

        try {
            Request request = requestParser.parse(socketReader);
            String content = requestReader.readContent(request.getUri());
            response.setContent(content);
            response.setHttpStatus(HttpStatus.OK);
            responseWriter.writeResponse(socketWriter, response);

        } catch (ServerException e) {
            response.setHttpStatus(e.getHttpStatus());
            responseWriter.writeErrorResponse(socketWriter, response);
            e.printStackTrace();
        }
    }
}


