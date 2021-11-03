package searchApplication.invertedIndex;

public class FileData {
    private static ReviewFileData reviewFileData;
    private static QAFileData qaFileData;

    private FileData () {}

    public synchronized static ReviewFileData getReviewFileData() {
        if (reviewFileData == null) {
            reviewFileData = new ReviewFileData();
        }
        return reviewFileData;
    }

    public synchronized static QAFileData getQAFileData() {
        if (qaFileData == null) {
            qaFileData = new QAFileData();
        }
        return qaFileData;
    }
}
