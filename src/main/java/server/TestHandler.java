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
                "  <title>Find</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "<h1><u>Find ASIN</u></h1>" +
                "<form action=\"/echo\" method=\"post\">\n" +
                "  <label for=\"msg\"><b>Enter ASIN:</b></label><br/><br/>\n" +
                "  <input type=\"text\" id=\"msg\" name=\"msg\"/><br/><br/>\n" +
                "  <input type=\"submit\" value=\"Submit\"/>\n" +
                "</form>" +
                "\n" +
                "</body>\n" +
                "</html>";
        httpResponse.setResponseMessage(responseMessage);
        return httpResponse;
    }
}
