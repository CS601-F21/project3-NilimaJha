package chatApplication.api;

import com.google.gson.JsonObject;
import handler.Handler;
import server.HTTPConstants;
import server.HTTPRequest;
import server.HTTPResponse;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class SlackBotHandler implements Handler {

    @Override
    public HTTPResponse handle(HTTPRequest httpRequest) {
        if (httpRequest.getMethod().equals(HTTPConstants.POST)) {
            return doPost(httpRequest.getRequestPayload());
        } else {
            return doGet();
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
                "  <title>Slack Bot</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "<h1><u>Slack Bot</u></h1>" +
                "<form action=\"/slackbot\" method=\"post\">\n" +
                "  <label for=\"msg\"><b>Enter Message:</b></label><br/><br/>\n" +
                "  <input type=\"text\" id=\"message\" name=\"message\"/><br/><br/>\n" +
                "  <input type=\"submit\" value=\"Submit\"/>\n" +
                "</form>" +
                "\n" +
                "</body>\n" +
                "</html>";
        return HTMLResponseMessage;
    }

    public HTTPResponse doPost(String httpRequestMessage) {
        String threadId = Thread.currentThread().getName();
        System.out.println("[Thread # " + threadId + "]: " + "in doPost()");

//            Gson gson = new Gson();
//
//        Token tokenObject = null;
//        try {
//            tokenObject = gson.fromJson(new FileReader("token.json"), Token.class);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//            System.exit(1);
//        }
//
//        String token = tokenObject.getToken();

        String bodyValue = null;
        try {
            System.out.println("Decoding the message. =" +httpRequestMessage);
            bodyValue = URLDecoder.decode(httpRequestMessage.substring(httpRequestMessage.indexOf("=")+1, httpRequestMessage.length()), StandardCharsets.UTF_8.toString());
            System.out.println("Decoded message.= " +bodyValue);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //String message = URLDecoder.decode(httpRequestMessage, )


        String token = "xoxb-2464212157-2698004405792-VHX1nJ2jZkHuBUSTYHbPJhbD";
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " +token);
        headers.put("Content-Type", "application/json");
        headers.put("Accept-Charset", "utf-8");
        //headers.put("Connection", "close");

        String url = "https://slack.com/api/chat.postMessage";
        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("channel", "C02KR8EDTNZ");
        requestBody.addProperty("text", bodyValue);
        String body = requestBody.toString();
        System.out.println("[Thread # " + threadId + "]: " + "i~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        String slackPostResponse = doPostOnSlack(url, headers, body);

        //parse response;
        System.out.println("[Thread # " + threadId + "]: " + slackPostResponse);
        System.out.println("[Thread # " + threadId + "]: " + "o~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

        String responseProtocol = HTTPConstants.PROTOCOL;
        String responseStatusCode = HTTPConstants.CODE_OK;
        String responseStatusMessage = HTTPConstants.MESSAGE_OK;
        HTTPResponse httpResponse = new HTTPResponse(responseProtocol, responseStatusCode, responseStatusMessage);
        String HTMLResponseMessage = "<!DOCTYPE html>\n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                "<head>\n" +
                "  <title>Slack Bot</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "<h1><u>Slack Bot</u></h1>" +
                "<p> Message successfully posted on slack channel!" +
                "\n" +
                "</body>\n" +
                "</html>";
        httpResponse.setResponseMessage(HTMLResponseMessage);
        return httpResponse;

    }

    public static String doPostOnSlack(String url, Map<String, String> headers, String body) {
        try {
            HttpRequest.Builder builder = HttpRequest.newBuilder(new URI(url));
            builder = setHeaders(builder, headers);
            HttpRequest request = builder.POST((HttpRequest.BodyPublishers.ofString(body))).build();
            HttpClient client = HttpClient.newHttpClient();
            System.out.println("B4 send");
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("after send");
            return response.body();
        } catch(URISyntaxException | IOException | InterruptedException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    private static HttpRequest.Builder setHeaders (HttpRequest.Builder builder, Map < String, String > headers){
        if (headers != null) {
            for (String key : headers.keySet()) {
                builder = builder.setHeader(key, headers.get(key));
            }
        }
        return builder;
    }

    private String parseSlackResponse (String slackPostResponse) {
        return slackPostResponse;
    }
}

