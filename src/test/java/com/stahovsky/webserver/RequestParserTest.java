package com.stahovsky.webserver;

import com.stahovsky.webserver.entity.Request;
import com.stahovsky.webserver.exception.ServerException;
import com.stahovsky.webserver.io.RequestParser;
import com.stahovsky.webserver.validitymanager.ValidityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RequestParserTest {

    private final static String NOT_CORRECT_HTTP_VERSION = "GET /wiki/somePage NOT/1.1";
    private final static String START_LINE_WITHOUT_SPACES = "GET/wiki/somePageHTTP/1.1";
    private final static String EXPECTED_HTTP_VERSION = "HTTP/1.1";
    private final static String EXPECTED_URI = "/wiki/somePage";
    private final static String BLANK_REQUEST_START_lINE = "  ";
    private final static String EXPECTED_METHOD_GET = "GET";
    private RequestParser requestParser;
    private Request request;

    private final static String HTTP_REQUEST_CONTENT_WITH_METHOD_GET = "GET /wiki/somePage HTTP/1.1\n" +
            "Host: ru.wikipedia.org\n" +
            "User-Agent: Mozilla/5.0 (X11; U; Linux i686; ru; rv:1.9b5) Gecko/2008050509 Firefox/3.0b5\n" +
            "Accept: text/html\n" +
            "Connection: close\n";

    private static final String EXPECTED_HEADERS = "{Accept=text/html, User-Agent=Mozilla/5.0 (X11; U; Linux i686; ru; rv:1.9b5) " +
            "Gecko/2008050509 Firefox/3.0b5, Connection=close, Host=ru.wikipedia.org}";

    @BeforeEach
    public void before() {
        requestParser = new RequestParser();
        request = new Request();
    }

    @DisplayName("test requestParser After Parsing Request Add All Specified Data To Request Object")
    @Test
    public void testRequestParserAfterParsingRequestAddAllSpecifiedDataToRequestObject() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                new ByteArrayInputStream(HTTP_REQUEST_CONTENT_WITH_METHOD_GET.getBytes())));

        request = requestParser.parse(bufferedReader);
        String actualMethod = request.getHttpMethod().toString();
        String actualUri = request.getUri();
        String actualHeaders = (request.getHeaders().toString());
        String actualHttpVersion = request.getHttpVersion();

        assertEquals(EXPECTED_METHOD_GET, actualMethod);
        assertEquals(EXPECTED_URI, actualUri);
        assertEquals(EXPECTED_HEADERS, actualHeaders);
        assertEquals(EXPECTED_HTTP_VERSION, actualHttpVersion);
    }

    @DisplayName("test validateHttpRequest Throws ServerExceptions When Request Start Line Is Blank")
    @Test
    void testValidateHttpRequestThrowsServerExceptionsWhenRequestStartLineIsBlank() {
        Assertions.assertThrows(ServerException.class,
                () -> ValidityManager.validateHttpStartLine(BLANK_REQUEST_START_lINE));
    }

    @DisplayName("test validateHttpRequest Throws ServerExceptions When Request Start Line Without Space")
    @Test
    void testValidateHttpRequestThrowsServerExceptionsWhenRequestStartLineIsWithoutSpace() {
        Assertions.assertThrows(ServerException.class,
                () -> ValidityManager.validateHttpStartLine(START_LINE_WITHOUT_SPACES));
    }

    @DisplayName("test validateHttpRequest Throws ServerExceptions When Request Start Line Have Not Correct HTTP Version")
    @Test
    void testValidateHttpRequestThrowsServerExceptionsWhenRequestStartLineHaveNotCorrectHTTPVersion() {
        Assertions.assertThrows(ServerException.class,
                () -> ValidityManager.validateHttpStartLine(NOT_CORRECT_HTTP_VERSION));
    }
}