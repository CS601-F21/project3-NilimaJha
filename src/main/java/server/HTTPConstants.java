package server;

public class HTTPConstants {
    public static final String GET = "GET";
    public static final String POST = "POST";
    public static final String VERSION = "HTTP/1.1";

    public static final String OK = "200 OK";
    public static final String NOT_FOUND = "404 Not Found";
    public static final String NOT_ALLOWED = "405 Method Not Allowed";

    public static final String CONTENT_LENGTH = "Content-Length:";
    public static final String CONNECTION_CLOSE = "Connection: close";

    public static final String FILE_PATH = "/files";


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
