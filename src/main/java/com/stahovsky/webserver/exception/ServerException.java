package com.stahovsky.webserver.exception;

import com.stahovsky.webserver.entity.HttpStatus;

public class ServerException extends RuntimeException {

    private HttpStatus httpStatus;

    public ServerException(Throwable cause, HttpStatus httpStatus) {
        super(cause);
        this.httpStatus = httpStatus;
    }

    public ServerException(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
