package server;

import java.io.PrintWriter;

public class ServerUtils {

    /**
     * Send the status line of an HTTP 200 OK response.
     * @param writer
     */
    public static void send200(PrintWriter writer) {
        writer.printf("%s %s\r\n", HTTPConstants.VERSION, HTTPConstants.OK);
        writer.printf("%s \r\n\r\n", HTTPConstants.CONNECTION_CLOSE);
    }

}
