package server;

import searchApplication.api.ReviewSearchHandler;
import searchApplication.invertedIndex.*;
import searchApplication.api.FindHandler;

import java.io.IOException;

public class Test {
    public static void main(String[] args) {

        ReviewFileData rfds = new ReviewFileData();
        QAFileData qafds = new QAFileData();
        String reviewFileName = "reviews_Cell_Phones_and_Accessories_5.json";
        String qaFileName = "qa_Cell_Phones_and_Accessories.json";
        try {
            rfds = new FileProcessor().reviewFileProcessor(reviewFileName);
            qafds = new FileProcessor().qaFileProcessor(qaFileName);
        } catch (IOException e) {
            System.out.println("IOException." + e);
        }
        HTTPServer httpServer = new HTTPServer(1026);
        httpServer.addMapping("/find", new FindHandler(rfds, qafds));
        httpServer.addMapping("/reviewsearch", new ReviewSearchHandler(rfds,qafds));
        System.out.println();
        System.out.println("Test server starting.");
        System.out.println("........................");
        httpServer.startup();
        System.out.println("Test server started.");
        System.out.println("........................");
    }


}
