package server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HTTPConstants {
    public static final String GET = "GET";
    public static final String POST = "POST";
    public static final String PROTOCOL = "HTTP/1.1";

    public static final String CODE_OK = "200";
    public static final String MESSAGE_OK = "OK";

    public static final String CODE_SERVER_ERROR = "500";
    public static final String MESSAGE_SERVER_ERROR = "Internal Server Error";

    public static final String CODE_NOT_ALLOWED = "405";
    public static final String MESSAGE_NOT_ALLOWED = "Method Not Allowed";


    public static final String CODE_BAD_REQUEST = "400";
    public static final String MESSAGE_BAD_REQUEST = "Bad Request";

    public static final List<String> VALID_POST_QUERY = Arrays.asList("asin=", "message=", "query=");

    public static final String CONTENT_LENGTH = "Content-Length:";
    public static final String CONNECTION_CLOSE = "Connection: closed";


    public static final String NOT_FOUND_PAGE = "<!DOCTYPE html>\n" +
            "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
            "<head>\n" +
            "  <title>Resource not found</title>\n" +
            "</head>\n" +
            "<body>\n" +
            "\n" +
            "  <p>The resource you are looking for was not found.</p>\n" +
            "\n" +
            "</body>\n" +
            "</html>";
}
