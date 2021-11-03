package searchApplication.api;

import handler.Handler;
import searchApplication.invertedIndex.*;
import server.HTTPConstants;
import server.HTTPRequest;
import server.HTTPResponse;


/**
 *
 * @author nilimajha
 */
public class FindHandler implements Handler {
//    private ReviewFileData reviewFileData;
//    private QAFileData qaFileData;


//    public FindHandler (ReviewFileData reviewInvertedIndex, QAFileData qaInvertedIndex) {
////        this.reviewFileData = reviewInvertedIndex;
////        this.qaFileData = qaInvertedIndex;
//    }

    @Override
    public HTTPResponse handle(HTTPRequest httpRequest) {

        if (httpRequest.getMethod().equals(HTTPConstants.GET)) {
            return doGet();
        } else {
            return doPost(httpRequest.getRequestPayload());
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
                "  <title>Find ASIN</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "<h1><u>Find ASIN</u></h1>" +
                "<form action=\"/find\" method=\"post\">\n" +
                "  <label for=\"msg\"><b>Enter ASIN:</b></label><br/><br/>\n" +
                "  <input type=\"text\" id=\"asin\" name=\"asin\"/><br/><br/>\n" +
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
        String ASIN = httpRequestMessage.substring(httpRequestMessage.indexOf("=") + 1);
        String responseData = new FileDataOps().findAsin(ASIN);
        httpResponse.setResponseMessage(generateHTMLResponseForPOST("Find ASIN", responseData));
        return httpResponse;
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
