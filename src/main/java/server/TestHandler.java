package server;
import handler.Handler;

public class TestHandler implements Handler {
    @Override
    public HTTPResponse handle(HTTPRequest httpRequest) {
        String responseProtocol = HTTPConstants.PROTOCOL;
        String responseStatusCode = HTTPConstants.CODE_OK;
        String responseStatusMessage = HTTPConstants.MESSAGE_OK;
        HTTPResponse httpResponse = new HTTPResponse(responseProtocol, responseStatusCode, responseStatusMessage);
        String responseMessage;
        responseMessage = "<!DOCTYPE html>\n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                "<head>\n" +
                "  <title>Test</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "  <p>Test server worked.</p>\n" +
                "\n" +
                "</body>\n" +
                "</html>";
        httpResponse.setResponseMessage(responseMessage);
        return httpResponse;
    }
}
