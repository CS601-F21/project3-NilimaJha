package searchApplication;

/**
 * Class searchAppConfigData to store json config file data.
 * @author nilimajha
 */
public class SearchAppConfigData {
    private String reviewFile;
    private String qaFile;
    private int portNo;

    /**
     * method getReviewFile() is a getter of attribute reviewFile.
     * @return reviewFile
     */
    public String getReviewFile() {
        return reviewFile;
    }

    /**
     * method getQaFile() is a getter of attribute qaFile.
     * @return qaFile
     */
    public String getQaFile() {
        return qaFile;
    }

    /**
     * method getPortNo() is a getter of attribute portNo.
     * @return portNo
     */
    public int getPortNo() {
        return portNo;
    }

    /**
     * sets value to the attribute named reviewFile.
     * @param reviewFile
     */
    public void setReviewFile(String reviewFile) {
        this.reviewFile = reviewFile;
    }

    /**
     * sets value to the attribute named qaFile.
     * @param qaFile
     */
    public void setQaFile(String qaFile) {
        this.qaFile = qaFile;
    }

    /**
     * sets value to the attribute named portNo.
     * @param portNo
     */
    public void setPortNo(int portNo) {
        this.portNo = portNo;
    }

    /**
     * method isValid() returns
     * true if all the attribute contains value.
     * otherwise, false.
     * @return boolean
     */
    public boolean isValid () {
        if (!this.reviewFile.equals(null) && !this.qaFile.equals(null) && this.portNo != 0) {
            if (jsonFileValidator(this.reviewFile) && jsonFileValidator(this.qaFile)) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    /**
     * method jsonFileValidator() returns
     * true if the input file is of type json
     * and false otherwise.
     * @param fileName
     * @return boolean
     */
    public boolean jsonFileValidator (String fileName) {
        if (getExtension(fileName).equals(".json")) {
            return true;
        }
        return false;
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

}
