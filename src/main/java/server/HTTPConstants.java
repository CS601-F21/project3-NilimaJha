package server;


/**
 * A helper class to store various constants used for the HTTP server.
 * @author nilimajha
 */
public class HTTPConstants {
    public static final String GET = "GET";
    public static final String POST = "POST";
    public static final String PROTOCOL = "HTTP/1.1";

    public static final int CODE_OK = 200;
    public static final String MESSAGE_OK = "OK";

    public static final int CODE_SERVER_ERROR = 500;
    public static final String MESSAGE_SERVER_ERROR = "Internal Server Error";

    public static final int CODE_NOT_ALLOWED = 405;
    public static final String MESSAGE_NOT_ALLOWED = "Method Not Allowed";

    public static final int CODE_NOT_FOUND = 404;
    public static final String MESSAGE_NOT_FOUND = "Not Found";

    public static final int CODE_BAD_REQUEST = 400;
    public static final String MESSAGE_BAD_REQUEST = "Bad Request";

    public static final String CONTENT_LENGTH = "Content-Length:";
    public static final String CONNECTION_CLOSE = "Connection: closed\s";

    public static final int CHAT_APP_PORT = 9090;
    public static final int SEARCH_APP_PORT = 8080;

}
