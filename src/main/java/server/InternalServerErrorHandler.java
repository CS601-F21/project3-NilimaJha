package server;

public class InternalServerErrorHandler implements Handler {

    @Override
    public HTTPResponse handle (HTTPRequest httpRequest) {
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
        return httpResponse;
    }
}
