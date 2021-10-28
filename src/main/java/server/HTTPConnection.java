package server;

import server.httpDefaultHandlers.BadRequestHandler;
import handler.Handler;
import server.httpDefaultHandlers.MethodNotAllowedHandler;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;

public class HTTPConnection implements Runnable {
    private static final Logger LOGGER = LogManager.getLogger(HTTPConnection.class);

    private Socket connectionChannel;
    private HashMap<String, Handler> pathToHandlerMap;

    public HTTPConnection(Socket connectionChannel, HashMap<String, Handler>pathToHandlerMap) {
        this.connectionChannel = connectionChannel;
        this.pathToHandlerMap = pathToHandlerMap;
        LOGGER.info("New Connection Object initialized for Client : " + connectionChannel);
//        System.out.println("Inside Constructor of connection class.");
    }

    @Override
    public void run() {
        String threadId = Thread.currentThread().getName();
        LOGGER.info("[Thread # " + threadId + "]: "+ "Request execution started.....");
//        System.out.println("[Thread # " + threadId + "]: "+ "Request execution started.....");
        try (
                BufferedReader inStreamReader = new BufferedReader
                        (new InputStreamReader
                        (connectionChannel.getInputStream()));
                PrintWriter outStreamWriter = new PrintWriter
                        (connectionChannel.getOutputStream())) {
//            System.out.println("[Thread # " + threadId + "]: "+ "inputStreamReader and outputStreamReader initialized.");
            HTTPRequest httpRequest = readRequest(inStreamReader);
            pathHandlerAssigner(httpRequest, outStreamWriter);
        } catch (IOException e) {
            LOGGER.error("[Thread # " + threadId + "]: " + "IN CATCH IOException: " + e);
//            System.out.println("[Thread # " + threadId + "]: "+ "IN CATCH IOException: " + e);
//            e.printStackTrace();
        } catch (Exception e) {
            LOGGER.error("[Thread # " + threadId + "]: " + "IN CATCH Exception: " + e);
//            System.out.println("[Thread # " + threadId + "]: "+ "IN EXCEPT CATCH :" + e);
//            e.printStackTrace();
        }
    }


    public HTTPRequest readRequest(BufferedReader instream) throws IOException {
        String threadId = Thread.currentThread().getName();
        LOGGER.info("Reading Request of Client :" + this.connectionChannel);
//        LOGGER.info("[Thread # " + threadId + "]: Inside readRequest method.");
//        System.out.println("[Thread # " + threadId + "]: "+ "Inside ReadRequest method.");
        HTTPRequest httpRequest = new HTTPRequest();
        String[] HTTPRequestLineArray;
        String currentLine;
        String[] previousLine;

        currentLine = instream.readLine();
        LOGGER.info("Request Line :" + currentLine);
//        LOGGER.info("[Thread # " + threadId + "]: "+ currentLine);
//        System.out.println("[Here Thread # " + threadId + "]: "+  currentLine);
//        if(currentLine == null){
//            // if first line is null skipping that line.
//            currentLine = instream.readLine();
//        }
//        System.out.println("[There Thread # " + threadId + "]: "+  currentLine);

        if(currentLine != null){

            HTTPRequestLineArray = currentLine.split("\\s");
//            System.out.println("[Thread # " + threadId + "]: "+ "=========");
//            for(String s : HTTPRequestLineArray){
//                System.out.println("[Thread # " + threadId + "]: "+ s);
//            }
//            System.out.println("[Thread # " + threadId + "]: "+ "=========");

            //validating request and extracting required info.
            if(requestLineValidator(HTTPRequestLineArray)) {
                httpRequest.setMethod(HTTPRequestLineArray[0]);
                httpRequest.setPath(HTTPRequestLineArray[1]);
                httpRequest.setProtocol(HTTPRequestLineArray[2]);
            } else {
                httpRequest.setIsValid(false);
            }
            previousLine = HTTPRequestLineArray;
            currentLine = instream.readLine();
            while (currentLine != null && currentLine.length() != 0 ) {
//                System.out.println("[Thread # " + threadId + "]: " +  "FOR current HEADER LINE>>: " + currentLine);
                String[] HTTPRequestHeader = currentLine.split(":\\s");
                if (requestHeaderLineValidator(HTTPRequestHeader)) {
                    httpRequest.putRequestHeaders(HTTPRequestHeader[0], HTTPRequestHeader[1]);
                } else {
                    httpRequest.setIsValid(false);
                }
                previousLine = HTTPRequestHeader;
                currentLine = instream.readLine();
            }
//            System.out.println("[Thread # " + threadId + "]: "+  "FOR LAST LINE: " + currentLine);
            // checking if previousLine was content-length line
            if (previousLine[0].equals("Content-length:")) {
                int contentLength = Integer.parseInt (previousLine[1]);
                LOGGER.info("Request contain Message of length : " + contentLength);
                char[] bodyArray = new char[contentLength];
                instream.read (bodyArray, 0, bodyArray.length);
                httpRequest.setMessage(new String (bodyArray));
                LOGGER.info("Request Message Body :" + httpRequest.getMessage());
            }
        } else {
//            System.out.println("[Thread # " + threadId + "]: "
//                    + "REQUEST FIRST LINE IS NULL -> should contain method/path/protocol");
            httpRequest.setIsValid(false);
        }
        return httpRequest;
    }


    public void pathHandlerAssigner(HTTPRequest httpRequest, PrintWriter writer) {
//        String threadId = Thread.currentThread().getName();
//        LOGGER.info("[Thread # " + threadId + "]: "+ "Assigning appropriate Handler.");
//        System.out.println("[Thread # " + threadId + "]: "+ "Assigning appropriate Handler.");
        HTTPResponse httpResponse;
        if (!httpRequest.getIsValid()) {
            LOGGER.info("Request is not Valid!");
            LOGGER.info("Assigning the request to BadRequestHandler.\"");
//            System.out.println("[Thread # " + threadId + "]: "+ "BadRequest !!!!! ");
            httpResponse = new BadRequestHandler().handle(httpRequest);
        } else {
//            System.out.println("[Thread # " + threadId + "]: "+ "VALID REQUEST !!!!! ");
            String method = httpRequest.getMethod();
//            System.out.println("[Thread # " + threadId + "]: "+ "METHOD: " + method);
            String path = httpRequest.getPath();
//            System.out.println("[Thread # " + threadId + "]: "+ "PATH: " + path);

            if(!(method.equals(HTTPConstants.GET) || method.equals(HTTPConstants.PROTOCOL))) {
                LOGGER.info("Request Method (" + method + ") is not supported!");
                LOGGER.info("Assigning the request to MethodNotAllowedHandler.");
//                System.out.println("[Thread # " + threadId + "]: "+ "INVALID METHOD: " + method);
                httpResponse = new MethodNotAllowedHandler().handle(httpRequest);
            } else if (!pathToHandlerMap.containsKey(path)) {
                LOGGER.info("Request Path (" + path + ") is not valid!");
                LOGGER.info("Assigning the request to BadRequestHandler.");
//                System.out.println("[Thread # " + threadId + "]: "+ "INVALID PATH: " + path);
                httpResponse = new BadRequestHandler().handle(httpRequest);
            } else {
                LOGGER.info("Request is valid!");
                LOGGER.info("Assigning the request to " + pathToHandlerMap.get(path).handle(httpRequest).getClass());
//                System.out.println("[Thread # " + threadId + "]: "+ "VALID CALL ");
                httpResponse = pathToHandlerMap.get(path).handle(httpRequest);
            }
        }
        //testing if the response in valid i.e. correctly formatted.
        LOGGER.info("Sending Response to the Client!");
        LOGGER.info("Response to be sent : ");
        LOGGER.info(httpResponse.getHTTPResponse());
        writer.write(httpResponse.getHTTPResponse());
    }



    public boolean requestLineValidator (String[] HTTPRequestArray) {
        if (HTTPRequestArray.length == 3 && HTTPRequestArray[2].equals(HTTPConstants.PROTOCOL)) {
            return true;
        }
        return false;
    }

    public boolean requestHeaderLineValidator (String[] HTTPRequestHeader) {
//        String threadId = Thread.currentThread().getName();

//        System.out.println("[Thread # " + threadId + "]: " +"i++++++++++++++++++");
//        System.out.println("[Thread # " + threadId + "]: " +"LENGTH: " + HTTPRequestHeader.length );
//        for(String s :HTTPRequestHeader){
//            System.out.println("[Thread # " + threadId + "]: " + s);
//        }
        if (HTTPRequestHeader.length == 2) {
//            System.out.println("[Thread # " + threadId + "]: " + " TRUE ");
//            System.out.println("[Thread # " + threadId + "]: " +"0++++++++++++++++++");
            return true;
        }
//        System.out.println("[Thread # " + threadId + "]: " +" FALSE ");
//        System.out.println("[Thread # " + threadId + "]: " +"0++++++++++++++++++");
        return false;
    }

}
