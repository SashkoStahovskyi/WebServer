package com.stahovsky.webserver.entity;

public enum HttpStatus {

    OK(200, "OK"),
    BAD_REQUEST(400, "Bad Request"),
    NOT_FOUND(404, "Not Found"),
    METHOD_NOT_ALLOWED(405, " Method Not Allowed"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error");

    private final int status;
    private final String message;

    HttpStatus(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
