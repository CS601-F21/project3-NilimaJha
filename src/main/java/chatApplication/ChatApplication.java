package chatApplication;

import chatApplication.api.SlackBotHandler;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import server.HTTPServer;
import server.httpDefaultHandlers.ShutDownHandler;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * ChatApplication server driver class.
 * @author nilimajha
 */
public class ChatApplication {
    private static final Logger LOGGER = (Logger) LogManager.getLogger(ChatApplication.class);

    /**
     * main method of ChatApplication.
     * HTTPServer for ChatApplication starts here.
     *
     * @param args
     */
    public static void main(String[] args) {

//        String arg = "-input Chat_Application_config.json";
//        String[] args = arg.split(" ");

        int port;
        if (inputArgumentIsValid(args)) {
            try {
                ChatAppConfigData chatAppConfigData = readInputConfigFile(args[1]);
                if (chatAppConfigData.isValid()) {
                    String slackTokenFileName = chatAppConfigData.getTokenFileName();
                    String slackChannelID = chatAppConfigData.getSlackChannelId();
                    port = chatAppConfigData.getPortNo();

                    HTTPServer httpServer = new HTTPServer(port);
                    httpServer.addMapping("/slackbot", new SlackBotHandler(slackTokenFileName, slackChannelID));
                    httpServer.addMapping("/shutdown", new ShutDownHandler());
                    LOGGER.info("Starting ChatApplication Server!...");
                    httpServer.startup();
                    LOGGER.info("ChatApplication Server Stopped!");
                } else {
                    LOGGER.error("Missing Data in Config File '" + args[1] + "' !");
                    LOGGER.error("Shunting down the ChatApplication...");
                    System.exit(0);
                }
            } catch (FileNotFoundException e) {
                LOGGER.error("Config File '" + args[1] + "' Not Found!");
                LOGGER.error("Shunting down the ChatApplication...");
                LOGGER.error("FileNotFoundException : " + e );
                System.exit(0);
            }
        } else {
            LOGGER.error("Input Argument received : " + args.toString());
            LOGGER.error("Invalid Input Argument! \nShunting down the ChatApplication...");
            System.exit(0);
        }

    }

    /**
     * method inputArgumentIsValid() validates
     * the input argument.
     * @param args
     * @return
     */
    public static boolean inputArgumentIsValid (String[] args) {
        if (args.length == 2 && args[0].equals("-input") && getExtension(args[1]).equals(".json")) {
            LOGGER.info("Input config file name : " +args[1]);
            return true;
        }else {
            return false;
        }
    }

    /**
     * method getExtension()
     * extracts the extension of the given fileName that is in String format.
     * @param fileName
     * @return extension
     */
    public static String getExtension(String fileName) {
        String extension = null;
        int index = fileName.lastIndexOf(".");
        if (index > 0 && index < fileName.length() - 1) {
            extension = fileName.substring(index);
        }
        return extension;
    }

    /**
     * method readInputConfigFile()
     * reads from InputConfigFile and extracts information from it.
     *
     * @param inputConfigFileName
     * @return chatAppConfigDataObj
     * @throws FileNotFoundException
     */
    public static ChatAppConfigData readInputConfigFile (String inputConfigFileName) throws FileNotFoundException {
        Gson gson = new Gson();
        ChatAppConfigData chatAppConfigData = new ChatAppConfigData();
        FileInputStream fileInputStream = new FileInputStream(inputConfigFileName);
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.ISO_8859_1);
        try (BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
            String line = bufferedReader.readLine();
            try {
                chatAppConfigData = gson.fromJson(line, ChatAppConfigData.class);
            } catch (JsonSyntaxException e) {
                LOGGER.error("JsonSyntaxException occurred: " + e);
                e.printStackTrace();
            }
        } catch (IOException e) {
            LOGGER.error("IOException occurred occurred: " + e);
            e.printStackTrace();
        }
        LOGGER.info("tokenFileName: " + chatAppConfigData.getTokenFileName() +
                "\nSlackChannelID: " + chatAppConfigData.getSlackChannelId() +
                "\nPortNumber: " + chatAppConfigData.getPortNo());
        return chatAppConfigData;
    }

}
