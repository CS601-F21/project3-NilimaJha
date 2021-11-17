package chatApplicationTest;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import server.HTTPResponse;
import server.HTTPTestClient;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ChatApplicationTest {
    public static String PathNotFoundResponse404;
    public static String BadRequestResponse400;
    public static String MethodNotAllowedResponse405;

    @BeforeAll
    public static void TestInitializer() {
        PathNotFoundResponse404 = "<!DOCTYPE html>\n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                "<head>\n" +
                "  <title>Path Not Found</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "  <p>404 Path Not Found.</p>\n" +
                "\n" +
                "</body>\n" +
                "</html>";

        BadRequestResponse400 = "<!DOCTYPE html>\n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                "<head>\n" +
                "  <title>Bad Request</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "  <p>Bad Request!</p>\n" +
                "\n" +
                "</body>\n" +
                "</html>";

        MethodNotAllowedResponse405 = "<!DOCTYPE html>\n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                "<head>\n" +
                "  <title>Method Not Allowed</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "  <p>405 Method Not Allowed.</p>\n" +
                "\n" +
                "</body>\n" +
                "</html>";
    }

    //for /find
    @Test
    public void testSlackBotForGETWithCorrectPath() {
        String expectedResponse = "<!DOCTYPE html>\n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                "<head>\n" +
                "  <title>Slack Bot</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "<h1><u>Slack Bot</u></h1><form action=\"/slackbot\" method=\"post\">\n" +
                "  <label for=\"msg\"><b>Enter Message:</b></label><br/><br/>\n" +
                "  <input type=\"text\" id=\"message\" name=\"message\"/><br/><br/>\n" +
                "  <input type=\"submit\" value=\"Submit\"/>\n" +
                "</form>\n" +
                "</body>\n" +
                "</html>";

        HTTPResponse ActualHttpResponse = HTTPTestClient.doGet("http://localhost:9090/slackbot") ;
        assertEquals(200, ActualHttpResponse.getStatusCode());
        assertEquals(expectedResponse, ActualHttpResponse.getResponseMessage());
    }

    @Test
    public void testSlackBotForGETWithWrongPath1() {
        HTTPResponse ActualHttpResponse = HTTPTestClient.doGet("http://localhost:9090/");
        assertEquals(404, ActualHttpResponse.getStatusCode());
        assertEquals(PathNotFoundResponse404, ActualHttpResponse.getResponseMessage());
    }

    @Test
    public void testSlackBotForGETWithWrongPath2() {
        HTTPResponse ActualHttpResponse = HTTPTestClient.doGet("http://localhost:9090/slackbot?asin=abc");
        assertEquals(404, ActualHttpResponse.getStatusCode());
        assertEquals( PathNotFoundResponse404, ActualHttpResponse.getResponseMessage());
    }

    @Test
    public void testSlackBotForGETWithWrongPath3() {
        HTTPResponse ActualHttpResponse = HTTPTestClient.doGet("http://localhost:9090/SLACKBOT");
        assertEquals(404, ActualHttpResponse.getStatusCode());
        assertEquals( PathNotFoundResponse404, ActualHttpResponse.getResponseMessage());
    }

    @Test
    public void testSlackBotForPOSTWithWrongPath() {
        HashMap<String, String> headers = new HashMap<>();
        HTTPResponse ActualHttpResponse = HTTPTestClient.doPost("http://localhost:9090/slackboat",
                headers, "message=INCORRECT PATH");
        System.out.println(ActualHttpResponse);
        System.out.println(ActualHttpResponse.getStatusCode());
        assertEquals(404, ActualHttpResponse.getStatusCode());
        assertEquals( PathNotFoundResponse404, ActualHttpResponse.getResponseMessage());
    }

    @Test
    public void testSlackBotForMethodNotAllowed() {
        HashMap<String, String> headers = new HashMap<>();
        HTTPResponse ActualHttpResponse = HTTPTestClient.doDelete("http://localhost:9090/slackbot");
        assertEquals(405, ActualHttpResponse.getStatusCode());
        assertEquals( MethodNotAllowedResponse405, ActualHttpResponse.getResponseMessage());
    }

    @Test
    public void testSlackBotForPOSTWithCorrectPathIncorrectRequest1() {
        HashMap<String, String> headers = new HashMap<>();
        HTTPResponse ActualHttpResponse = HTTPTestClient.doPost("http://localhost:9090/slackbot",
                headers, "message = INCORRECT=REQUEST1");
        assertEquals(400, ActualHttpResponse.getStatusCode());
        assertEquals(BadRequestResponse400, ActualHttpResponse.getResponseMessage());
    }

    @Test
    public void testSlackBotForPOSTWithCorrectPathIncorrectRequest2() {
        HashMap<String, String> headers = new HashMap<>();
        HTTPResponse ActualHttpResponse = HTTPTestClient.doPost("http://localhost:9090/slackbot",
                headers, " message=INCORRECT=REQUEST2");
        assertEquals(400, ActualHttpResponse.getStatusCode());
        assertEquals(BadRequestResponse400, ActualHttpResponse.getResponseMessage());
    }

    @Test
    public void testSlackBotForPOSTWithCorrectPathIncorrectRequest3() {
        HashMap<String, String> headers = new HashMap<>();
        HTTPResponse ActualHttpResponse = HTTPTestClient.doPost("http://localhost:9090/slackbot",
                headers, " message~INCORRECT=REQUEST3");
        assertEquals(400, ActualHttpResponse.getStatusCode());
        assertEquals(BadRequestResponse400, ActualHttpResponse.getResponseMessage());
    }

    @Test
    public void testSlackBotForPOSTWithCorrectPathIncorrectRequest4() {
        HashMap<String, String> headers = new HashMap<>();
        HTTPResponse ActualHttpResponse = HTTPTestClient.doPost("http://localhost:9090/slackbot",
                headers, "mess=INCORRECT=REQUEST4");
        assertEquals(400, ActualHttpResponse.getStatusCode());
        assertEquals(BadRequestResponse400, ActualHttpResponse.getResponseMessage());
    }

    @Test
    public void testSlackBotForPOSTWithCorrectPathCorrectRequest() {
        String message = "Test message from ChatApplication Integration Test.";
        String expectedRepose = generateHTMLResponseForPOST(message);
        HashMap<String, String> headers = new HashMap<>();
        message = "message="+ message;
        HTTPResponse ActualHttpResponse = HTTPTestClient.doPost("http://localhost:9090/slackbot",
                headers, message);
        assertEquals(200, ActualHttpResponse.getStatusCode());
        assertEquals(expectedRepose, ActualHttpResponse.getResponseMessage());
    }

    private String generateHTMLResponseForPOST(String message) {
        String HTMLResponseMessage = "<!DOCTYPE html>\n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                "<head>\n" +
                "  <title>Slack Bot</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "<h1><u>Slack Bot</u></h1>" +
                "<p> MESSAGE <b>'" + message + "'</b> SENT SUCCESSFULLY ON SLACK CHANNEL!</p>" +
                "<form action=\"/slackbot\" method=\"post\">\n" +
                "  <label for=\"msg\"><b>Send another Message:</b></label><br/><br/>\n" +
                "  <input type=\"text\" id=\"message\" name=\"message\"/><br/><br/>\n" +
                "  <input type=\"submit\" value=\"Submit\"/>\n" +
                "</form>" +
                "\n" +
                "</body>\n" +
                "</html>";

        return HTMLResponseMessage;
    }

}
