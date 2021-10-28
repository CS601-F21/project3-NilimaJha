package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HTTPServer {
    private HashMap<String, Handler> pathToHandlerMap;
    private int port;
    private boolean running;
    private final int threadPoolSize = 8;
    private ExecutorService threadPool;

    /**
     * Constructor
     * @param port
     */
    public HTTPServer (int port) {
        this.port = port;
        this.running = true;
        this.pathToHandlerMap = new HashMap<>();
        this.threadPool = Executors.newFixedThreadPool(threadPoolSize);

    }

    /**
     * method will map a URI path to a specific Handler instance.
     * When a new request is made to the server,
     * the server will retrieve from the mapping the Handler appropriate for the path in the request URI.
     */
    public void addMapping(String path, Handler handlerObject) {
        if (path != null && handlerObject != null) {
            this.pathToHandlerMap.put(path, handlerObject);
        }
    }

    /**
     *
     * @param instream
     * @return
     * @throws IOException
     */
    public HTTPRequest readRequest(BufferedReader instream) throws IOException {
        HTTPRequest httpRequest= new HTTPRequest();
        String[] HTTPRequestLineArray;
        String currentLine;
        String[] lastLine;

        currentLine = instream.readLine();
        HTTPRequestLineArray = currentLine.split("\\s");
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
        while (currentLine != null) {
            String[] HTTPRequestHeader = currentLine.split("\\s");
            if (requestHeaderLineValidator(HTTPRequestHeader)) {
                httpRequest.putRequestHeaders(HTTPRequestHeader[0], HTTPRequestHeader[1]);
            } else {
                httpRequest.setIsValid(false);
            }
            lastLine = HTTPRequestHeader;
            currentLine = instream.readLine();
        }
       // checking if lastLine was content-length line
        if (lastLine[0].equals("Content-length:")) {
            int contentLength = Integer.parseInt (lastLine[1]);
            char[] bodyArray = new char[contentLength];
            instream.read (bodyArray, 0, bodyArray.length);
            httpRequest.setMessage(new String (bodyArray));
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
        if (HTTPRequestHeader.length == 2) {
            return true;
        }
        return false;
    }

    /**
     * method startup()
     */
    public void startup() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while (running) {
                try (Socket connectionSocket = serverSocket.accept()) {
                    this.threadPool.execute(new Runnable() {
                        Socket currentConnectionSocket = connectionSocket;
                        @Override
                        public void run() {
                            try (BufferedReader inStream = new BufferedReader(new InputStreamReader(currentConnectionSocket.getInputStream()));
                                 PrintWriter writer = new PrintWriter(currentConnectionSocket.getOutputStream())
                            ) {
                                HTTPRequest httpRequest = readRequest(inStream);
                                pathHandlerAssigner(httpRequest, writer);
                                //got request object not check if the request is valid or not then assign a proper handler
                            } catch (IOException e) {
                                //logger
                                e.printStackTrace();
                               // HTTPResponse httpResponse = new InternalServerErrorHandler().handle(httpRequest);
                            }
                        }

                        /**
                         * method pathHandlerAssigner()
                         *
                         * @param httpRequest
                         * @param writer
                         */
                        public void pathHandlerAssigner(HTTPRequest httpRequest, PrintWriter writer) {
                            HTTPResponse httpResponse;
                            if (!httpRequest.getIsValid()) {
                                httpResponse = new BadRequestHandler().handle(httpRequest);
                            } else {
                                String path = httpRequest.getPath();
                                if(!path.equals("GET") || !path.equals("POST")) {
                                    httpResponse = new MethodNotAllowedHandler().handle(httpRequest);
                                } else if (!pathToHandlerMap.containsKey(path)) {
                                    httpResponse = new BadRequestHandler().handle(httpRequest);
                                } else {
                                    httpResponse = pathToHandlerMap.get(path).handle(httpRequest);
                                }
                            }
                            writer.write(httpResponse.getHTTPResponse());
                        }
                    });
                } catch (IOException e) {
                    //logger
                    e.printStackTrace();
                }
                //models view controller
            }
        }  catch (IOException e) {
            e.printStackTrace();
            //logger
        }
    }

    public static void main(String[] args) {
        HTTPServer httpServer = new HTTPServer(1024);
    }
}
