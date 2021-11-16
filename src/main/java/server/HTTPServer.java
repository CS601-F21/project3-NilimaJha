package server;

import handler.Handler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * HTTPServer Class serves the request of client by waiting for request,
 * assigning it to the appropriate handler and
 * sending response back to the client.
 * It uses thread pool to handle multiple client request at a time.
 *
 * @author nilimajha
 */
public class HTTPServer {
    private static final Logger LOGGER = (Logger) LogManager.getLogger(HTTPServer.class);

    private HashMap<String, Handler> pathToHandlerMap;
    private int port;
    private volatile boolean running;
    private final int threadPoolSize = 8;
    private ExecutorService threadPool;
    public static int shutdownRequestFlag = 0 ;

    /**
     * Constructor
     * assign the port number to attribute port,
     * sets running value to true and
     * initializes pathToHandlerMap and threadPool.
     *
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
            if (path.charAt(0) == '/') {
                this.pathToHandlerMap.put(path, handlerType);
            }
        }
    }

    /**
     * method startup()
     * starts the HTTPServer, accepts the request and assign it to the thread-pool
     */
    public void startup() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);

            while (running) {
                LOGGER.info("Server listening on port " + port);
                try {
                    Socket connectionSocket = serverSocket.accept();
                    if (shutdownRequestFlag == 1) {
                        shutdown();
                    } else {
                        LOGGER.info("New connection from " + connectionSocket.getInetAddress());
                        this.threadPool.execute(new HTTPConnection(connectionSocket, this.pathToHandlerMap));
                        LOGGER.info(">> Request Assigned to thread pool.");
                    }
                } catch (IOException e) {
                    LOGGER.error("Caught IOException : " + e);
                }
            }
        }  catch (IOException e) {
            LOGGER.error("Caught IOException : " + e);
        }
    }

    /**
     * shutdown server and thread-pool
     */
    public void shutdown() {
        this.running = false;
        threadPool.shutdown();
        try {
            threadPool.awaitTermination(60, TimeUnit.MILLISECONDS);
        } catch(InterruptedException e) {
            LOGGER.warn("InterruptedException occurred while shutting down Thread-Pool");
        }
    }
}
