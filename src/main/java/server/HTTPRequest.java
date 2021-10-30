package server;


import java.util.HashMap;

public class HTTPRequest {

    private String method;
    private String path;
    private String protocol;
    private HashMap<String, String> requestHeaders =new HashMap<>();
    private String requestPayload;
    private boolean isValid = true;

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getProtocol() {
        return protocol;
    }

    public HashMap<String, String> getRequestHeaders() {
        return requestHeaders;
    }

    public String getRequestPayload() {
        return requestPayload;
    }

    public boolean getIsValid() {
        return isValid;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void  setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public void putRequestHeaders(String header, String value) {
        this.requestHeaders.put(header, value);
    }

    public void setRequestPayload(String requestPayload) {
        this.requestPayload = requestPayload;
    }

    public void setIsValid(boolean isValid) {
        this.isValid = isValid;
    }
}
