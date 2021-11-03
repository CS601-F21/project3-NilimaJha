package searchApplication.invertedIndex;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * FileProcessor class contains
 * a method that reads from review data file and fills ReviewFileData.
 * and a method that reads from QA data file and fills QAFileData.
 * @author nilimajha
 */
public class FileProcessor {

    /**
     * method reviewFileProcessor()
     * creates a list of Review class obj from review json file.
     * updates reviewInvertedIndex.
     * updates AsinReviewMap to map the asin with their respective review obj.
     *
     * @param fileName  File to read.
     * @throws IOException
     */
    public void reviewFileProcessor(String fileName) throws IOException {
        ReviewFileData reviewFileData = FileDataInitializer.getReviewFileData();

        InvertedIndex reviewInvertedIndex = new InvertedIndex();
        HashMap<String, ArrayList<Review>> asinReviewMap = new HashMap<>();
        ArrayList<Review> reviewList = new ArrayList<>();

        FileInputStream fileInputStream = new FileInputStream(fileName);
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.ISO_8859_1);

        try (BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
            Gson gson = new Gson();
            int docID = 0;
            String jsonStr = bufferedReader.readLine();
            while (jsonStr != null) {
                try {
                    Review reviewObj = gson.fromJson(jsonStr, Review.class);
                    reviewList.add(reviewObj);
                    reviewInvertedIndex.createAndAddIndex(docID, reviewObj.getReviewText());

                    //updating ASIN in asinReviewMap for Reviews.
                    ArrayList<Review> asinReviewList;
                    if(asinReviewMap.containsKey(reviewObj.getAsin())) {
                        asinReviewList = asinReviewMap.get(reviewObj.getAsin());
                    }else {
                        asinReviewList = new ArrayList<>();
                    }
                    asinReviewList.add(reviewObj);
                    asinReviewMap.put(reviewObj.getAsin(), asinReviewList);

                    // Reading Next Json entry
                    jsonStr = bufferedReader.readLine();
                    docID++;

                } catch (com.google.gson.JsonSyntaxException e) {
                    // Commenting The JsonSyntaxException error
                    //System.out.println("Caught a JsonSyntaxException, ignoring this json:" + jsonStr);
                    //System.out.println(e);

                    // Reading next line, to skip above Exception.
                    jsonStr = bufferedReader.readLine();
                }
            }
            reviewFileData.setInvertedIndex(reviewInvertedIndex);
            reviewFileData.setAsinReviewMap(asinReviewMap);
            reviewFileData.setReviewList(reviewList);
        }
        //return reviewFileData;
    }

    /**
     * method qaFileProcessor()
     * creates a list of QA class obj from qa json file.
     * updates qaInvertedIndex.
     * updates AsinQAMap to map the asin with their respective QA obj.
     *
     * @param fileName  File to read.
     * @throws IOException
     */
    public QAFileData qaFileProcessor(String fileName) throws IOException {
        QAFileData qaFileData = FileDataInitializer.getQAFileData();

        InvertedIndex qaInvertedIndex = new InvertedIndex();
        HashMap<String, ArrayList<QA>> qaAsinDocMap = new HashMap<>();
        ArrayList<QA> qaList = new ArrayList<>();

        FileInputStream fileInputStream = new FileInputStream(fileName);
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.ISO_8859_1);

        try (BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
            Gson gson = new Gson();
            String jsonStr = bufferedReader.readLine();
            int docID = 0;
            while (jsonStr != null) {
                try {
                    QA qaObj = gson.fromJson(jsonStr, QA.class);
                    qaList.add(qaObj);
                    qaInvertedIndex.createAndAddIndex(docID, qaObj.getQuestion());
                    qaInvertedIndex.createAndAddIndex(docID, qaObj.getAnswer());

                    // updating ASIN in AsinQAMap for Question/Answer.
                    ArrayList<QA> asinQAMap;
                    if(qaAsinDocMap.containsKey(qaObj.getAsin())) {
                        asinQAMap = qaAsinDocMap.get(qaObj.getAsin());
                    }else {
                        asinQAMap = new ArrayList<>();
                    }
                    asinQAMap.add(qaObj);
                    qaAsinDocMap.put(qaObj.getAsin(), asinQAMap);

                    // Reading NExt Json entry
                    jsonStr = bufferedReader.readLine();
                    docID++;
                }catch(JsonSyntaxException e) {
                    // Commenting The JsonSyntaxException error
                    //System.out.println("Caught a JsonSyntaxException, ignoring this json:" + jsonStr);
                    //System.out.println(e);

                    // Reading next line, to skip above Exception.
                    jsonStr = bufferedReader.readLine();
                }
            }
            qaFileData.setInvertedIndex(qaInvertedIndex);
            qaFileData.setAsinQAMap(qaAsinDocMap);
            qaFileData.setQAList(qaList);
        }
        return qaFileData;
    }
}
