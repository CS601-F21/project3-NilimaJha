package server;

import java.util.HashMap;

public class HTTPRequest {
    private String method;
    private String path;
    private HashMap<String, String> requestHeaders =new HashMap<>();
    private String message;

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public HashMap<String, String> getRequestHeaders() {
        return requestHeaders;
    }

    public String getMessage() {
        return message;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void putRequestHeaders(String header, String value) {
        this.requestHeaders.put(header, value);
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
