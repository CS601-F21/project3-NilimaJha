package server;

public class HTTPResponse {
    private String protocol;
    private String statusCode;
    private String reasonPhrase;
    private String responseMessage;

    public HTTPResponse(String protocol, String statusCode, String reasonPhrase) {
        this.protocol = protocol;
        this.statusCode = statusCode;
        this.reasonPhrase = reasonPhrase;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage() {

    }

}
