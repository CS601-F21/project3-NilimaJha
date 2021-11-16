package chatApplicationTest;

import htmlTestUtil.HTMLValidator;
import org.junit.jupiter.api.Test;
import server.HTTPResponse;
import server.HTTPTestClient;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChatApplicationSystemTest {
    /**
     * test of status code received from doGet on slackbot with correct path.
     */
    @Test
    public void testStatusCodeForGETOnSlackbot1() {
        HTTPResponse httpResponse = HTTPTestClient.doGet("http://localhost:9090/slackbot") ;
        assertEquals(200, httpResponse.getStatusCode());
    }

    /**
     * test to validate the response body of doGet on slackbot with correct path.
     */
    @Test
    public void testToValidateResponseBodyFromGETOnSlackBot1() {
        HTTPResponse httpResponse = HTTPTestClient.doGet("http://localhost:9090/slackbot") ;
        assertEquals(true, HTMLValidator.isValid(httpResponse.getResponseMessage()));
    }

    /**
     * test of status code received from doGet on slackbot with incorrect path
     */
    @Test
    public void testStatusCodeForGETOnSlackbot2() {
        HTTPResponse httpResponse = HTTPTestClient.doGet("http://localhost:9090/");
        assertEquals(404, httpResponse.getStatusCode());
    }

    /**
     * test to validate the response body of doGet on slackbot with incorrect path.
     */
    @Test
    public void testToValidateResponseBodyFromGETOnSlackBot2() {
        HTTPResponse httpResponse = HTTPTestClient.doGet("http://localhost:9090/");
        assertEquals(true, HTMLValidator.isValid(httpResponse.getResponseMessage()));
    }


    /**
     * test of status code received from doGet on slackbot with incorrect path
     */
    @Test
    public void testStatusCodeForGETOnSlackbot3() {
        HTTPResponse httpResponse = HTTPTestClient.doGet("http://localhost:9090/slackbot?asin=abc");
        assertEquals(404, httpResponse.getStatusCode());
    }

    /**
     * test to validate the response body of doGet on slackbot with incorrect path.
     */
    @Test
    public void testToValidateResponseBodyFromGETOnSlackBot3() {
        HTTPResponse httpResponse = HTTPTestClient.doGet("http://localhost:9090/slackbot?asin=abc");
        assertEquals(true, HTMLValidator.isValid(httpResponse.getResponseMessage()));
    }

    /**
     * test of status code received from doGet on slackbot with path in upperCase.
     */
    @Test
    public void testStatusCodeForGETOnSlackbot4() {
        HTTPResponse httpResponse = HTTPTestClient.doGet("http://localhost:9090/SLACKBOT");
        assertEquals(404, httpResponse.getStatusCode());
    }

    /**
     * test to validate the response body of doGet on slackbot with path in upperCase.
     */
    @Test
    public void testToValidateResponseBodyFromGETOnSlackBot4() {
        HTTPResponse httpResponse = HTTPTestClient.doGet("http://localhost:9090/SLACKBOT");
        assertEquals(true, HTMLValidator.isValid(httpResponse.getResponseMessage()));
    }

    /**
     * test of status code received from doPost on slackbot with incorrect path.
     */
    @Test
    public void testStatusCodeForPOSTOnSlackbot1() {
        HashMap<String, String> headers = new HashMap<>();
        HTTPResponse httpResponse = HTTPTestClient.doPost("http://localhost:9090/slackboat",
                headers, "message=INCORRECT PATH");
        assertEquals(404, httpResponse.getStatusCode());

    }

    /**
     * test to validate the response body of doPost on slackbot with incorrect path.
     */
    @Test
    public void testToValidateResponseBodyFromPOSTOnSlack1() {
        HashMap<String, String> headers = new HashMap<>();
        HTTPResponse httpResponse = HTTPTestClient.doPost("http://localhost:9090/slackboat",
                headers, "message=INCORRECT PATH");
        assertEquals(true, HTMLValidator.isValid(httpResponse.getResponseMessage()));
    }

    /**
     * test of status code received from doDelete on slackbot with correct path.
     */
    @Test
    public void testStatusCodeOfSlackBotForMethodNotAllowed() {
        HTTPResponse httpResponse = HTTPTestClient.doDelete("http://localhost:9090/slackbot");
        assertEquals(405, httpResponse.getStatusCode());
    }

    /**
     * test to validate the response body of doDelete on slackbot with correct path.
     */
    @Test
    public void testToValidateResponseBodyForMethodNotAllowed() {
        HTTPResponse httpResponse = HTTPTestClient.doDelete("http://localhost:9090/slackbot");
        assertEquals(true, HTMLValidator.isValid(httpResponse.getResponseMessage()));
    }

    /**
     * test of status code received from doPost on slackbot with incorrect format of request payload.
     */
    @Test
    public void testStatusCodeForPOSTOnSlackbot2() {
        HashMap<String, String> headers = new HashMap<>();
        HTTPResponse httpResponse = HTTPTestClient.doPost("http://localhost:9090/slackbot",
                headers, "message = INCORRECT=REQUEST1");
        assertEquals(400, httpResponse.getStatusCode());
    }

    /**
     * test to validate the response body of doPost on slackbot with incorrect format of request payload.
     */
    @Test
    public void testToValidateResponseBodyFromPOSTOnSlack2() {
        HashMap<String, String> headers = new HashMap<>();
        HTTPResponse httpResponse = HTTPTestClient.doPost("http://localhost:9090/slackbot",
                headers, "message = INCORRECT=REQUEST1");
        assertEquals(true, HTMLValidator.isValid(httpResponse.getResponseMessage()));
    }

    /**
     * test of status code received from doPost on slackbot with incorrect format of request payload.
     * there is a space before key of the message response.
     */
    @Test
    public void testStatusCodeForPOSTOnSlackbot3() {
        HashMap<String, String> headers = new HashMap<>();
        HTTPResponse httpResponse = HTTPTestClient.doPost("http://localhost:9090/slackbot",
                headers, " message=INCORRECT=REQUEST2");
        assertEquals(400, httpResponse.getStatusCode());
    }

    /**
     * test to validate the response body of doPost on slackbot with incorrect format of request payload.
     * there is a space before key of the message response.
     */
    @Test
    public void testToValidateResponseBodyFromPOSTOnSlack3() {
        HashMap<String, String> headers = new HashMap<>();
        HTTPResponse httpResponse = HTTPTestClient.doPost("http://localhost:9090/slackbot",
                headers, " message=INCORRECT=REQUEST2");
        assertEquals(true, HTMLValidator.isValid(httpResponse.getResponseMessage()));
    }

    /**
     * test of status code received from doPost on slackbot with incorrect format of request payload.
     * in request payload in place of = tilde ~ symbol is used
     */
    @Test
    public void testStatusCodeForPOSTOnSlackbot4() {
        HashMap<String, String> headers = new HashMap<>();
        HTTPResponse httpResponse = HTTPTestClient.doPost("http://localhost:9090/slackbot",
                headers, " message~INCORRECT=REQUEST3");
        assertEquals(400, httpResponse.getStatusCode());
    }

    /**
     * test to validate the response body of doPost on slackbot with incorrect format of request payload.
     * in request payload in place of = tilde ~ symbol is used
     */
    @Test
    public void testToValidateResponseBodyFromPOSTOnSlack4() {
        HashMap<String, String> headers = new HashMap<>();
        HTTPResponse httpResponse = HTTPTestClient.doPost("http://localhost:9090/slackbot",
                headers, " message~INCORRECT=REQUEST3");
        assertEquals(true, HTMLValidator.isValid(httpResponse.getResponseMessage()));
    }

    /**
     * test of status code received from doPost on slackbot with incorrect key in request payload.
     */
    @Test
    public void testStatusCodeForPOSTOnSlackbot5() {
        HashMap<String, String> headers = new HashMap<>();
        HTTPResponse httpResponse = HTTPTestClient.doPost("http://localhost:9090/slackbot",
                headers, "mess=INCORRECT=REQUEST4");
        assertEquals(400, httpResponse.getStatusCode());
    }

    /**
     * test to validate the response body of doPost on slackbot with incorrect key in request payload.
     */
    @Test
    public void testToValidateResponseBodyFromPOSTOnSlack5() {
        HashMap<String, String> headers = new HashMap<>();
        HTTPResponse httpResponse = HTTPTestClient.doPost("http://localhost:9090/slackbot",
                headers, "mess=INCORRECT=REQUEST4");
        assertEquals(true, HTMLValidator.isValid(httpResponse.getResponseMessage()));
    }

    /**
     * test to check status code and to validate the response body of doPost on slackbot with
     * correct method, path, and payload.
     */
    @Test
    public void testSlackBotPOST() {
        String message = "Message from ChatAppSystemTest.";
        HashMap<String, String> headers = new HashMap<>();
        message = "message="+ message;
        HTTPResponse httpResponse = HTTPTestClient.doPost("http://localhost:9090/slackbot", headers, message);
        assertEquals(200, httpResponse.getStatusCode());
        assertEquals(true, HTMLValidator.isValid(httpResponse.getResponseMessage()));
    }
}
