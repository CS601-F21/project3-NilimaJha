package server.httpDefaultHandlers;

import handler.Handler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import server.HTTPConstants;
import server.HTTPRequest;
import server.HTTPResponse;

/**
 * Default handler that handles the request with path that is not supported.
 * @author nilimajha
 */
public class PathNotFoundHandler implements Handler {
    private static final Logger LOGGER = (Logger) LogManager.getLogger(PathNotFoundHandler.class);

    /**
     * 405 Handle method with path other than /find,
     * /reviewsearch, /shutdown, /slackbot
     * @param httpRequest
     * @return httpResponse
     */
    @Override
    public HTTPResponse handle(HTTPRequest httpRequest) {
        LOGGER.info("Handling Request with Path :" + httpRequest.getMethod());
        String responseProtocol = HTTPConstants.PROTOCOL;
        int responseStatusCode = HTTPConstants.CODE_NOT_FOUND;
        String responseStatusMessage = HTTPConstants.MESSAGE_NOT_FOUND;
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
