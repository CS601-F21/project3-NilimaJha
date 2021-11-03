package searchApplication.invertedIndex;

/**
 *
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
     *
     * @return
     */
    public synchronized static ReviewFileData getReviewFileData() {
        if (reviewFileData == null) {
            reviewFileData = new ReviewFileData();
        }
        return reviewFileData;
    }

    /**
     *
     * @return
     */
    public synchronized static QAFileData getQAFileData() {
        if (qaFileData == null) {
            qaFileData = new QAFileData();
        }
        return qaFileData;
    }
}
