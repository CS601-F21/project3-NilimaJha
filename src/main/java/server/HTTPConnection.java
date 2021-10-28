package server;

import handler.BadRequestHandler;
import handler.Handler;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;

public class Connection implements Runnable {
    private Socket connectionChannel;
    private HashMap<String, Handler> pathToHandlerMap;

    public Connection (Socket connectionChannel, HashMap<String, Handler>pathToHandlerMap) {
        this.connectionChannel = connectionChannel;
        this.pathToHandlerMap = pathToHandlerMap;
        System.out.println("Inside Constructor of connection class.");
    }

    @Override
    public void run() {
        String threadId = Thread.currentThread().getName();
        System.out.println("[Thread # " + threadId + "]: "+ "Request execution started.....");
        try (BufferedReader inStreamReader = new BufferedReader(new InputStreamReader(connectionChannel.getInputStream()));
        PrintWriter outStreamWriter = new PrintWriter(connectionChannel.getOutputStream())) {
            System.out.println("[Thread # " + threadId + "]: "+ "inputStreamReader and outputStreamReader initialized.");
            HTTPRequest httpRequest = readRequest(inStreamReader);
            pathHandlerAssigner(httpRequest, outStreamWriter);
        } catch (IOException e) {
            System.out.println("[Thread # " + threadId + "]: "+ "IN CATCH IOException: " + e);
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("[Thread # " + threadId + "]: "+ "IN EXCEPT CATCH :" + e);
            e.printStackTrace();
        }
    }

    public void pathHandlerAssigner(HTTPRequest httpRequest, PrintWriter writer) {
        String threadId = Thread.currentThread().getName();
        System.out.println("[Thread # " + threadId + "]: "+ "Assigning appropriate Handler.");
        HTTPResponse httpResponse;
        if (!httpRequest.getIsValid()) {
            System.out.println("[Thread # " + threadId + "]: "+ "BadRequest !!!!! ");
            httpResponse = new BadRequestHandler().handle(httpRequest);
        } else {
            System.out.println("[Thread # " + threadId + "]: "+ "VALID REQUEST !!!!! ");
            String method = httpRequest.getMethod();
            System.out.println("[Thread # " + threadId + "]: "+ "METHOD: " + method);
            String path = httpRequest.getPath();
            System.out.println("[Thread # " + threadId + "]: "+ "PATH: " + path);

            if(!(method.equals("GET") || method.equals("POST"))) {
                System.out.println("[Thread # " + threadId + "]: "+ "INVALID METHOD: " + method);
                httpResponse = new MethodNotAllowedHandler().handle(httpRequest);
            } else if (!pathToHandlerMap.containsKey(path)) {
                System.out.println("[Thread # " + threadId + "]: "+ "INVALID PATH: " + path);
                httpResponse = new BadRequestHandler().handle(httpRequest);
            } else {
                System.out.println("[Thread # " + threadId + "]: "+ "VALID CALL ");
                httpResponse = pathToHandlerMap.get(path).handle(httpRequest);
            }
        }
        writer.write(httpResponse.getHTTPResponse());
    }

    public HTTPRequest readRequest(BufferedReader instream) throws IOException {
        String threadId = Thread.currentThread().getName();
        System.out.println("[Thread # " + threadId + "]: "+ "Inside ReadRequest method.");
        HTTPRequest httpRequest= new HTTPRequest();
        String[] HTTPRequestLineArray;
        String currentLine;
        String[] lastLine;

        currentLine = instream.readLine();
        System.out.println("[Thread # " + threadId + "]: "+  currentLine);
        if(currentLine == null){
            // if First line is NULL
            currentLine = instream.readLine();
        }
        System.out.println("[Thread # " + threadId + "]: "+  currentLine);

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
            lastLine = HTTPRequestLineArray;
            currentLine = instream.readLine();
            while (currentLine != null && currentLine.length() != 0 ) {
                System.out.println("[Thread # " + threadId + "]: " +  "FOR current HEADER LINE>>: " + currentLine);
                String[] HTTPRequestHeader = currentLine.split(":\\s");
                if (requestHeaderLineValidator(HTTPRequestHeader)) {
                    httpRequest.putRequestHeaders(HTTPRequestHeader[0], HTTPRequestHeader[1]);
                } else {
                    httpRequest.setIsValid(false);
                }
                lastLine = HTTPRequestHeader;
                currentLine = instream.readLine();
            }
            System.out.println("[Thread # " + threadId + "]: "+  "FOR LAST LINE: " + currentLine);
            // checking if lastLine was content-length line
            if (lastLine[0].equals("Content-length:")) {
                int contentLength = Integer.parseInt (lastLine[1]);
                char[] bodyArray = new char[contentLength];
                instream.read (bodyArray, 0, bodyArray.length);
                httpRequest.setMessage(new String (bodyArray));
            }
        } else {
            System.out.println("[Thread # " + threadId + "]: "
                    + "REQUEST FIRST LINE IS NULL -> should contain method/path/protocol");
            httpRequest.setIsValid(false);
        }
        return httpRequest;
    }

    public boolean requestLineValidator (String[] HTTPRequestArray) {
        if (HTTPRequestArray.length == 3 && HTTPRequestArray[2].equals(HTTPConstants.PROTOCOL)) {
            return true;
        }
        return false;
    }

    public boolean requestHeaderLineValidator (String[] HTTPRequestHeader) {
        String threadId = Thread.currentThread().getName();

        System.out.println("[Thread # " + threadId + "]: " +"i++++++++++++++++++");
        System.out.println("[Thread # " + threadId + "]: " +"LENGTH: " + HTTPRequestHeader.length );
        for(String s :HTTPRequestHeader){
            System.out.println("[Thread # " + threadId + "]: " + s);
        }
        if (HTTPRequestHeader.length == 2) {
            System.out.println("[Thread # " + threadId + "]: " + " TRUE ");
            System.out.println("[Thread # " + threadId + "]: " +"0++++++++++++++++++");
            return true;
        }
        System.out.println("[Thread # " + threadId + "]: " +" FALSE ");
        System.out.println("[Thread # " + threadId + "]: " +"0++++++++++++++++++");
        return false;
    }

}
