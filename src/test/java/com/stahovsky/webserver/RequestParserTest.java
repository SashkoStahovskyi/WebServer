package com.stahovsky.webserver;

import com.stahovsky.webserver.entity.Request;
import com.stahovsky.webserver.exception.ServerException;
import com.stahovsky.webserver.io.RequestParser;
import com.stahovsky.webserver.validitymanager.ValidityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.stahovsky.webserver.io.RequestParser.injectUriAndMethod;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RequestParserTest {

    private final static String START_LINE_WITHOUT_SPACES = "GET/wiki/somePageHTTP/1.1";
    private final static String NOT_CORRECT_HTTP_VERSION = "GET /wiki/somePage NOT/1.1";
    private final static String BLANK_REQUEST_START_lINE = "  ";
    private final static String HTTP_REQUEST_START_LINE = "GET /wiki/somePage HTTP/1.1";
    private final static String EXPECTED_HTTP_VERSION = "HTTP/1.1";
    private final static String EXPECTED_METHOD_GET = "GET";
    private final static String EXPECTED_URI = "/wiki/somePage";
    private RequestParser requestParser;
    private Request request;

    @BeforeEach
    public void before() {
        requestParser = new RequestParser();
        request = new Request();
    }

    @DisplayName("test injectUriAndMethod Inject From Request Start Line Method Uri And Http Version And Set It Into Request Object")
    @Test
    public void testInjectUriAndMethodInjectFromRequestStartLineMethodUriAndHttpVersionAndSetItIntoRequestObject() {
        injectUriAndMethod(HTTP_REQUEST_START_LINE, request);
        String actualMethod = request.getHttpMethod().toString();
        String actualUri = request.getUri();
        String actualHttpVersion = request.getHttpVersion();

        assertEquals(EXPECTED_METHOD_GET, actualMethod);
        assertEquals(EXPECTED_URI, actualUri);
        assertEquals(EXPECTED_HTTP_VERSION, actualHttpVersion);
    }

    @DisplayName("test validateHttpRequest Throws ServerExceptions When Request Start Line Is Blank")
    @Test
    public void testValidateHttpRequestThrowsServerExceptionsWhenRequestStartLineIsBlank() {
        Assertions.assertThrows(ServerException.class,
                () -> ValidityManager.validateHttpStartLine(BLANK_REQUEST_START_lINE));
    }

    @DisplayName("test validateHttpRequest Throws ServerExceptions When Request Start Line Without Space")
    @Test
    public void testValidateHttpRequestThrowsServerExceptionsWhenRequestStartLineIsWithoutSpace() {
        Assertions.assertThrows(ServerException.class,
                () -> ValidityManager.validateHttpStartLine(START_LINE_WITHOUT_SPACES));
    }

    @DisplayName("test validateHttpRequest Throws ServerExceptions When Request Start Line Have Not Correct HTTP Version")
    @Test
    public void testValidateHttpRequestThrowsServerExceptionsWhenRequestStartLineHaveNotCorrectHTTPVersion() {
        Assertions.assertThrows(ServerException.class,
                () -> ValidityManager.validateHttpStartLine(NOT_CORRECT_HTTP_VERSION));
    }
}