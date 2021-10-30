package chatApplication;

import chatApplication.api.SlackBotHandler;
import server.HTTPServer;

public class ChatApplication {
    public static void main(String[] args) {
        HTTPServer httpServer = new HTTPServer(1027);
        httpServer.addMapping("/slackbot", new SlackBotHandler());
        System.out.println();
        System.out.println("Test server starting.");
        System.out.println("........................");
        httpServer.startup();
        System.out.println("Test server started.");
        System.out.println("........................");
    }

}
