package searchApplicationTest;

import htmlTestUtil.HTMLValidator;
import org.junit.jupiter.api.Test;
import server.HTTPResponse;
import server.HTTPTestClient;


import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SearchApplicationSystemTest {
    /**
     * test of status code of the response received from doGet on search application with correct path.
     */
    @Test
    public void testResponseStatusCodeFromFindAsinGET1() {
        HTTPResponse ActualHttpResponse = HTTPTestClient.doGet("http://localhost:8080/find") ;
        assertEquals(200, ActualHttpResponse.getStatusCode());
    }

    /**
     * test to validate the response body of doGet on search application with correct path.
     */
    @Test
    public void testResponseBodyFromFindAsinGET1() {
        HTTPResponse ActualHttpResponse = HTTPTestClient.doGet("http://localhost:8080/find") ;
        assertTrue(HTMLValidator.isValid(ActualHttpResponse.getResponseMessage()));
    }

    /**
     * test of status code of the response received from doGet on search application using with wrong path.
     */
    @Test
    public void testResponseStatusCodeFromFindAsinGET2() {
        HTTPResponse ActualHttpResponse = HTTPTestClient.doGet("http://localhost:8080/find?asin=abc");
        assertEquals(404, ActualHttpResponse.getStatusCode());
    }

    /**
     * test to validate the response body of doGet on search application with incorrect path.
     */
    @Test
    public void testResponseBodyFromFindAsinGET2() {
        HTTPResponse ActualHttpResponse = HTTPTestClient.doGet("http://localhost:8080/find?asin=abc");
        assertTrue(HTMLValidator.isValid(ActualHttpResponse.getResponseMessage()));
    }

    /**
     * test of status code of the response received from doGet by on search application with incorrect path.
     */
    @Test
    public void testResponseStatusCodeFromFindAsinGET3() {
        HTTPResponse ActualHttpResponse = HTTPTestClient.doGet("http://localhost:8080/search");
        assertEquals(404, ActualHttpResponse.getStatusCode());
    }

    /**
     * to validate the response body from doGet by on search application with incorrect path.
     */
    @Test
    public void testResponseBodyFromFindAsinGET3() {
        HTTPResponse ActualHttpResponse = HTTPTestClient.doGet("http://localhost:8080/search");
        assertTrue(HTMLValidator.isValid(ActualHttpResponse.getResponseMessage()));
    }

    /**
     * test of status code received from doPost on search application with incorrect path.
     */
    @Test
    public void testResponseStatusCodeFromFindAsinPOST1() {
        HashMap<String, String> headers = new HashMap<>();
        HTTPResponse ActualHttpResponse = HTTPTestClient.doPost("http://localhost:8080/finder",
                headers, "asin=120401325X");
        assertEquals(404, ActualHttpResponse.getStatusCode());
    }

    /**
     * to validate the response body from doPost on search application with incorrect path.
     */
    @Test
    public void testResponseBodyFromFindAsinPOST1() {
        HashMap<String, String> headers = new HashMap<>();
        HTTPResponse ActualHttpResponse = HTTPTestClient.doPost("http://localhost:8080/finder",
                headers, "asin=120401325X");
        assertTrue(HTMLValidator.isValid(ActualHttpResponse.getResponseMessage()));
    }

    /**
     * test of status code received from doDelete on search application with correct path.
     */
    @Test
    public void testResponseStatusCodeFromFindAsinDELETE() {
        HTTPResponse ActualHttpResponse = HTTPTestClient.doDelete("http://localhost:8080/find");
        assertEquals(405, ActualHttpResponse.getStatusCode());
    }

    /**
     * to validate the response body from doDelete on search application with correct path.
     */
    @Test
    public void testResponseBodyFromFindAsinDELETE() {
        HTTPResponse ActualHttpResponse = HTTPTestClient.doDelete("http://localhost:8080/find");
        assertTrue(HTMLValidator.isValid(ActualHttpResponse.getResponseMessage()));
    }

    /**
     * test of status code received from doPost on search application with correct path
     * and incorrect key of the request payload.
     */
    @Test
    public void testResponseStatusCodeFromFindAsinPOST2() {
        HashMap<String, String> headers = new HashMap<>();
        HTTPResponse ActualHttpResponse = HTTPTestClient.doPost("http://localhost:8080/find",
                headers, "asinNumber=120401325X");
        assertEquals(400, ActualHttpResponse.getStatusCode());
    }

    /**
     * to validate the response body from doPost on search application with correct path
     * and incorrect key of the request payload.
     */
    @Test
    public void testResponseBodyFromFindAsinPOST2() {
        HashMap<String, String> headers = new HashMap<>();
        HTTPResponse ActualHttpResponse = HTTPTestClient.doPost("http://localhost:8080/find",
                headers, "asinNumber=120401325X");
        assertTrue(HTMLValidator.isValid(ActualHttpResponse.getResponseMessage()));
    }

    /**
     * test of status code received from doPost on search application with correct path
     * and incorrect request payload.
     */
    @Test
    public void testResponseStatusCodeFromFindAsinPOST3() {
        HashMap<String, String> headers = new HashMap<>();
        HTTPResponse ActualHttpResponse = HTTPTestClient.doPost("http://localhost:8080/find",
                headers, "asin = 120401325X");
        assertEquals(400, ActualHttpResponse.getStatusCode());
    }

    /**
     * to validate the response body from doPost on search application with correct path
     * and incorrect request payload.
     */
    @Test
    public void testResponseBodyFromFindAsinPOST3() {
        HashMap<String, String> headers = new HashMap<>();
        HTTPResponse ActualHttpResponse = HTTPTestClient.doPost("http://localhost:8080/find",
                headers, "asin = 120401325X");
        assertTrue(HTMLValidator.isValid(ActualHttpResponse.getResponseMessage()));
    }

    /**
     * test of status code received from doPost on search application with correct path
     * and incorrect key of the request payload.
     */
    @Test
    public void testResponseStatusCodeFromFindAsinPOST4() {
        HashMap<String, String> headers = new HashMap<>();
        HTTPResponse ActualHttpResponse = HTTPTestClient.doPost("http://localhost:8080/find",
                headers, " asin=120401325X");
        assertEquals(400, ActualHttpResponse.getStatusCode());
    }

    /**
     * to validate the response body from doPost on search application with correct path
     * and incorrect key of the request payload.
     */
    @Test
    public void testResponseBodyFromFindAsinPOST4() {
        HashMap<String, String> headers = new HashMap<>();
        HTTPResponse ActualHttpResponse = HTTPTestClient.doPost("http://localhost:8080/find",
                headers, " asin=120401325X");
        assertTrue(HTMLValidator.isValid(ActualHttpResponse.getResponseMessage()));
    }

    /**
     * test of status code received from doPost on search application with correct path
     * and incorrect request payload format.
     * in place of "=" "-" is used in between key and value of request payload.
     */
    @Test
    public void testResponseStatusCodeFromFindAsinPOST5() {
        HashMap<String, String> headers = new HashMap<>();
        HTTPResponse ActualHttpResponse = HTTPTestClient.doPost("http://localhost:8080/find",
                headers, "asin-120401325X");
        assertEquals(400, ActualHttpResponse.getStatusCode());
    }

    /**
     * to validate the response body from doPost on search application with correct path
     * and incorrect request payload format.
     */
    @Test
    public void testResponseBodyFromFindAsinPOST5() {
        HashMap<String, String> headers = new HashMap<>();
        HTTPResponse ActualHttpResponse = HTTPTestClient.doPost("http://localhost:8080/find",
                headers, "asin-120401325X");
        assertTrue(HTMLValidator.isValid(ActualHttpResponse.getResponseMessage()));
    }

    /**
     * test of status code received from doPost on search application with correct path and
     * well-formed payload.
     */
    @Test
    public void testResponseStatusCodeFromFindAsinPOST6() {
        String testASIN = "120401325X";
        HashMap<String, String> headers = new HashMap<>();
        String body = "asin="+ testASIN;
        HTTPResponse ActualHttpResponse = HTTPTestClient.doPost("http://localhost:8080/find", headers, body);
        assertEquals(200, ActualHttpResponse.getStatusCode());
    }

    /**
     * to validate the response body from doPost on search application with correct path and
     * well-formed payload.
     */
    @Test
    public void testResponseBodyFromFindAsinPOST6() {
        String testASIN = "120401325X";
        HashMap<String, String> headers = new HashMap<>();
        String body = "asin="+ testASIN;
        HTTPResponse ActualHttpResponse = HTTPTestClient.doPost("http://localhost:8080/find", headers, body);
        assertTrue(HTMLValidator.isValid(ActualHttpResponse.getResponseMessage()));
    }

    /**
     * test of status code received from doPost on search application with correct path and
     * well-formed payload but invalid asin to test InvertedIndex.
     */
    @Test
    public void testResponseStatusCodeFromFindAsinPOST7() {
        String testASIN = "120401325$X";
        HashMap<String, String> headers = new HashMap<>();
        String body = "asin="+ testASIN;
        HTTPResponse ActualHttpResponse = HTTPTestClient.doPost("http://localhost:8080/find", headers, body);
        assertEquals(200, ActualHttpResponse.getStatusCode());
    }

    /**
     * to validate the response body from doPost on search application with correct path and
     * well-formed payload but invalid asin to test InvertedIndex.
     */
    @Test
    public void testResponseBodyFromFindAsinPOST7() {
        String testASIN = "120401325$X";
        HashMap<String, String> headers = new HashMap<>();
        String body = "asin="+ testASIN;
        HTTPResponse ActualHttpResponse = HTTPTestClient.doPost("http://localhost:8080/find", headers, body);
        assertTrue(HTMLValidator.isValid(ActualHttpResponse.getResponseMessage()));
    }

    /**
     * test of status code of the response received from doGet on search application with correct path.
     */
    @Test
    public void testResponseStatusCodeFromReviewSearchGET1() {
        HTTPResponse httpResponse = HTTPTestClient.doGet("http://localhost:8080/reviewsearch") ;
        assertEquals(200, httpResponse.getStatusCode());
    }

    /**
     * test to validate the response body received from doGet on search application with correct path.
     */
    @Test
    public void testResponseBodyFromReviewSearchGET1() {
        HTTPResponse httpResponse = HTTPTestClient.doGet("http://localhost:8080/reviewsearch") ;
        assertTrue(HTMLValidator.isValid(httpResponse.getResponseMessage()));
    }

    /**
     * test of status code of the response received from doGet on search application with incorrect path.
     */
    @Test
    public void testResponseStatusCodeFromReviewSearchGET2() {
        HTTPResponse httpResponse = HTTPTestClient.doGet("http://localhost:8080/");
        assertEquals(404, httpResponse.getStatusCode());
    }

    /**
     * test to validate the response body received from doGet on search application with incorrect path.
     */
    @Test
    public void testResponseBodyFromReviewSearchGET2() {
        HTTPResponse httpResponse = HTTPTestClient.doGet("http://localhost:8080/");
        assertTrue(HTMLValidator.isValid(httpResponse.getResponseMessage()));
    }

    /**
     * test of status code of the response received from doGet on search application with incorrect path.
     */
    @Test
    public void testResponseStatusCodeFromReviewSearchGET3() {
        HTTPResponse httpResponse = HTTPTestClient.doGet("http://localhost:8080/reviewsearch?query=computer");
        assertEquals(404, httpResponse.getStatusCode());
    }

    /**
     * test to validate the response body received from doGet on search application with incorrect path.
     */
    @Test
    public void testResponseBodyFromReviewSearchGET3() {
        HTTPResponse httpResponse = HTTPTestClient.doGet("http://localhost:8080/reviewsearch?query=computer");
        assertTrue(HTMLValidator.isValid(httpResponse.getResponseMessage()));
    }

    /**
     * test of status code of the response received from doGet on search application with incorrect path.
     */
    @Test
    public void testResponseStatusCodeFromReviewSearchPOST1() {
        HashMap<String, String> headers = new HashMap<>();
        HTTPResponse httpResponse = HTTPTestClient.doPost("http://localhost:8080/reviewsearcher", headers, "query=120401325X");
        assertEquals(404, httpResponse.getStatusCode());
    }

    /**
     * test to validate the response body received from doGet on search application with incorrect path.
     */
    @Test
    public void testResponseBodyFromReviewSearchPOST1() {
        HashMap<String, String> headers = new HashMap<>();
        HTTPResponse httpResponse = HTTPTestClient.doPost("http://localhost:8080/reviewsearcher", headers, "query=120401325X");
        assertTrue(HTMLValidator.isValid(httpResponse.getResponseMessage()));
    }

    /**
     * test of status code of the response received from doDelete on search application with correct path.
     */
    @Test
    public void testResponseStatusCodeFromReviewSearchDELETE() {
        HTTPResponse httpResponse = HTTPTestClient.doDelete("http://localhost:8080/reviewsearch");
        assertEquals(405, httpResponse.getStatusCode());
    }

    /**
     * test to validate the response body received from doDelete on search application with correct path.
     */
    @Test
    public void testResponseBodyFromReviewSearchDELETE() {
        HTTPResponse httpResponse = HTTPTestClient.doDelete("http://localhost:8080/reviewsearch");
        assertTrue(HTMLValidator.isValid(httpResponse.getResponseMessage()));
    }

    /**
     * test of status code of the response received from doPost on search application with correct path
     * but incorrect request payload key.
     */
    @Test
    public void testResponseStatusCodeFromReviewSearchPOST2() {
        HashMap<String, String> headers = new HashMap<>();
        HTTPResponse httpResponse = HTTPTestClient.doPost("http://localhost:8080/reviewsearch",
                headers, "review=computer");
        assertEquals(400, httpResponse.getStatusCode());
    }

    /**
     * test to validate the response body received from doPost on search application with correct path
     * but incorrect request payload key.
     */
    @Test
    public void testResponseBodyFromReviewSearchPOST2() {
        HashMap<String, String> headers = new HashMap<>();
        HTTPResponse httpResponse = HTTPTestClient.doPost("http://localhost:8080/reviewsearch",
                headers, "review=computer");
        assertTrue(HTMLValidator.isValid(httpResponse.getResponseMessage()));
    }

    /**
     * test of status code of the response received from doPost on search application with correct path
     * but incorrect request payload format.
     */
    @Test
    public void testResponseStatusCodeFromReviewSearchPOST3() {
        HashMap<String, String> headers = new HashMap<>();
        HTTPResponse httpResponse = HTTPTestClient.doPost("http://localhost:8080/reviewsearch",
                headers, "query-computer");
        assertEquals(400, httpResponse.getStatusCode());
    }

    /**
     * test to validate the response body received from doPost on search application with correct path
     * but incorrect request payload format.
     */
    @Test
    public void testResponseBodyFromReviewSearchPOST3() {
        HashMap<String, String> headers = new HashMap<>();
        HTTPResponse httpResponse = HTTPTestClient.doPost("http://localhost:8080/reviewsearch", headers, "query-computer");
        assertTrue(HTMLValidator.isValid(httpResponse.getResponseMessage()));
    }

    /**
     * test of status code of the response received from doPost on search application with correct path
     * but incorrect request payload format.
     */
    @Test
    public void testResponseStatusCodeFromReviewSearchPOST4() {
        HashMap<String, String> headers = new HashMap<>();
        HTTPResponse httpResponse = HTTPTestClient.doPost("http://localhost:8080/reviewsearch",
                headers, "query = computer");
        assertEquals(400, httpResponse.getStatusCode());
    }

    /**
     * test to validate the response body received from doPost on search application with correct path
     * but incorrect request payload format.
     */
    @Test
    public void testResponseBodyFromReviewSearchPOST4() {
        HashMap<String, String> headers = new HashMap<>();
        HTTPResponse httpResponse = HTTPTestClient.doPost("http://localhost:8080/reviewsearch", headers, "query = computer");
        assertTrue(HTMLValidator.isValid(httpResponse.getResponseMessage()));
    }

    /**
     * test of status code of the response received from doPost on search application with correct path
     * but incorrect request payload key.
     */
    @Test
    public void testResponseStatusCodeFromReviewSearchPOST5() {
        HashMap<String, String> headers = new HashMap<>();
        HTTPResponse httpResponse = HTTPTestClient.doPost("http://localhost:8080/reviewsearch",
                headers, " query=computer");
        assertEquals(400, httpResponse.getStatusCode());
    }

    /**
     * test to validate the response body received from doPost on search application with correct path
     * but incorrect request payload key.
     */
    @Test
    public void testResponseBodyFromReviewSearchPOST5() {
        HashMap<String, String> headers = new HashMap<>();
        HTTPResponse httpResponse = HTTPTestClient.doPost("http://localhost:8080/reviewsearch", headers, " query=computer");
        assertTrue(HTMLValidator.isValid(httpResponse.getResponseMessage()));
    }

    /**
     * test of status code of the response received from doPost on search application with correct path
     * and correct request payload.
     */
    @Test
    public void testResponseStatusCodeFromReviewSearchPOST6() {
        HashMap<String, String> headers = new HashMap<>();
        HTTPResponse httpResponse = HTTPTestClient.doPost("http://localhost:8080/reviewsearch", headers, "query=computer");
        assertEquals(200, httpResponse.getStatusCode());
    }

    /**
     * test to validate the response body received from doPost on search application with correct path
     * and correct request payload.
     */
    @Test
    public void testResponseBodyFromReviewSearchPOST6() {
        HashMap<String, String> headers = new HashMap<>();
        HTTPResponse httpResponse = HTTPTestClient.doPost("http://localhost:8080/reviewsearch", headers, "query=computer");
        assertTrue(HTMLValidator.isValid(httpResponse.getResponseMessage()));
    }
}
