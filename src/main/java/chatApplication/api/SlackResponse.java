package chatApplication.api;

public class SlackResponse {
    private boolean ok = false;
    private String text = null;
    private String error = null;

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setError(String error) {
        this.error = error;
    }

    public boolean isOk() {
        return ok;
    }

    public String getText() {
        return text;
    }

    public String getError() {
        return error;
    }

}
