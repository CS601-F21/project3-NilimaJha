package searchApplication;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import searchApplication.api.FindHandler;
import searchApplication.api.ReviewSearchHandler;
import searchApplication.invertedIndex.FileProcessor;
import server.HTTPServer;
import server.httpDefaultHandlers.ShutDownHandler;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * SearchApplication server driver class.
 * @author nilimajha
 */
public class SearchApplication {
    private static final Logger LOGGER = (Logger) LogManager.getLogger(SearchApplication.class);

    /**
     * main method of SearchApplication.
     * HTTPServer for SearchApplication starts here.
     *
     * @param args
     */
    public static void main(String[] args) {

//        String arg = "-input Search_Application_config.json";
//        String[] args = arg.split(" ");

        String reviewFileName;
        String qaFileName;
        int port;
        if (inputArgumentIsValid(args)) {
            try {
                SearchAppConfigData searchAppConfigData = readInputConfigFile(args[1]);
                if (searchAppConfigData.isValid()) {
                    reviewFileName = searchAppConfigData.getReviewFile();
                    qaFileName = searchAppConfigData.getQaFile();
                    port = searchAppConfigData.getPortNo();
                    FileProcessor fileProcessor = new FileProcessor();
                    try {
                        fileProcessor.reviewFileProcessor(reviewFileName);
                        fileProcessor.qaFileProcessor(qaFileName);
                    } catch (IOException e) {
                        LOGGER.error("IOException occurred while reading from input files!");
                        LOGGER.error("IOException." + e);
                        LOGGER.error("Shunting down the SearchApplication.");
                        System.exit(0);
                    }
                    LOGGER.info("before SearchApplication Server!.");
                    HTTPServer httpServer = new HTTPServer(port);
                    httpServer.addMapping("/find", new FindHandler());
                    httpServer.addMapping("/reviewsearch", new ReviewSearchHandler());
                    httpServer.addMapping("/shutdown", new ShutDownHandler());
                    LOGGER.info("Starting SearchApplication Server!.");
                    httpServer.startup();
                    LOGGER.info("SearchApplication Server Stopped!.");
                } else {
                    LOGGER.error("Missing Data in Config File '" + args[1] + "' !");
                    LOGGER.error("Shunting down the SearchApplication.");
                    System.exit(0);
                }
            } catch (FileNotFoundException e) {
                LOGGER.error("Config File '" + args[1] + "' Not Found!");
                LOGGER.error("FileNotFoundException: " + e);
                LOGGER.error("Shunting down the SearchApplication.");
                System.exit(0);
            }
        } else {
            LOGGER.error("Input Argument received : " + args.toString());
            LOGGER.error("Invalid Input Argument! \nShunting down the SearchApplication...");
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
     * @return searchAppConfigDataObj
     * @throws FileNotFoundException
     */
    public static SearchAppConfigData readInputConfigFile (String inputConfigFileName) throws FileNotFoundException {
        Gson gson = new Gson();
        SearchAppConfigData searchAppConfigDataObj = new SearchAppConfigData();
        FileInputStream fileInputStream = new FileInputStream(inputConfigFileName);
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.ISO_8859_1);
        try (BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
            String line = bufferedReader.readLine();
            try {
                searchAppConfigDataObj = gson.fromJson(line, SearchAppConfigData.class);
            } catch (JsonSyntaxException e) {
                LOGGER.error("JsonSyntaxException occurred: " + e);
            }
        } catch (IOException e) {
            LOGGER.error("IOException occurred occurred: " + e);
        }
        return searchAppConfigDataObj;
    }
}
