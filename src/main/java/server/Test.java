package server;

public class Test {
    public static void main(String[] args) {
        HTTPServer httpServer = new HTTPServer(1026);
        System.out.println("Test server started.");
        System.out.println("........................");
        httpServer.addMapping("/find", new TestHandler());
        System.out.println();
        httpServer.startup();
    }

}
