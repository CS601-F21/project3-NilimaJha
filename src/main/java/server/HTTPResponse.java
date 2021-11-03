package server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

/**
 * HTTPResponse class stores
 * httpResponse information like
 * protocol,
 * statusCode,
 * statusMessage and
 * responseMessage.
 *
 * @author nilimajha
 */
public class HTTPResponse {
    private static final Logger LOGGER = (Logger) LogManager.getLogger(HTTPServer.class);

    private String protocol;
    private String statusCode;
    private String statusMessage;
    private String responseMessage;

    /**
     * Constructor
     * sets the value of the attribute- protocol, statusCode, and statusMessage
     * @param protocol
     * @param statusCode
     * @param statusMessage
     */
    public HTTPResponse (String protocol, String statusCode, String statusMessage) {
        this.protocol = protocol;
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
    }

    /**
     * returns httpResponse header
     * @return
     */
    public String getResponseHeader() {
        //return protocol + " " + statusCode + " " + statusMessage + "\n" + HTTPConstants.CONNECTION_CLOSE + "\r\n";
        return protocol + " " + statusCode + " " + statusMessage + "\n\r\n";
    }

    /**
     * sets responseMessage attribute of HTTPResponse class.
     * @param responseMessage
     */
    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    /**
     * getter for responseMessage of the HTTPResponse.
     * @return responseMessage
     */
    public String getResponseMessage() {
        return responseMessage;
    }

    /**
     * getHTTPResponse() method returns
     * the entireHTTPResponse.
     * @return
     */
    public String getHTTPResponse () {
        return getResponseHeader() + getResponseMessage();
    }

}
