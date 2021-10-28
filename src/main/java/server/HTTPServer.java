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


    public void startup() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            int i = 1;
            while (running) {
                System.out.println(i + ": Server is waiting for input on "+ port + "....");
                try {
                    Socket connectionSocket = serverSocket.accept();
                    this.threadPool.execute(new Connection(connectionSocket, this.pathToHandlerMap));
                    System.out.println("Request Assigned to thread pool.");
                } catch (IOException e) {
                    //logger
                    System.out.println("CATCH IOException 1 : " + e );
                    e.printStackTrace();
                }
                i++;
            }
        }  catch (IOException e) {
            System.out.println("CATCH IOException 2 : " + e );
            e.printStackTrace();
            //logger
        }
    }

    public static void main(String[] args) {
        HTTPServer httpServer = new HTTPServer(1024);
    }
}
