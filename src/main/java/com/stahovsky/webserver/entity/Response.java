package com.stahovsky.webserver.entity;

public class Response {

    private HttpStatus httpStatus;
    private String content;

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getContent() {
        return content;
    }
}
