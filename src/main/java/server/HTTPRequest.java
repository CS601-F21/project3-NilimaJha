package server;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import java.util.HashMap;

/**
 * HTTPRequest class stores
 * method,
 * path,
 * protocol,
 * requestHeaders,
 * requestPayload,
 * of the HTTPRequest received by the server.
 *
 * @author nilimajha
 */
public class HTTPRequest {
    private static final Logger LOGGER = (Logger) LogManager.getLogger(HTTPServer.class);

    private String method;
    private String path;
    private String protocol;
    private HashMap<String, String> requestHeaders =new HashMap<>();
    private String requestPayload;
    private boolean isValid = true;

    /**
     * getter of attribute method.
     * @return method
     */
    public String getMethod() {
        return method;
    }

    /**
     * getter of attribute path.
     * @return path
     */
    public String getPath() {
        return path;
    }

    /**
     * getter of attribute protocol
     * @return protocol
     */
    public String getProtocol() {
        return protocol;
    }

    /**
     * getter of attribute requestHeader.
     * @return requestHeaders
     */
    public HashMap<String, String> getRequestHeaders() {
        return requestHeaders;
    }

    /**
     * getter for RequestPayload.
     * @return requestPayload
     */
    public String getRequestPayload() {
        return requestPayload;
    }

    /**
     * getter for attribute isValid.
     * @return isValid
     */
    public boolean getIsValid() {
        return isValid;
    }

    /**
     * setter for attribute method.
     * @param method
     */
    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * setter for attribute path.
     * @param path
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * setter for attribute protocol.
     * @param protocol
     */
    public void  setProtocol(String protocol) {
        this.protocol = protocol;
    }

    /**
     * adds value to the requestHeader map.
     * @param header
     * @param value
     */
    public void putRequestHeaders(String header, String value) {
        this.requestHeaders.put(header, value);
    }

    /**
     * setter for attribute requestPayload .
     * @param requestPayload
     */
    public void setRequestPayload(String requestPayload) {
        this.requestPayload = requestPayload;
    }

    /**
     * Sets the request as valid true or false.
     * @param isValid
     */
    public void setIsValid(boolean isValid) {
        this.isValid = isValid;
    }
}
