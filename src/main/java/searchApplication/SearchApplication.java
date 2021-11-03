package searchApplication;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import searchApplication.api.FindHandler;
import searchApplication.api.ReviewSearchHandler;
import searchApplication.invertedIndex.FileProcessor;
import server.HTTPServer;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 *
 * @author nilimajha
 */
public class SearchApplication {
    public static void main(String[] argss) {
        String arg = "-input Search_Application_Config.json";
        String[] args = arg.split(" ");

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
                        System.out.println("IOException occurred while reading from input files.");
                        System.out.println("IOException." + e);
                        System.exit(0);
                    }
                    HTTPServer httpServer = new HTTPServer(port);
                    httpServer.addMapping("/find", new FindHandler());
                    httpServer.addMapping("/reviewsearch", new ReviewSearchHandler());
                    System.out.println();
                    System.out.println("Test server starting.");
                    System.out.println("........................");
                    httpServer.startup();
                    System.out.println("Test server started.");
                    System.out.println("........................");

                } else {
                    System.out.println("Input Config File does not contain all the required info. Shunting Down the Search App.");
                    System.exit(0);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                System.exit(0);
            }
        } else {
            System.out.println("Invalid Input Argument! \nShunting down the Search App.");
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
     *
     * @param inputConfigFileName
     * @return
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
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return searchAppConfigDataObj;
    }
}
