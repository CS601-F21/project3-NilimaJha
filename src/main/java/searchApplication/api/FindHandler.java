package searchApplication.api;

import handler.Handler;
import org.apache.commons.text.StringEscapeUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import searchApplication.invertedIndex.*;
import server.HTTPConstants;
import server.HTTPRequest;
import server.HTTPResponse;


/**
 * FileHandler class implements Handler Interface
 * and handles httpRequest to SearchApplication Server with path /find
 * @author nilimajha
 */
public class FindHandler implements Handler {
    private static final Logger LOGGER = (Logger) LogManager.getLogger(FindHandler.class);

    /**
     * method handle() handles
     * /find request by calling doGet() or doPost() appropriately.
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
     * doGet() handles GET request made to /find
     *
     * @return send a form to enter asin number.
     */
    public HTTPResponse doGet() {
        String responseProtocol = HTTPConstants.PROTOCOL;
        int responseStatusCode = HTTPConstants.CODE_OK;
        String responseStatusMessage = HTTPConstants.MESSAGE_OK;
        HTTPResponse httpResponse = new HTTPResponse(responseProtocol, responseStatusCode, responseStatusMessage);
        httpResponse.setResponseMessage(generateHTMLResponseForGET());
        return httpResponse;
    }

    /**
     * generateHTMLResponseForGET() method
     * returns body of response for GET request to /find.
     *
     * @return HTMLResponseMessage
     */
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

    /**
     * handles POST request on /find by extracting the result fo the search.
     * and Returns response in HTML format to display the result of the asin search.
     * @param httpRequestMessage
     * @return
     */
    public HTTPResponse doPost(String httpRequestMessage) {
        String responseProtocol = HTTPConstants.PROTOCOL;
        int responseStatusCode = HTTPConstants.CODE_OK;
        String responseStatusMessage = HTTPConstants.MESSAGE_OK;
        HTTPResponse httpResponse = new HTTPResponse(responseProtocol, responseStatusCode, responseStatusMessage);
        // extracting asin from request query.
        String ASIN = httpRequestMessage.substring(httpRequestMessage.indexOf("=") + 1);
        String responseData = new FileDataOps().findAsin(ASIN);
        LOGGER.debug("Looking for ASIN=" + ASIN);
        httpResponse.setResponseMessage(generateHTMLResponseForPOST("Find ASIN", responseData));
        return httpResponse;
    }

    /**
     * generateHTMLResponseForPOST() response to be sent in HTML format.
     * @param title
     * @param body
     * @return
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
            line = StringEscapeUtils.escapeHtml4(line);
            HTMLResponseMessage += line + "<br/>" ;
        }

        HTMLResponseMessage += "</p>\n" +
                "</body>\n" +
                "</html>";

        return HTMLResponseMessage;
    }
}
