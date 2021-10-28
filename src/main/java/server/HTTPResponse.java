package server;

public class HTTPResponse {
    private String protocol;
    private String statusCode;
    private String statusMessage;
    private String responseMessage;

    public HTTPResponse (String protocol, String statusCode, String statusMessage) {
        this.protocol = protocol;
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
    }

    public String getResponseHeader() {
        return protocol + " " + statusCode + " " + statusMessage + "\n" + HTTPConstants.CONNECTION_CLOSE + "\r\n";
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public String getHTTPResponse () {
        return getResponseHeader() + getResponseMessage();
    }

}
