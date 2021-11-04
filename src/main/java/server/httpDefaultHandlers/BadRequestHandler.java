package server.httpDefaultHandlers;

import handler.Handler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import server.HTTPConstants;
import server.HTTPRequest;
import server.HTTPResponse;

/**
 * Default handler that handles the request that is not well-formed or missing expected information.
 *@author nilimajha
 */
public class BadRequestHandler implements Handler {
    private static final Logger LOGGER = (Logger) LogManager.getLogger(BadRequestHandler.class);

    /**
     * returns 400 badRequest httpResponse
     * @param httpRequest
     * @return httpResponse
     */
    @Override
    public HTTPResponse handle(HTTPRequest httpRequest) {
        LOGGER.info("Handling Request with Path :" + httpRequest.getPath());
        String responseProtocol = HTTPConstants.PROTOCOL;
        int responseStatusCode = HTTPConstants.CODE_BAD_REQUEST;
        String responseStatusMessage = HTTPConstants.MESSAGE_BAD_REQUEST;
        HTTPResponse httpResponse = new HTTPResponse(responseProtocol, responseStatusCode, responseStatusMessage);
        String responseMessage;
        responseMessage = "<!DOCTYPE html>\n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                "<head>\n" +
                "  <title>Bad Request</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "  <p>Bad Request!</p>\n" +
                "\n" +
                "</body>\n" +
                "</html>";
        httpResponse.setResponseMessage(responseMessage);
        LOGGER.info("Returning following http Response to the client : ");
        LOGGER.info(httpResponse);
        return httpResponse;
    }
}
