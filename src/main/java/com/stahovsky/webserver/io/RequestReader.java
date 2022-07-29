package com.stahovsky.webserver.io;

import java.io.*;

import static com.stahovsky.webserver.validitymanager.ValidityManager.validatePath;

public class RequestReader {

    private final String webAppPath;

    public RequestReader(String webAppPath) {
        this.webAppPath = webAppPath;
    }

    public String readContent(String uri) throws IOException {
        String content;
        File resource = new File(String.valueOf(webAppPath), uri);
        validatePath(resource);

        try (InputStream resourceStreamReader = new BufferedInputStream(new FileInputStream(resource))) {
            content = new String(resourceStreamReader.readAllBytes());
        }
        return content;
    }
}
