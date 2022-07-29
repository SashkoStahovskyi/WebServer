package com.stahovsky.webserver.io;

import com.stahovsky.webserver.exception.ServerException;
import com.stahovsky.webserver.io.RequestReader;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RequestReaderITest {

    private static final String EXPECTED_CONTENT = "<!DOCTYPE html>\n" +
            "<html lang=\"en\">\n" +
            "<head>\n" +
            "    <meta charset=\"UTF-8\">\n" +
            "    <link rel=\"stylesheet\" href=\"/src/main/resources/webapp/css/styles.css\">\n" +
            "    <title>Title</title>\n" +
            "</head>\n" +
            "<body>\n" +
            "<h1> Hello World </h1>\n" +
            "</body>\n" +
            "</html>";

    private static final String NOT_EXIST_WEB_APP_PATH = "src/main/notExistPath";
    private static final String NOT_EXIST_URI = "/NotExistResource.html";
    private static final String WEB_APP_PATH = "src/main/resources";
    private static final String URI = "/Test.html";

    @BeforeEach
    void before() throws IOException {
        File file = new File("src/main/resources/Test.html");
        file.createNewFile();

        OutputStream outStream = new FileOutputStream(file);
        outStream.write(EXPECTED_CONTENT.getBytes(StandardCharsets.UTF_8));
        outStream.close();
    }

    @AfterEach
    void after() {
        File file = new File("src/main/resources/Test.html");
        file.delete();
    }

    @DisplayName("test readContent Combines WebAppPath And Uri Then Read Content From This Path")
    @Test
    public void testReadContentCombinesWebAppPathAndUriThenReadContentFromThisPath() throws IOException {
        RequestReader requestReader = new RequestReader(WEB_APP_PATH);
        String actualContent = requestReader.readContent(URI);
        assertEquals(EXPECTED_CONTENT, actualContent);
    }

    @DisplayName("test readContent Throw ServerException When Path To Resource Not Exist")
    @Test
    public void testReadContentThrowServerExceptionWhenPathToResourceNotExist() {
        RequestReader requestReader = new RequestReader(NOT_EXIST_WEB_APP_PATH);
        Assertions.assertThrows(ServerException.class,
                () -> requestReader.readContent(URI));
    }

    @DisplayName("test readContent Throw ServerException When Resource Not Exist")
    @Test
    public void testReadContentThrowServerExceptionWhenResourceNotExist() {
        RequestReader requestReader = new RequestReader(WEB_APP_PATH);
        Assertions.assertThrows(ServerException.class,
                () -> requestReader.readContent(NOT_EXIST_URI));
    }

    @DisplayName("test readContent Does Not Throw Exception On Existing Resource")
    @Test
    public void testReadContentDoesNotThrowExceptionOnExistingResource() {
        RequestReader requestReader = new RequestReader(WEB_APP_PATH);
        Assertions.assertDoesNotThrow(() -> requestReader.readContent(URI));
    }
}
