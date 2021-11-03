package searchApplication;

public class SearchApplicationConfigFileInfo {
    private String reviewFile;
    private String qaFile;
    private int portNo;

    public String getReviewFile() {
        return reviewFile;
    }

    public String getQaFile() {
        return qaFile;
    }

    public int getPortNo() {
        return portNo;
    }

    public void setReviewFile(String reviewFile) {
        this.reviewFile = reviewFile;
    }

    public void setQaFile(String qaFile) {
        this.qaFile = qaFile;
    }

    public void setPortNo(int portNo) {
        this.portNo = portNo;
    }
}
