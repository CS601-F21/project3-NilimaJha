package chatApplication.api;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import handler.Handler;
import server.HTTPConstants;
import server.HTTPRequest;
import server.HTTPResponse;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

public class SlackBotHandler implements Handler {

    @Override
    public HTTPResponse handle(HTTPRequest httpRequest) {
        if (httpRequest.getMethod().equals(HTTPConstants.GET)) {
            return doGet();
        } else {
           // return doPost(httpRequest.getRequestPayload());
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

        String token = "xoxb-2464212157-2698004405792-VHX1nJ2jZkHuBUSTYHbPJhbD";
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " +token);
        System.out.println("================================");
        System.out.println(headers.get("Authorization"));
        System.out.println("================================");

        headers.put("Content-Type", "application/json");
        headers.put("Accept-Charset", "utf-8");
        //headers.put("Connection", "close");
        System.out.println("Size of header Map: "+headers.size());
        String url = "https://slack.com/api/chat.postMessage";
        JsonObject obj = new JsonObject();
        obj.addProperty("channel", "C02KR8EDTNZ");
        obj.addProperty("text", httpRequestMessage);
        String body = obj.toString();
        System.out.println("+++++++++++++++++++++++++++++++++++");
        System.out.println(body);
        String slackPostResponse = doPostOnSlack(url, headers, body);
        //parse response;
        System.out.println(slackPostResponse);

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
                "<p> Inside doPost of SlackBot!" +
                "\n" +
                "</body>\n" +
                "</html>";
        httpResponse.setResponseMessage(HTMLResponseMessage);
        return httpResponse;

    }

    public static String doPostOnSlack(String url, Map<String, String> headers, String body) {
        try {
            System.out.println("Inside do post method");
            HttpRequest.Builder builder = HttpRequest.newBuilder(new URI(url));
            System.out.println("Created Builder.");
            System.out.println("Size of header Map before sending it to setHeaders method: "+headers.size());
            builder = setHeaders(builder, headers);
            System.out.println("Header Set.");
            HttpRequest request = builder.POST((HttpRequest.BodyPublishers.ofString(body))).build();
            System.out.println("Set method as Post in request.");
            System.out.println("--------->" + request.method());
            System.out.println("--------->" + request.headers());
            System.out.println("--------->" + request.uri());
            System.out.println("--------->" + request.bodyPublisher());

            HttpClient client = HttpClient.newHttpClient();
            System.out.println("Sending the request to slack.");
            //System.out.println("HttpResponse.BodyHandlers.ofString() :" + HttpResponse.BodyHandlers.ofString());
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("-.-.-.-.-.-.-.-.->Body      "+response.body());
            System.out.println("-.-.-.-.-.-.-.-.->uri       "+response.uri());
            System.out.println("-.-.-.-.-.-.-.-.->SC        "+response.statusCode());

            return response.body();

        } catch(URISyntaxException | IOException | InterruptedException e) {
            System.err.println(e.getMessage());
            return null;
        }

    }


    private static HttpRequest.Builder setHeaders (HttpRequest.Builder builder, Map < String, String > headers){
        System.out.println("Inside setHeaders method.");
        System.out.println("Size of header Map: "+headers.size());
        if (headers != null) {
            System.out.println("header map provided is not null.");
            int i = 1;
            for (String key : headers.keySet()) {
                System.out.println(i +"Headers key:" +key);
                builder = builder.setHeader(key, headers.get(key));
                System.out.println("Headers value:" +headers.get(key));
                i++;
            }
            System.out.println("Header set done.");
        }
        System.out.println("returning builder object");
        return builder;
    }

    private String parseSlackResponse (String slackPostResponse) {
//        if (slackPostResponse.contains("ok")) {
//
//        }
        //String channel =
        return slackPostResponse;
    }
}

