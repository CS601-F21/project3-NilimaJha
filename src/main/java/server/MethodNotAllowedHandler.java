package server;

import handler.Handler;

public class MethodNotAllowedHandler implements Handler {

    @Override
    public HTTPResponse handle(HTTPRequest httpRequest) {
        String responseProtocol = HTTPConstants.PROTOCOL;
        String responseStatusCode = HTTPConstants.CODE_NOT_ALLOWED;
        String responseStatusMessage = HTTPConstants.MESSAGE_NOT_ALLOWED;
        HTTPResponse httpResponse = new HTTPResponse(responseProtocol, responseStatusCode, responseStatusMessage);
        String responseMessage;
        responseMessage = "<!DOCTYPE html>\n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                "<head>\n" +
                "  <title>Method Not Allowed</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "  <p>Method Not Allowed.</p>\n" +
                "\n" +
                "</body>\n" +
                "</html>";
        httpResponse.setResponseMessage(responseMessage);
        return httpResponse;
    }
}
