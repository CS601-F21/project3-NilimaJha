package server;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

public class HTTPTestClient {


    /**
     * Execute an HTTP GET for the specified URL and return the
     * body of the response as a String.
     * @param url
     * @return
     */
    public static HTTPResponse doGet(String url) {
        return doGet(url, null);
    }


    public static HTTPResponse doGet(String url, Map<String, String> headers) {
        try {
            HttpRequest.Builder builder = HttpRequest.newBuilder(new URI(url));
            builder = setHeaders(builder, headers);
            HttpRequest request = builder.GET()
                    .build();
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = null;
            try {
                response = client.send(request, HttpResponse.BodyHandlers.ofString());
            } catch (IOException e) {
                e.printStackTrace();
            }

            HTTPResponse expectedHttpResponse = new HTTPResponse("HTTP/1.1", response.statusCode(),
                    statusMessage(response.statusCode()));
            expectedHttpResponse.setResponseMessage(response.body());

            return expectedHttpResponse;
        } catch(URISyntaxException | InterruptedException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    /**
     * Helper method to set the headers of any HttpRequest.Builder.
     * @param builder
     * @param headers
     * @return
     */
    private static HttpRequest.Builder setHeaders(HttpRequest.Builder builder, Map<String, String> headers) {
        if(headers != null) {
            for (String key : headers.keySet()) {
                builder = builder.setHeader(key, headers.get(key));
            }
        }
        return builder;
    }

    /**
     * Execute an HTTP POST for the specified URL and return the body of the
     * response as a String.
     * Headers for the request are provided in the map headers.
     * The body of the request is provided as a String.
     *
     * @param url
     * @param headers
     * @param body
     * @return
     */
    public static HTTPResponse doPost(String url, Map<String, String> headers, String body) {

        try {
            HttpRequest.Builder builder = HttpRequest.newBuilder(new URI(url));
            builder = setHeaders(builder, headers);
            HttpRequest request = builder.POST((HttpRequest.BodyPublishers.ofString(body)))
                    .build();

            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            //System.out.println(response);

            HTTPResponse httpResponse = new HTTPResponse(HTTPConstants.PROTOCOL, response.statusCode(), statusMessage(response.statusCode()));
            httpResponse.setResponseMessage(response.body());
            return httpResponse;

        } catch(URISyntaxException | IOException | InterruptedException e) {
            System.err.println(e.getMessage());
            return null;
        }

    }

    public static String statusMessage(int statusCode) {
        if (statusCode == 200) {
            return HTTPConstants.MESSAGE_OK;
        } else if (statusCode == 500) {
            return HTTPConstants.MESSAGE_SERVER_ERROR;
        } else if (statusCode == 405) {
            return HTTPConstants.MESSAGE_NOT_ALLOWED;
        } else if (statusCode == 404) {
            return HTTPConstants.MESSAGE_NOT_FOUND;
        } else if (statusCode == 400) {
            return HTTPConstants.MESSAGE_BAD_REQUEST;
        } else {
            return null;
        }
    }

    /**
     * Execute an HTTP GET for the specified URL and return the
     * body of the response as a String.
     * @param url
     * @return
     */
    public static HTTPResponse doDelete(String url) {
        return doDelete(url, null);
    }
    public static HTTPResponse doDelete(String url, Map<String, String> headers) {
        try {
            HttpRequest.Builder builder = HttpRequest.newBuilder(new URI(url));
            builder = setHeaders(builder, headers);
            HttpRequest request = builder.DELETE()
                    .build();
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = null;
            try {
                response = client.send(request, HttpResponse.BodyHandlers.ofString());
            } catch (IOException e) {
                e.printStackTrace();
            }

            HTTPResponse expectedHttpResponse = new HTTPResponse("HTTP/1.1", response.statusCode(),
                    statusMessage(response.statusCode()));
            expectedHttpResponse.setResponseMessage(response.body());

            return expectedHttpResponse;
        } catch(URISyntaxException | InterruptedException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

}
