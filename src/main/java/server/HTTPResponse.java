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
        return protocol + " " + statusCode + " " + statusMessage + "\n\r\n";
    }

    public void setResponseMessage(String responseMessage) {
//        this.responseMessage = "<!DOCTYPE html>\n" +
//                "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
//                "<head>\n" +
//                "  <title>Resource not found</title>\n" +
//                "</head>\n" +
//                "<body>\n" +
//                "\n" +
//                "  <p>The resource you are looking for was not found.</p>\n" +
//                "\n" +
//                "</body>\n" +
//                "</html>";
        this.responseMessage = responseMessage;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public String getHTTPResponse () {
        return getResponseHeader() + getResponseMessage();
    }

}
