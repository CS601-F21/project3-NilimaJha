package searchApplicationTest;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import searchApplication.invertedIndex.FileDataOps;
import searchApplication.invertedIndex.FileProcessor;
import server.HTTPResponse;
import server.HTTPTestClient;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class SearchApplicationReviewSearchTest {
    public static FileDataOps fileOps;
    public static String PathNotFoundResponse404;
    public static String BadRequestResponse400;
    public static String MethodNotAllowedResponse405;

    @BeforeAll
    public static void InitializeInvertedIndex() {
        FileProcessor fileProcessor = new FileProcessor();
        try {
            fileProcessor.reviewFileProcessor("review_Cell_Phones_and_Accessories_smaller.json");
            fileProcessor.qaFileProcessor("qa_Cell_Phones_and_Accessories_5_smaller.json");
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
    public void testReviewSearchForGETWithCorrectPath() {
        String expectedResponse = "<!DOCTYPE html>\n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                "<head>\n" +
                "  <title>Review Search</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "<h1><u>Review Search</u></h1><form action=\"/reviewsearch\" method=\"post\">\n" +
                "  <label for=\"msg\"><b>Enter Term:</b></label><br/><br/>\n" +
                "  <input type=\"text\" id=\"query\" name=\"query\"/><br/><br/>\n" +
                "  <input type=\"submit\" value=\"Submit\"/>\n" +
                "</form>\n" +
                "</body>\n" +
                "</html>";

        HTTPResponse ActualHttpResponse = HTTPTestClient.doGet("http://localhost:8080/reviewsearch") ;
        assertEquals(200, ActualHttpResponse.getStatusCode());
        assertEquals(expectedResponse, ActualHttpResponse.getResponseMessage());
    }

    @Test
    public void testReviewSearchForGETWithWrongPath1() {
        HTTPResponse ActualHttpResponse = HTTPTestClient.doGet("http://localhost:8080/");
        assertEquals(404, ActualHttpResponse.getStatusCode());
        assertEquals(PathNotFoundResponse404, ActualHttpResponse.getResponseMessage());
    }

    @Test
    public void testReviewSearchForGETWithWrongPath2() {
        HTTPResponse ActualHttpResponse = HTTPTestClient.doGet("http://localhost:8080/reviewsearch?query=computer");
        assertEquals(404, ActualHttpResponse.getStatusCode());
        assertEquals( PathNotFoundResponse404, ActualHttpResponse.getResponseMessage());
    }

    @Test
    public void testReviewSearchForGETWithWrongPath3() {
        HTTPResponse ActualHttpResponse = HTTPTestClient.doGet("http://localhost:8080/search");
        assertEquals(404, ActualHttpResponse.getStatusCode());
        assertEquals( PathNotFoundResponse404, ActualHttpResponse.getResponseMessage());
    }


    @Test
    public void testReviewSearchForPOSTWithWrongPath() {
        HashMap<String, String> headers = new HashMap<>();
        HTTPResponse ActualHttpResponse = HTTPTestClient.doPost("http://localhost:8080/reviewsearcher", headers, "query=120401325X");
        System.out.println(ActualHttpResponse);
        System.out.println(ActualHttpResponse.getStatusCode());
        assertEquals(404, ActualHttpResponse.getStatusCode());
        assertEquals( PathNotFoundResponse404, ActualHttpResponse.getResponseMessage());
    }

    @Test
    public void testReviewSearchForMethodNotAllowed() {
        HashMap<String, String> headers = new HashMap<>();
        HTTPResponse ActualHttpResponse = HTTPTestClient.doDelete("http://localhost:8080/reviewsearch");
        assertEquals(405, ActualHttpResponse.getStatusCode());
        assertEquals( MethodNotAllowedResponse405, ActualHttpResponse.getResponseMessage());
    }

    @Test
    public void testReviewSearchForPOSTWithCorrectPathIncorrectRequest1() {
        HashMap<String, String> headers = new HashMap<>();
        HTTPResponse ActualHttpResponse = HTTPTestClient.doPost("http://localhost:8080/reviewsearch", headers, "review=computer");
        assertEquals(400, ActualHttpResponse.getStatusCode());
        assertEquals(BadRequestResponse400, ActualHttpResponse.getResponseMessage());
    }

    @Test
    public void testReviewSearchForPOSTWithCorrectPathIncorrectRequest2() {
        HashMap<String, String> headers = new HashMap<>();
        HTTPResponse ActualHttpResponse = HTTPTestClient.doPost("http://localhost:8080/reviewsearch", headers, "query-computer");
        assertEquals(400, ActualHttpResponse.getStatusCode());
        assertEquals(BadRequestResponse400, ActualHttpResponse.getResponseMessage());
    }

    @Test
    public void testReviewSearchForPOSTWithCorrectPathIncorrectRequest3() {
        HashMap<String, String> headers = new HashMap<>();
        HTTPResponse ActualHttpResponse = HTTPTestClient.doPost("http://localhost:8080/reviewsearch", headers, "query = computer");
        assertEquals(400, ActualHttpResponse.getStatusCode());
        assertEquals(BadRequestResponse400, ActualHttpResponse.getResponseMessage());
    }

    @Test
    public void testReviewSearchForPOSTWithCorrectPathIncorrectRequest4() {
        HashMap<String, String> headers = new HashMap<>();
        HTTPResponse ActualHttpResponse = HTTPTestClient.doPost("http://localhost:8080/reviewsearch", headers, " query=computer");
        assertEquals(400, ActualHttpResponse.getStatusCode());
        assertEquals(BadRequestResponse400, ActualHttpResponse.getResponseMessage());
    }

    @Test
    public void testReviewSearchForPOSTWithCorrectPathCorrectRequest() {
        String expectedRepose = generateHTMLResponseForPOST("Review Search", fileOps.reviewSearch("computer") );
        HashMap<String, String> headers = new HashMap<>();
        HTTPResponse ActualHttpResponse = HTTPTestClient.doPost("http://localhost:8080/reviewsearch", headers, "query=computer");
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
