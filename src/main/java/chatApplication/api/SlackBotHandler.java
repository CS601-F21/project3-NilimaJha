package chatApplication.api;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import handler.Handler;
import server.HTTPConstants;
import server.HTTPRequest;
import server.HTTPResponse;
import server.httpDefaultHandlers.InternalServerErrorHandler;

import java.io.FileNotFoundException;
import java.io.FileReader;
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

/**
 * SlackBotHandler class implements Handler Interface
 * and handles GET and POST httpRequest to ChatApplication Server with path /slackbot
 * @author nilimajha
 */
public class SlackBotHandler implements Handler {
    private String slackTokenFileName;
    private String slackChannelID;

    /**
     * Constructor that initializes slackTokenFileName and slackChannelID
     * @param slackTokenFileName
     * @param slackChannelID
     */
    public SlackBotHandler(String slackTokenFileName, String slackChannelID) {
        this.slackTokenFileName = slackTokenFileName;
        this.slackChannelID = slackChannelID;
    }

    /**
     * method handle() handles
     * /slackbot request by calling doGet() or doPost() appropriately.
     * @param httpRequest
     * @return httpResponse
     */
    @Override
    public HTTPResponse handle(HTTPRequest httpRequest) {
        if (httpRequest.getMethod().equals(HTTPConstants.POST)) {
            return doPost(httpRequest);
        } else {
            return doGet();
        }
    }

    /**
     * doGet() handles GET request made to /slackbot
     * @return send a form to enter message to be sent to slack channel.
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
     * returns body of response for GET request to /slackbot.
     *
     * @return HTMLResponseMessage
     */
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

    /**
     * Handles post request made by client for path /slackbot.
     * Decode request payload form a json obj to be sent to slackAPI,
     * calls doPostOnSlack method to posts the message on the slack api and
     * send appropriate response in well-formed XHTML page to the client
     * @param httpRequest
     * @return httpResponse
     */
    public HTTPResponse doPost(HTTPRequest httpRequest) {
//        String threadId = Thread.currentThread().getName();
//        System.out.println("[Thread # " + threadId + "]: " + "in doPost()");

        String httpRequestMessage = httpRequest.getRequestPayload();
        Token tokenObj = tokenConfigFileReader(slackTokenFileName);
        String token = tokenObj.getToken();
        String bodyValue = null;
        try {
           // System.out.println("Decoding the message. =" +httpRequestMessage);
            bodyValue = URLDecoder.decode(httpRequestMessage.substring(httpRequestMessage.indexOf("=")+1), StandardCharsets.UTF_8.toString());
            Map<String, String> headers = new HashMap<>();
            headers.put("Authorization", "Bearer " +token);
            headers.put("Content-Type", "application/json");
            headers.put("Accept-Charset", "utf-8");

            String url = "https://slack.com/api/chat.postMessage";
            JsonObject requestBody = new JsonObject();
            requestBody.addProperty("channel", slackChannelID);
            requestBody.addProperty("text", bodyValue);
            String body = requestBody.toString();
            //System.out.println("[Thread # " + threadId + "]: " + "i~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            SlackResponse slackPostResponse = doPostOnSlack(url, headers, body);

            //parse response;
//        System.out.println("[Thread # " + threadId + "]: " + slackPostResponse);
//        System.out.println("[Thread # " + threadId + "]: " + "o~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

            String responseProtocol = HTTPConstants.PROTOCOL;
            int responseStatusCode = HTTPConstants.CODE_OK;
            String responseStatusMessage = HTTPConstants.MESSAGE_OK;
            HTTPResponse httpResponse = new HTTPResponse(responseProtocol, responseStatusCode, responseStatusMessage);
            //When message is delivered successfully
            String HTMLResponseMessage1 = "<!DOCTYPE html>\n" +
                    "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                    "<head>\n" +
                    "  <title>Slack Bot</title>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "\n" +
                    "<h1><u>Slack Bot</u></h1>" +
                    "<p> MESSAGE <b>'" + bodyValue + "'</b> SENT SUCCESSFULLY ON SLACK CHANNEL!</p>" +
                    "<form action=\"/slackbot\" method=\"post\">\n" +
                    "  <label for=\"msg\"><b>Send another Message:</b></label><br/><br/>\n" +
                    "  <input type=\"text\" id=\"message\" name=\"message\"/><br/><br/>\n" +
                    "  <input type=\"submit\" value=\"Submit\"/>\n" +
                    "</form>" +
                    "\n" +
                    "</body>\n" +
                    "</html>";

            //When message is not delivered due to some error.
            String HTMLResponseMessage2 = "<!DOCTYPE html>\n" +
                    "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                    "<head>\n" +
                    "  <title>Slack Bot</title>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "\n" +
                    "<h1><u>Slack Bot</u></h1>" +
                    "<p> MESSAGE <b>'" + bodyValue + "'</b> WAS NOT SENT ON THE SLACK CHANNEL!</p>" +
                    "<p>Reason : " + slackPostResponse.getError() + "</p>" +
                    "\n" +
                    "</body>\n" +
                    "</html>";


            if (slackPostResponse.isOk()) {
                httpResponse.setResponseMessage(HTMLResponseMessage1);
            } else {
                httpResponse.setResponseMessage(HTMLResponseMessage2);
            }
            return httpResponse;
            // System.out.println("Decoded message.= " +bodyValue);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return new InternalServerErrorHandler().handle(httpRequest);
            //return InternalServerErrorHandler
        }

    }

    /**
     * call doPost on teh slack api and reads response from it.
     * calls parseSlackResponse method to parse the json response sent by the slackAPI.
     * @param url
     * @param headers
     * @param body
     * @return
     */
    public static SlackResponse doPostOnSlack(String url, Map<String, String> headers, String body) {
        SlackResponse slackResponse = new SlackResponse();
        try {
            HttpRequest.Builder builder = HttpRequest.newBuilder(new URI(url));
            builder = setHeaders(builder, headers);
            HttpRequest request = builder.POST((HttpRequest.BodyPublishers.ofString(body))).build();
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            slackResponse = parseSlackResponse(response.body());
        } catch(URISyntaxException | IOException | InterruptedException e) {
            System.err.println(e.getMessage());
        }
        return slackResponse;
    }

    /**
     * Helper method to set the headers of any HttpRequest.Builder.
     * @param builder
     * @param headers
     * @return
     */
    private static HttpRequest.Builder setHeaders (HttpRequest.Builder builder, Map < String, String > headers){
        if (headers != null) {
            for (String key : headers.keySet()) {
                builder = builder.setHeader(key, headers.get(key));
            }
        }
        return builder;
    }

    /**
     * parses the json response sent from slackAPI.
     * @param slackPostResponse
     * @return
     */
    private static SlackResponse parseSlackResponse (String slackPostResponse) {
        Gson gson = new Gson();
        SlackResponse slackResponse = new SlackResponse();
        try {
            slackResponse = gson.fromJson(slackPostResponse, SlackResponse.class);
        }catch(JsonSyntaxException e) {
            //log
        }
        return slackResponse;
    }

    /**
     * reads from token config file and forms a token object.
     * @param tokenConfigFileName
     * @return
     */
    private Token tokenConfigFileReader(String tokenConfigFileName) {
        Gson gson = new Gson();
        Token tokenObject = null;
        try {
            tokenObject = gson.fromJson(new FileReader(tokenConfigFileName), Token.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return tokenObject;
    }

}

