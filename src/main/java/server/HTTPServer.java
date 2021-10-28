package server;

import handler.Handler;
import org.apache.log4j.Logger;
import org.apache.log4j.LogManager;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HTTPServer {
    private static final Logger LOGGER = LogManager.getLogger(HTTPServer.class);

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
    public void addMapping(String path, Handler handlerType) {
        if (path != null && handlerType != null) {
            this.pathToHandlerMap.put(path, handlerType);
        }
    }


    public void startup() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            int i = 1; // To keep track of requests
            while (running) {
                LOGGER.info("Server listening on port " + port);
//                System.out.println(i + ": Server is waiting for input on "+ port + "....");
                try {
                    Socket connectionSocket = serverSocket.accept();
                    LOGGER.info("New connection from " + connectionSocket.getInetAddress());
                    System.out.println("New connection from " + connectionSocket.getInetAddress());
                    this.threadPool.execute(new HTTPConnection(connectionSocket, this.pathToHandlerMap));
                    LOGGER.info(i + ">> Request Assigned to thread pool.");
//                    System.out.println("Request Assigned to thread pool.");
                } catch (IOException e) {
                    LOGGER.error("Caught IOException : " + e);
                }
                i++;
            }
        }  catch (IOException e) {
            LOGGER.error("Caught IOException : " + e);
        }
    }
}
