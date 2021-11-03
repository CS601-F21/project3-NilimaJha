package server.httpDefaultHandlers;

import handler.Handler;
import server.HTTPConstants;
import server.HTTPRequest;
import server.HTTPResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

/**
 * ShutdownHandler handles request with /shutdown
 * @author nilimajha
 */
public class ShutDownHandler implements Handler {

    /**
     * assign the request to the appropriate method as per the request.
     * @param httpRequest
     * @return
     */
    @Override
    public HTTPResponse handle(HTTPRequest httpRequest) {
        if (httpRequest.getMethod().equals(HTTPConstants.GET)) {
            return doGet();
        } else {
            return doPost(httpRequest.getRequestPayload());
        }
    }

    /**
     * handles GET request made with /shutdown path to server.
     * @return httpResponse
     */
    public HTTPResponse doGet() {
        String responseProtocol = HTTPConstants.PROTOCOL;
        String responseStatusCode = HTTPConstants.CODE_OK;
        String responseStatusMessage = HTTPConstants.MESSAGE_OK;
        HTTPResponse httpResponse = new HTTPResponse(responseProtocol, responseStatusCode, responseStatusMessage);
        httpResponse.setResponseMessage(generateHTMLResponseForGET());
        return httpResponse;
    }

    /**
     * returns the response body for doGet method.
     * @return htmlResponseMessage
     */
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

    /**
     * handles POST request body made with /shutdown path to server.
     * @param httpRequestMessage
     * @return httpResponse
     */
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
        return httpResponse;
    }

    /**
     * generateHTMLResponseForPOST() dynamically generates
     * response body in XHTML format
     * @param title
     * @param body
     * @return HTMLResponseMessage
     */
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
