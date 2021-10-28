package server;

public class BadRequestHandler implements Handler {

    @Override
    public HTTPResponse handle(HTTPRequest httpRequest) {
        String responseProtocol = HTTPConstants.PROTOCOL;
        String responseStatusCode = HTTPConstants.CODE_BAD_REQUEST;
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
                "  <p>The Request made is not correct.</p>\n" +
                "\n" +
                "</body>\n" +
                "</html>";
        httpResponse.setResponseMessage(responseMessage);
        return httpResponse;
    }
}
