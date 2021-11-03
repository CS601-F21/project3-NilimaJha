package server.httpDefaultHandlers;

import handler.Handler;
import searchApplication.invertedIndex.FileDataOps;
import server.HTTPConstants;
import server.HTTPRequest;
import server.HTTPResponse;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class ShutDownHandler implements Handler {

    @Override
    public HTTPResponse handle(HTTPRequest httpRequest) {
        return null;
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
                "  <title>Server Shutdown</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "<h1><u>Server Shutdown</u></h1>" +
                "<form action=\"/shutdown\" method=\"post\">\n" +
                "  <label for=\"msg\"><b>Enter Username:</b></label>\n" +
                "  <input type=\"text\" id=\"username\" name=\"username\"/><br/><br/>\n" +
                "  <label for=\"msg\"><b>Enter Password:</b></label>\n" +
                "  <input type=\"text\" id=\"password\" name=\"password\"/><br/><br/>\n" +
                "  <input type=\"submit\" value=\"Submit\"/>\n" +
                "</form>" +
                "\n" +
                "</body>\n" +
                "</html>";
        return HTMLResponseMessage;
    }

    public HTTPResponse doPost(String httpRequestMessage) {

        String responseProtocol = HTTPConstants.PROTOCOL;
        String responseStatusCode = HTTPConstants.CODE_OK;
        String responseStatusMessage = HTTPConstants.MESSAGE_OK;
        HTTPResponse httpResponse = new HTTPResponse(responseProtocol, responseStatusCode, responseStatusMessage);
        // extracting asin from request query.
        String bodyValue = null;

        try {
            bodyValue = URLDecoder.decode(httpRequestMessage.substring(httpRequestMessage.indexOf("=")+1, httpRequestMessage.length()), StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
//        String ASIN = httpRequestMessage.substring(httpRequestMessage.indexOf("=") + 1);
//        //String responseData = new FileDataOps(reviewFileData, qaFileData).findAsin(ASIN);
//        httpResponse.setResponseMessage(generateHTMLResponseForPOST("Find ASIN", responseData));
//        return httpResponse;
    }

    private String generateHTMLResponseForPOST(String title, String body) {
        String HTMLResponseMessage = "<!DOCTYPE html>\n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                "<head>\n" +
                "<title>" + title + " </title>\n" +
                "<h2><u>" + title + " </u></h2>\n" +
                "</head>\n" +
                "<body>\n" +
                "<p>" ;

        String[] lines = body.split("\n");
        for(String line: lines){
            HTMLResponseMessage += line + "<br/>" ;
        }

        HTMLResponseMessage += "</p>\n" +
                "</body>\n" +
                "</html>";

        return HTMLResponseMessage;
    }
}
