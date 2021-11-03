package server;

import server.httpDefaultHandlers.BadRequestHandler;
import handler.Handler;
import server.httpDefaultHandlers.PathNotFoundHandler;
import server.httpDefaultHandlers.MethodNotAllowedHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import java.io.*;
import java.net.Socket;
import java.util.HashMap;

/**
 * HTTPConnection class implements Runnable
 * @author nilimajha
 */
public class HTTPConnection implements Runnable {
    private static final Logger LOGGER = (Logger) LogManager.getLogger(HTTPConnection.class);

    private Socket connectionChannel;
    private HashMap<String, Handler> pathToHandlerMap;
    private String validPostQueryKey;

    /**
     * Constructor sets the value of connectionChannel and pathToHandlerMap.
     * @param connectionChannel
     * @param pathToHandlerMap
     */
    public HTTPConnection(Socket connectionChannel, HashMap<String, Handler>pathToHandlerMap) {
        this.connectionChannel = connectionChannel;
        this.pathToHandlerMap = pathToHandlerMap;
        LOGGER.info("New Connection Object initialized for Client : " + connectionChannel);
    }

    /**
     * thread handles the request from client and stores it in HTTPRequest obj
     * and handles it to appropriate handler and
     * returns the HTTPResponse to the client
     */
    @Override
    public void run() {
        String threadId = Thread.currentThread().getName();
        LOGGER.info("[Thread # " + threadId + "]: "+ "Request execution started.....");
        try (
                BufferedReader inStreamReader = new BufferedReader
                        (new InputStreamReader
                        (connectionChannel.getInputStream()));
                PrintWriter outStreamWriter = new PrintWriter
                        (connectionChannel.getOutputStream())) {
            HTTPRequest httpRequest = readRequest(inStreamReader);
            System.out.println("[Thread # " + threadId + "]: "+ "Returned to run method on HTTPConnection class and now assigning request to appropriate handler.");
            pathHandlerAssigner(httpRequest, outStreamWriter);
        } catch (IOException e) {
            LOGGER.error("[Thread # " + threadId + "]: " + "IN CATCH IOException: " + e);
        } catch (Exception e) {
            LOGGER.error("[Thread # " + threadId + "]: " + "IN CATCH Exception: " + e);
        }
    }

    /**
     * reads the request sent from client side
     * @param instream
     * @return httpRequest
     * @throws IOException
     */
    public HTTPRequest readRequest(BufferedReader instream) throws IOException {
        String threadId = Thread.currentThread().getName();
        LOGGER.info("[Thread # " + threadId + "]: " + "Reading Request of Client :" + this.connectionChannel);
        System.out.println("[Thread # " + threadId + "]: "+ "Inside ReadRequest method.");
        HTTPRequest httpRequest = new HTTPRequest();
        String[] HTTPRequestLineArray;
        String currentLine;

        currentLine = instream.readLine();
        LOGGER.info("Request Line :" + currentLine);
        System.out.println("[There Thread # " + threadId + "]: "+  currentLine);

        if(currentLine != null){
            HTTPRequestLineArray = currentLine.split("\\s");
            System.out.println("[Thread # " + threadId + "]: "+ "=========");
            for(String s : HTTPRequestLineArray){
                System.out.println("[Thread # " + threadId + "]: "+ s);
            }
            System.out.println("[Thread # " + threadId + "]: "+ "=========");

            //validating request and extracting required info.
            if(requestLineValidator(HTTPRequestLineArray)) {
                httpRequest.setMethod(HTTPRequestLineArray[0]);
                httpRequest.setPath(HTTPRequestLineArray[1]);
                httpRequest.setProtocol(HTTPRequestLineArray[2]);
            } else {
                httpRequest.setIsValid(false);
            }

            currentLine = instream.readLine();
            while (currentLine != null && currentLine.length() != 0 ) {
                System.out.println("[Thread # " + threadId + "]: " +  "FOR current HEADER LINE>>: " + currentLine);
                String[] HTTPRequestHeader = currentLine.split(":\\s");
                if (requestHeaderLineValidator(HTTPRequestHeader)) {
                    httpRequest.putRequestHeaders(HTTPRequestHeader[0], HTTPRequestHeader[1]);
                } else {
                    httpRequest.setIsValid(false);
                }
                currentLine = instream.readLine();
            }

            if (httpRequest.getRequestHeaders().containsKey("Content-Length")) {
                int contentLength = Integer.parseInt (httpRequest.getRequestHeaders().get("Content-Length"));
                LOGGER.info("Request contain Message of length : " + contentLength);
                System.out.println("[ Thread # "+ threadId +" ]: Inside readRequest method of class HTTPConnection and payload length : " + contentLength);
                char[] bodyArray = new char[contentLength];
                instream.read (bodyArray, 0, bodyArray.length);
                httpRequest.setRequestPayload(new String (bodyArray));
                System.out.println("[ Thread # " + threadId + " ]: Inside readRequest method of class HTTPConnection and payload value is: "+httpRequest.getRequestPayload());
                LOGGER.info("Request Message Body :" + httpRequest.getRequestPayload());
                //System.out.println("Request Message Body :" + httpRequest.getRequestPayload());
            }
        } else {
            httpRequest.setIsValid(false);
        }
        return httpRequest;
    }

    /**
     * handles the request to the appropriate Handler from pathToMapHandler.
     * @param httpRequest
     * @param writer
     */
    public void pathHandlerAssigner(HTTPRequest httpRequest, PrintWriter writer) {
        String threadId = Thread.currentThread().getName();
        HTTPResponse httpResponse;
        if (!httpRequest.getIsValid()) {
            LOGGER.info("Request is not Valid!");
            LOGGER.info("Assigning the request to BadRequestHandler.\"");
            httpResponse = new BadRequestHandler().handle(httpRequest);
        } else {
            String method = httpRequest.getMethod();
            String path = httpRequest.getPath();
            if (path.equals("/find")) {
                this.validPostQueryKey = "asin=";
            } else if (path.equals("/reviewsearch")) {
                this.validPostQueryKey = "query=" ;
            } else if(path.equals("/slackbot")) {
                this.validPostQueryKey = "message=" ;
            }

            if(!(method.equals(HTTPConstants.GET) || method.equals(HTTPConstants.POST))) {
                LOGGER.info("Request Method (" + method + ") is not supported!");
                LOGGER.info("Assigning the request to MethodNotAllowedHandler.");
                httpResponse = new MethodNotAllowedHandler().handle(httpRequest);
            } else if (method.equals(HTTPConstants.POST) && !requestPayloadValidator(httpRequest.getRequestPayload())) {
                System.out.println("[Thread # " + threadId + "]: "+ "********2");

                String httpRequestPayload = httpRequest.getRequestPayload();
                String query = httpRequestPayload.substring(0,httpRequestPayload.indexOf("=") + 1);
                LOGGER.info("Request Query (" + httpRequestPayload + ")is not valid!");
                LOGGER.info("Assigning the request to FileNotFoundHandler.");
                httpResponse = new PathNotFoundHandler().handle(httpRequest);
            } else if (!pathToHandlerMap.containsKey(path)) {
                System.out.println("[Thread # " + threadId + "]: "+ "********3");

                LOGGER.info("Request Path (" + path + ") is not valid!");
                LOGGER.info("Assigning the request to FileNotFoundHandler.");
                httpResponse = new PathNotFoundHandler().handle(httpRequest);
            } else {
                System.out.println("[Thread # " + threadId + "]: "+ "********4");
                LOGGER.info("Request is valid!");
                httpResponse = pathToHandlerMap.get(path).handle(httpRequest);
            }
        }
        System.out.println("[Thread # " + threadId + "]: "+ "********5 out");
        //testing if the response in valid i.e. correctly formatted.
        LOGGER.info("Sending Response to the Client!");
        LOGGER.info("Response to be sent : ");
        LOGGER.info(httpResponse.getHTTPResponse());
        writer.write(httpResponse.getHTTPResponse());
    }

    /**
     * validate requestLine and returns true of false accordingly.
     * @param HTTPRequestArray
     * @return true or false
     */
    public boolean requestLineValidator (String[] HTTPRequestArray) {
        if (HTTPRequestArray.length == 3 && HTTPRequestArray[2].equals(HTTPConstants.PROTOCOL)) {
            return true;
        }
        return false;
    }

    /**
     * validate the requestLine validator and
     * returns true or false accordingly.
     * @param HTTPRequestHeader
     * @return true or false
     */
    public boolean requestHeaderLineValidator (String[] HTTPRequestHeader) {
        if (HTTPRequestHeader.length == 2) {
            return true;
        }
        return false;
    }

    /**
     * validates the request payload and returns
     * true or false accordingly.
     * @param httpRequestMessage
     * @return true or false
     */
    private boolean requestPayloadValidator (String httpRequestMessage) {
        if (httpRequestMessage.contains("=")) {
            String query = httpRequestMessage.substring(0,httpRequestMessage.indexOf("=") + 1);
            String requestTerm = httpRequestMessage.substring(httpRequestMessage.indexOf("=") + 1);
            if (queryIsValid(query) && requestTerm.length() != 0 ) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * validate the query
     * @param query
     * @return true or false
     */
    private boolean queryIsValid (String query) {
        if (this.validPostQueryKey.equals(query)) {
            return true;
        } else {
            return false;
        }
    }

}
