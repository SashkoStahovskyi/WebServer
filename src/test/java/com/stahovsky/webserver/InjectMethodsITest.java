package com.stahovsky.webserver;

import com.stahovsky.webserver.entity.Request;
import com.stahovsky.webserver.exception.ServerException;
import org.junit.jupiter.api.*;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static com.stahovsky.webserver.io.RequestParser.injectHeaders;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class InjectMethodsITest {

    private BufferedReader bufferedReader;
    private Request request;

    private final static String HTTP_REQUEST_HEADERS =
                    "Host: ru.wikipedia.org\n" +
                    "User-Agent: Mozilla/5.0 (X11; U; Linux i686; ru; rv:1.9b5) Gecko/2008050509 Firefox/3.0b5\n" +
                    "Accept: text/html\n" +
                    "Connection: close\n";

    private static final String EXPECTED_HEADERS = "{Accept=text/html, User-Agent=Mozilla/5.0 (X11; U; Linux i686; ru; rv:1.9b5) " +
            "Gecko/2008050509 Firefox/3.0b5, Connection=close, Host=ru.wikipedia.org}";

    @BeforeEach
    public void before() throws IOException {
        File file = new File("src/main/resources/TestInjectHeaders.txt");
        file.createNewFile();
        OutputStream outStreamWriter = new FileOutputStream(file);
        outStreamWriter.write(HTTP_REQUEST_HEADERS.getBytes(StandardCharsets.UTF_8));
        outStreamWriter.close();

        bufferedReader = new BufferedReader(new FileReader(file));
        request = new Request();
    }

    @AfterEach
    public void after() {
        File path = new File("src/main/resources/TestInjectHeaders.txt");
        path.delete();
    }

    @DisplayName("test injectHeaders Inject From Request Headers Content And Set It Into Request Object")
    @Test
    public void testInjectHeadersInjectFromRequestHeadersContentAndSetItIntoRequestObject() throws IOException {
        String requestContent;
        while ((requestContent = bufferedReader.readLine()) != null && !requestContent.isEmpty()) {
            injectHeaders(requestContent, request);
        }
        String actualHeaders = request.getHeaders().toString();
        assertEquals(EXPECTED_HEADERS, actualHeaders);
    }

    @DisplayName("test injectHeaders Throw ServerException When Request Headers Not Correct")
    @Test
    public void testInjectHeadersThrowServerExceptionWhenRequestHeadersNotCorrect() {
        String notCorrectRequestContent = "Host ru.wikipedia.org\n";
        Assertions.assertThrows(ServerException.class,
                () -> injectHeaders(notCorrectRequestContent, request));
    }
}
