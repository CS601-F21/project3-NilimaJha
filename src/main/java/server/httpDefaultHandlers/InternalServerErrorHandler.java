package handler;

import handler.Handler;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import server.HTTPConstants;
import server.HTTPRequest;
import server.HTTPResponse;

public class InternalServerErrorHandler implements Handler {
    private static final Logger LOGGER = LogManager.getLogger(InternalServerErrorHandler.class);


    @Override
    public HTTPResponse handle (HTTPRequest httpRequest) {
//        LOGGER.info("Handling Request with Method :" + httpRequest.getMethod());
        String responseProtocol = HTTPConstants.PROTOCOL;
        String responseStatusCode = HTTPConstants.CODE_SERVER_ERROR;
        String responseStatusMessage = HTTPConstants.MESSAGE_SERVER_ERROR;
        HTTPResponse httpResponse = new HTTPResponse(responseProtocol, responseStatusCode, responseStatusMessage);
        String responseMessage;
        responseMessage = "<!DOCTYPE html>\n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                "<head>\n" +
                "  <title>Internal Server Error</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "  <p>Internal Server Error.</p>\n" +
                "\n" +
                "</body>\n" +
                "</html>";
        httpResponse.setResponseMessage(responseMessage);
        LOGGER.info("Returning following http Response to the client : ");
        LOGGER.info(httpResponse);
        return httpResponse;
    }
}
