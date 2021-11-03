package searchApplication.invertedIndex;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * ReviewFileData class contains the dataStructure related to Review File.
 * @author nilimajha
 */
public class ReviewFileData {
    private InvertedIndex invertedIndex = new InvertedIndex();
    private HashMap<String, ArrayList<Review>> asinReviewMap = new HashMap<>();
    private ArrayList<Review> reviewList = new ArrayList<>();

    /**
     * Getter for attribute invertedIndex.
     * @return invertedIndex
     */
    public searchApplication.invertedIndex.InvertedIndex getInvertedIndex() {
        return invertedIndex;
    }

    /**
     * Getter for attribute asinReviewMap which is an hashmap that maps asin with Review object
     * @return asinReviewMap
     */
    public HashMap<String, ArrayList<Review>> getAsinReviewMap() {
        return asinReviewMap;
    }

    /**
     * getter for attribute reviewList which is an ArrayList that stores Review obj.
     * @return reviewList
     */
    public ArrayList<Review> getReviewList() {
        return reviewList;
    }

    /**
     * setter for invertedIndex
     * @param invertedIndex
     */
    public void setInvertedIndex(InvertedIndex invertedIndex) {
        this.invertedIndex = invertedIndex;
    }

    /**
     * setter for asinReviewMap.
     * @param asinReviewMap
     */
    public void setAsinReviewMap(HashMap<String, ArrayList<Review>> asinReviewMap) {
        this.asinReviewMap = asinReviewMap;
    }

    /**
     * setter for reviewList.
     * @param reviewList
     */
    public void setReviewList(ArrayList<Review> reviewList) {
        this.reviewList = reviewList;
    }

}
