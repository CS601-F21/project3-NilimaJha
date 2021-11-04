package server.httpDefaultHandlers;

import handler.Handler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import server.HTTPConstants;
import server.HTTPRequest;
import server.HTTPResponse;

/**
 * Default handler handles the request when some unexpected error occur during that request processing.
 * @author nilimajha
 */
public class InternalServerErrorHandler implements Handler {
    private static final Logger LOGGER = (Logger) LogManager.getLogger(InternalServerErrorHandler.class);

    /**
     * Handles request when some unexpected error occurs.
     * @param httpRequest
     * @return
     */
    @Override
    public HTTPResponse handle (HTTPRequest httpRequest) {
        String responseProtocol = HTTPConstants.PROTOCOL;
        int responseStatusCode = HTTPConstants.CODE_SERVER_ERROR;
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
