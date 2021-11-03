package searchApplication.invertedIndex;

/**
 * QA Class for the QA json objects of the QA File.
 * @author nilimajha
 */
public class QA {
    private String questionType;
    private String asin;
    private String answerTime;
    private long unixTime;
    private String question;
    private char answerType;
    private String answer;

    /**
     * constructor
     *
     * @param questionType  String
     * @param asin          String
     * @param answerTime    String
     * @param unixTime      long
     * @param question      String
     * @param answerType    char
     * @param answer        String
     */
    public QA(String questionType, String asin, String answerTime,
              long unixTime, String question, char answerType, String answer) {
        this.questionType = questionType;
        this.asin = asin;
        this.answerTime = answerTime;
        this.unixTime = unixTime;
        this.question = question;
        this.answerType = answerType;
        this.answer = answer;
    }

    /**
     * getAsin() is a getter method for the attribute asin
     * @return asin
     */
    public String getAsin() {
        return asin;
    }

    /**
     * getQuestion() is a getter method for the attribute question
     *
     * @return question
     */
    public String getQuestion() {
        return question;
    }

    /**
     * getAnswer() is a getter method for the attribute answer;
     *
     * @return answer
     */
    public String getAnswer() {
        return answer;
    }
}
