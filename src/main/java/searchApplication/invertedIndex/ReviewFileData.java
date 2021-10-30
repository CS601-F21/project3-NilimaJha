package searchApplication.invertedIndex;

import java.util.ArrayList;
import java.util.HashMap;

public class ReviewFileData {
    private InvertedIndex InvertedIndex = new InvertedIndex();
    private HashMap<String, ArrayList<Review>> asinReviewMap = new HashMap<>();
    private ArrayList<Review> reviewList = new ArrayList<>();

    public searchApplication.invertedIndex.InvertedIndex getInvertedIndex() {
        return InvertedIndex;
    }

    public HashMap<String, ArrayList<Review>> getAsinReviewMap() {
        return asinReviewMap;
    }

    public ArrayList<Review> getReviewList() {
        return reviewList;
    }

    public void setInvertedIndex(InvertedIndex invertedIndex) {
        InvertedIndex = invertedIndex;
    }

    public void setAsinReviewMap(HashMap<String, ArrayList<Review>> asinReviewMap) {
        this.asinReviewMap = asinReviewMap;
    }

    public void setReviewList(ArrayList<Review> reviewList) {
        this.reviewList = reviewList;
    }
}
