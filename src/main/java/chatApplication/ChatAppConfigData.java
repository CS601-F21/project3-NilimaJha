package chatApplication;

import server.HTTPConstants;

/**
 * Class ChatApplicationConfig to store json config file data.
 * @author nilimajha
 */
public class ChatAppConfigData {
    private String tokenFileName;
    private String slackChannelId;
    private int portNo;

    /**
     * method getTokenFileName() returns the tokenFileName
     * @return tokenFileName
     */
    public String getTokenFileName() {
        return tokenFileName;
    }

    /**
     * method getSlackChannelId() returns the slackChannelId
     * @return slackChannelId
     */
    public String getSlackChannelId() {
        return slackChannelId;
    }

    /**
     * method getPortNo() returns the portNo
     * @return portNo
     */
    public int getPortNo() {
        return portNo;
    }

    /**
     * method setTokeFileName() sets the value of attribute tokenFileName.
     * @param tokenFileName
     */
    public void setTokenFileName(String tokenFileName) {
        this.tokenFileName = tokenFileName;
    }

    /**
     * method setSlackChannelId() sets the value of attribute slackChannelId.
     * @param slackChannelId
     */
    public void setSlackChannelId(String slackChannelId) {
        this.slackChannelId = slackChannelId;
    }

    /**
     * method setPortNo() sets the value of attribute setPortNo.
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
        if (!this.tokenFileName.equals(null) && !this.slackChannelId.equals(null) && this.portNo != HTTPConstants.CHAT_APP_PORT) {
            if (jsonFileValidator(this.tokenFileName)) {
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
    public String getExtension(String fileName) {
        String extension = null;
        int index = fileName.lastIndexOf(".");
        if (index > 0 && index < fileName.length() - 1) {
            extension = fileName.substring(index);
        }
        return extension;
    }
}
