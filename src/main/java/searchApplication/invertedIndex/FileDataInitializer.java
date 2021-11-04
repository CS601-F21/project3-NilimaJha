package searchApplication.invertedIndex;

/**
 * FileDataInitializer class make sure that only one instance of the review and Qa file data is created.
 * @author nilimajha
 */
public class FileDataInitializer {
    private static ReviewFileData reviewFileData;
    private static QAFileData qaFileData;

    /**
     * Constructor
     */
    private FileDataInitializer() {}

    /**
     * Returns the ReviewFileData object.
     * if the object has already been created then the reference of that object is passed.
     * if the object is not yet created then it will create an instance of it are return.
     * @return
     */
    public synchronized static ReviewFileData getReviewFileData() {
        if (reviewFileData == null) {
            reviewFileData = new ReviewFileData();
        }
        return reviewFileData;
    }

    /**
     * Returns the QAFileData object.
     * if the object has already been created then the reference of that object is passed.
     * if the object is not yet created then it will create an instance of it are return.
     * @return
     */
    public synchronized static QAFileData getQAFileData() {
        if (qaFileData == null) {
            qaFileData = new QAFileData();
        }
        return qaFileData;
    }
}
