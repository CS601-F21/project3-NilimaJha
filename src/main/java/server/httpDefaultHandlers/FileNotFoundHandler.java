package server.httpDefaultHandlers;

import handler.Handler;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import server.HTTPConstants;
import server.HTTPRequest;
import server.HTTPResponse;

public class FileNotFoundHandler implements Handler {
    private static final Logger LOGGER = LogManager.getLogger(FileNotFoundHandler.class);


    @Override
    public HTTPResponse handle(HTTPRequest httpRequest) {
        LOGGER.info("Handling Request with Path :" + httpRequest.getMethod());
        String responseProtocol = HTTPConstants.PROTOCOL;
        String responseStatusCode = HTTPConstants.CODE_NOT_ALLOWED;
        String responseStatusMessage = HTTPConstants.MESSAGE_NOT_ALLOWED;
        HTTPResponse httpResponse = new HTTPResponse(responseProtocol, responseStatusCode, responseStatusMessage);
        String responseMessage;
        responseMessage = "<!DOCTYPE html>\n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                "<head>\n" +
                "  <title>Path Not Found</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "  <p>404 Path Not Found.</p>\n" +
                "\n" +
                "</body>\n" +
                "</html>";
        httpResponse.setResponseMessage(responseMessage);
        LOGGER.info("Returning following http Response to the client : ");
        LOGGER.info(httpResponse);
        return httpResponse;
    }
}
