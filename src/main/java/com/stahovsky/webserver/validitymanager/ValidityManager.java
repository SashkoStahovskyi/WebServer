package com.stahovsky.webserver.validitymanager;

import com.stahovsky.webserver.entity.HttpStatus;
import com.stahovsky.webserver.exception.ServerException;

import java.io.File;

public class ValidityManager {

    private static final String CORRECT_HTTP_VERSION = "HTTP/1.1";
    private final static String SPACE_PATTERN = "\s";

    public static void validateHttpStartLine(String request) {

        if (request.isBlank()) {
            throw new ServerException(HttpStatus.BAD_REQUEST);
        }
        if (!request.contains(CORRECT_HTTP_VERSION)) {
            throw new ServerException(HttpStatus.BAD_REQUEST);
        }
        if (!request.contains(SPACE_PATTERN)) {
            throw new ServerException(HttpStatus.BAD_REQUEST);
        }
    }

    public static boolean validatePath(File path) {
        if (!path.exists()) {
            throw new ServerException(HttpStatus.NOT_FOUND);
        }
        return true;
    }
}
