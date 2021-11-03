package searchApplication.invertedIndex;

/**
 * Review Class for the Review json objects of the Review File.
 * @author nilimajha
 */
public class Review {
    private String reviewerID;
    private String asin;
    private String reviewerName;
    private int[] helpful;
    private String reviewText;
    private float overall;
    private String summary;
    private long unixReviewTime;
    private String reviewTime;

    /**
     * Constructor
     *
     * @param reviewerID   String
     * @param asin         String
     * @param reviewerName String
     * @param helpful      int[]
     * @param reviewText   String
     * @param overall      float
     * @param summary      String
     * @param unixReviewTime    long
     * @param reviewTime    String
     */
    public Review(String reviewerID, String asin, String reviewerName, int[] helpful, String reviewText,
                  float overall, String summary, long unixReviewTime, String reviewTime) {
        this.reviewerID = reviewerID;
        this.asin = asin;
        this.reviewerName = reviewerName;
        this.helpful = helpful;
        this.reviewText = reviewText;
        this.overall = overall;
        this.summary = summary;
        this.unixReviewTime = unixReviewTime;
        this.reviewTime = reviewTime;
    }

    /**
     * getAsin() is a getter method for the attribute asin;
     *
     * @return asin
     */
    public String getAsin() {
        return asin;
    }

    /**
     * getReviewText() is a getter method for the attribute reviewText;
     *
     * @return reviewText
     */
    public String getReviewText() {
        return reviewText;
    }
}
