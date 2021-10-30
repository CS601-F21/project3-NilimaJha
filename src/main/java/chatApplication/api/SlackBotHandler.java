package chatApplication.api;

import handler.Handler;
import server.HTTPConstants;
import server.HTTPRequest;
import server.HTTPResponse;

public class SlackBotHandler implements Handler {

    @Override
    public HTTPResponse handle(HTTPRequest httpRequest) {
        if (httpRequest.getMethod().equals(HTTPConstants.GET)) {
            return doGet();
        } else {
           // return doPost(httpRequest.getRequestPayload());
            return doPost();
        }
    }

    public HTTPResponse doGet() {
        String responseProtocol = HTTPConstants.PROTOCOL;
        String responseStatusCode = HTTPConstants.CODE_OK;
        String responseStatusMessage = HTTPConstants.MESSAGE_OK;
        HTTPResponse httpResponse = new HTTPResponse(responseProtocol, responseStatusCode, responseStatusMessage);
        httpResponse.setResponseMessage(generateHTMLResponseForGET());
        return httpResponse;
    }

    private String generateHTMLResponseForGET() {
        String HTMLResponseMessage = "<!DOCTYPE html>\n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                "<head>\n" +
                "  <title>Slack Bot</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "<h1><u>Slack Bot</u></h1>" +
                "<form action=\"/slackbot\" method=\"post\">\n" +
                "  <label for=\"msg\"><b>Enter Message:</b></label><br/><br/>\n" +
                "  <input type=\"text\" id=\"message\" name=\"message\"/><br/><br/>\n" +
                "  <input type=\"submit\" value=\"Submit\"/>\n" +
                "</form>" +
                "\n" +
                "</body>\n" +
                "</html>";
        return HTMLResponseMessage;
    }

    public HTTPResponse doPost() {
        String responseProtocol = HTTPConstants.PROTOCOL;
        String responseStatusCode = HTTPConstants.CODE_OK;
        String responseStatusMessage = HTTPConstants.MESSAGE_OK;
        HTTPResponse httpResponse = new HTTPResponse(responseProtocol, responseStatusCode, responseStatusMessage);
        String HTMLResponseMessage = "<!DOCTYPE html>\n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                "<head>\n" +
                "  <title>Slack Bot</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "<h1><u>Slack Bot</u></h1>" +
                "<p> Inside doPost of SlackBot!" +
                "\n" +
                "</body>\n" +
                "</html>";
        httpResponse.setResponseMessage(HTMLResponseMessage);
        return httpResponse;
    }
}
