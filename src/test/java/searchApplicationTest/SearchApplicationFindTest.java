package serverTest;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import searchApplication.invertedIndex.FileDataOps;
import searchApplication.invertedIndex.FileProcessor;

import server.*;
import server.httpDefaultHandlers.MethodNotAllowedHandler;
import server.httpDefaultHandlers.PathNotFoundHandler;

import java.io.IOException;
import java.util.HashMap;


public class HTTPServerTest {
    public static FileDataOps fileOps;
    public static String PathNotFoundResponse404;
    public static String BadRequestResponse400;
    public static String MethodNotAllowedResponse405;

    @BeforeAll
    public static void InitializeInvertedIndex() {
        FileProcessor fileProcessor = new FileProcessor();
        try {
            fileProcessor.reviewFileProcessor("review_Cell_Phones_and_Accessories.json");
            fileProcessor.qaFileProcessor("qa_Cell_Phones_and_Accessories_5.json");
        } catch (IOException e) {
            System.out.println("IOException occurred while reading from input files.");
            System.out.println("IOException." + e);
            System.exit(0);
        }
        fileOps = new FileDataOps();

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
    public void testFindAsinForGETWithCorrectPath() {
        String expectedResponse = "<!DOCTYPE html>\n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                "<head>\n" +
                "  <title>Find ASIN</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "<h1><u>Find ASIN</u></h1>" +
                "<form action=\"/find\" method=\"post\">\n" +
                "  <label for=\"msg\"><b>Enter ASIN:</b></label><br/><br/>\n" +
                "  <input type=\"text\" id=\"asin\" name=\"asin\"/><br/><br/>\n" +
                "  <input type=\"submit\" value=\"Submit\"/>\n" +
                "</form>" +
                "\n" +
                "</body>\n" +
                "</html>";

        HTTPResponse ActualHttpResponse = HTTPTestClient.doGet("http://localhost:8080/find") ;
        assertEquals(200, ActualHttpResponse.getStatusCode());
        assertEquals(expectedResponse, ActualHttpResponse.getResponseMessage());
    }

    @Test
    public void testFindAsinForGETWithWrongPath1() {
        HTTPResponse ActualHttpResponse = HTTPTestClient.doGet("http://localhost:8080/");
        assertEquals(404, ActualHttpResponse.getStatusCode());
        assertEquals(PathNotFoundResponse404, ActualHttpResponse.getResponseMessage());
    }

    @Test
    public void testFindAsinForGETWithWrongPath2() {
        HTTPResponse ActualHttpResponse = HTTPTestClient.doGet("http://localhost:8080/find?asin=abc");
        assertEquals(404, ActualHttpResponse.getStatusCode());
        assertEquals( PathNotFoundResponse404, ActualHttpResponse.getResponseMessage());
    }

    @Test
    public void testFindAsinForGETWithWrongPath3() {
        HTTPResponse ActualHttpResponse = HTTPTestClient.doGet("http://localhost:8080/search");
        assertEquals(404, ActualHttpResponse.getStatusCode());
        assertEquals( PathNotFoundResponse404, ActualHttpResponse.getResponseMessage());
    }


    @Test
    public void testFindAsinForPOSTWithWrongPath() {
        HashMap<String, String> headers = new HashMap<>();
        HTTPResponse ActualHttpResponse = HTTPTestClient.doPost("http://localhost:8080/finder", headers, "asin=120401325X");
        System.out.println(ActualHttpResponse);
        System.out.println(ActualHttpResponse.getStatusCode());
        assertEquals(404, ActualHttpResponse.getStatusCode());
        assertEquals( PathNotFoundResponse404, ActualHttpResponse.getResponseMessage());
    }

    @Test
    public void testFindAsinForMethodNotAllowed() {
        HashMap<String, String> headers = new HashMap<>();
        HTTPResponse ActualHttpResponse = HTTPTestClient.doDelete("http://localhost:8080/find");
        assertEquals(405, ActualHttpResponse.getStatusCode());
        assertEquals( MethodNotAllowedResponse405, ActualHttpResponse.getResponseMessage());
    }

    @Test
    public void testFindAsinForPOSTWithCorrectPathIncorrectRequest() {
        HashMap<String, String> headers = new HashMap<>();
        HTTPResponse ActualHttpResponse = HTTPTestClient.doPost("http://localhost:8080/find", headers, "asinNumber=120401325X");
        assertEquals(400, ActualHttpResponse.getStatusCode());
        assertEquals(BadRequestResponse400, ActualHttpResponse.getResponseMessage());
    }

    @Test
    public void testFindAsinForPOSTWithCorrectPathCorrectRequest() {
        String expectedRepose = generateHTMLResponseForPOST("Find ASIN", fileOps.findAsin("120401325X") );
        HashMap<String, String> headers = new HashMap<>();
        HTTPResponse ActualHttpResponse = HTTPTestClient.doPost("http://localhost:8080/find", headers, "asin=120401325X");
        assertEquals(200, ActualHttpResponse.getStatusCode());
        assertEquals(expectedRepose, ActualHttpResponse.getResponseMessage());
    }

    private String generateHTMLResponseForPOST(String title, String body) {
        String HTMLResponseMessage = "<!DOCTYPE html>\n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                "<head>\n" +
                "<title>" + title + " </title>\n" +
                "<h2><u>" + title + " </u></h2>\n" +
                "</head>\n" +
                "<body>\n" +
                "<p>" ;

        String[] lines = body.split("\n");
        for(String line: lines){
            HTMLResponseMessage += line + "<br/>" ;
        }

        HTMLResponseMessage += "</p>\n" +
                "</body>\n" +
                "</html>";

        return HTMLResponseMessage;
    }







}
