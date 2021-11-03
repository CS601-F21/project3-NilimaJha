package chatApplication.api;

/**
 * Class Slack Response is to store slackResponse.
 * @author nilimajha
 */
public class SlackResponse {
    private boolean ok = false;
    private String text = null;
    private String error = null;

    /**
     * setter of attribute ok.
     * @param ok
     */
    public void setOk(boolean ok) {
        this.ok = ok;
    }

    /**
     * setter of attribute text.
     * @param text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * setter of attribute error.
     * @param error
     */
    public void setError(String error) {
        this.error = error;
    }

    /**
     * getter for the attribute ok
     * @return ok - boolean
     */
    public boolean isOk() {
        return ok;
    }

    /**
     * getter for the attribute text.
     * @return text - String
     */
    public String getText() {
        return text;
    }

    /**
     * getter for the attribute error.
     * @return error - String
     */
    public String getError() {
        return error;
    }

}
