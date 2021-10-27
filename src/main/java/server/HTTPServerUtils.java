package server;

import java.io.PrintWriter;

public class HTTPServerUtils {

    /**
     * Send the status line of an HTTP 200 OK response.
     * @param writer
     */
    public static void send200(PrintWriter writer) {
        writer.printf("%s %s\r\n", HTTPConstants.VERSION, HTTPConstants.OK);
        writer.printf("%s \r\n\r\n", HTTPConstants.CONNECTION_CLOSE);
    }

    /**
     * Send the status line of an HTTP 404 Not Found response.
     * @param writer
     */
    public static void send404(PrintWriter writer) {
        writer.printf("%s %s\r\n", HTTPConstants.VERSION, HTTPConstants.NOT_FOUND);
        writer.printf("%s \r\n\r\n", HTTPConstants.CONNECTION_CLOSE);
        writer.println(HTTPConstants.NOT_FOUND_PAGE);
    }

    /**
     * Send the status line of an HTTP 405 Method Not Allowed response.
     * @param writer
     */
    public static void send405(PrintWriter writer) {
        writer.printf("%s %s\r\n", HTTPConstants.VERSION, HTTPConstants.NOT_ALLOWED);
        writer.printf("%s \r\n\r\n", HTTPConstants.CONNECTION_CLOSE);

    }

    /**
     * Send the status line of an HTTP 500 Internal Server Error response.
     * @param writer
     */
    public static void send500(PrintWriter writer) {
        writer.printf("%s %s\r\n", HTTPConstants.VERSION, HTTPConstants.SERVER_ERROR);
        writer.printf("%s \r\n\r\n", HTTPConstants.CONNECTION_CLOSE);

    }
}
