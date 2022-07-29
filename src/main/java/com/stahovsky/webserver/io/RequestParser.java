package com.stahovsky.webserver.io;

import com.stahovsky.webserver.entity.HttpMethod;
import com.stahovsky.webserver.entity.HttpStatus;
import com.stahovsky.webserver.entity.Request;
import com.stahovsky.webserver.exception.ServerException;
import com.stahovsky.webserver.validitymanager.ValidityManager;

import java.io.BufferedReader;
import java.io.IOException;
 public  class RequestParser {

    private final static String COLON_PATTERN_WITH_SPACE = ":\s";
    private final static String SPACE_PATTERN = "\s";
    private final static String COLON_PATTERN = ":";

    public RequestParser() {
    }

    public Request parse(BufferedReader socketReader) {
        Request request = new Request();

        try {
            String requestContent = socketReader.readLine();
            ValidityManager.validateHttpStartLine(requestContent);
            injectUriAndMethod(requestContent, request);

            while ((requestContent = socketReader.readLine()) != null && !requestContent.isEmpty()) {
                injectHeaders(requestContent, request);
            }
            return request;

        } catch (IOException e) {
            throw new ServerException(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

     static void injectUriAndMethod(String requestContent, Request request) {
        String[] startLine = requestContent.split(SPACE_PATTERN, 3);
        String requestHttpMethod = startLine[0];
        String requestUri = startLine[1];
        String requestHttpVersion = startLine[2];
        HttpMethod httpMethod = HttpMethod.getMethod(requestHttpMethod);

        if (!httpMethod.equals(HttpMethod.GET)) {
            throw new ServerException(HttpStatus.METHOD_NOT_ALLOWED);
        }
        request.setHttpMethod(HttpMethod.valueOf(requestHttpMethod));
        request.setUri(requestUri);
        request.setHttpVersion(requestHttpVersion);
    }

     static void injectHeaders(String requestContent, Request request) {
        if (requestContent.contains(COLON_PATTERN)) {
            String[] requestHeaders = requestContent.split(COLON_PATTERN_WITH_SPACE);
            String headersKey = requestHeaders[0];
            String headersValue = requestHeaders[1];
            request.setHeaders(headersKey, headersValue);
        } else
            throw new ServerException(HttpStatus.BAD_REQUEST);
    }
}

