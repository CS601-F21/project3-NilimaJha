package searchApplication.api;

import handler.Handler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import searchApplication.invertedIndex.FileDataOps;
import server.HTTPConstants;
import server.HTTPRequest;
import server.HTTPResponse;


/**
 * ReviewSearchHandler class implements Handler Interface
 * and handles httpRequest to SearchApplication Server with path /reviewsearch
 * @author nilimajha
 */
public class ReviewSearchHandler implements Handler {
    private static final Logger LOGGER = (Logger) LogManager.getLogger(FindHandler.class);

    /**
     * forwards request to the appropriate method.
     * @param httpRequest
     * @return httpResponse
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
     * handles GET request made with /reviewsearch path to SearchApplication.
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
     * sends well-formed HTML response body of GET request.
     * @return HTMLResponseMessage
     */
    private String generateHTMLResponseForGET() {
        String HTMLResponseMessage = "<!DOCTYPE html>\n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                "<head>\n" +
                "  <title>Review Search</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "<h1><u>Review Search</u></h1>" +
                "<form action=\"/reviewsearch\" method=\"post\">\n" +
                "  <label for=\"msg\"><b>Enter Term:</b></label><br/><br/>\n" +
                "  <input type=\"text\" id=\"query\" name=\"query\"/><br/><br/>\n" +
                "  <input type=\"submit\" value=\"Submit\"/>\n" +
                "</form>" +
                "\n" +
                "</body>\n" +
                "</html>";
        return HTMLResponseMessage;
    }

    /**
     * handles POST request made with /reviewsearch path to SearchApplication.
     * @return httpResponse
     */
    public HTTPResponse doPost(String httpRequestMessage) {
        String responseProtocol = HTTPConstants.PROTOCOL;
        String responseStatusCode = HTTPConstants.CODE_OK;
        String responseStatusMessage = HTTPConstants.MESSAGE_OK;
        HTTPResponse httpResponse = new HTTPResponse(responseProtocol, responseStatusCode, responseStatusMessage);
        // extracting asin from request query.
        String term = httpRequestMessage.substring(httpRequestMessage.indexOf("=") + 1);
        String responseData = new FileDataOps().reviewSearch(term);
        httpResponse.setResponseMessage(generateHTMLResponseForPOST("Review Search", responseData));
        return httpResponse;
    }

    /**
     * Dynamically Generates HTMLResponse body for POST request.
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
