package searchApplication.invertedIndex;

import java.util.ArrayList;
import java.util.Arrays;

public class FileDataOps {
    private ReviewFileData reviewFileData;
    private QAFileData qaFileData;
    private String alphanumeric = "^[A-Za-z0-9]*$";  // Common expression to check
    private String separator = "\n" + "=".repeat(100) + "\n" ;  // Using to separate outputs

    public FileDataOps(ReviewFileData reviewFileData,
                       QAFileData qaFileData) {

        this.reviewFileData = reviewFileData;
        this.qaFileData = qaFileData;
    }

    /**
     * method findAsin()
     * checks that validity of ASIN and then
     * prints the reviewText of all the Review objects, and
     * question and answer of all the QA objects
     * associated with the given ASIN (if available)
     * otherwise prints message.
     *
     * @param asin Product's ASIN
     */
    public String findAsin(String asin) {
        StringBuilder findAsinResult = new StringBuilder();

        // Validating the ASIN entered by User.
        if (!asin.matches(alphanumeric) || asin.length() == 0) {
            findAsinResult.append(this.separator);
            findAsinResult.append("Invalid ASIN. Please enter a valid ASIN.");
            findAsinResult.append(this.separator);
        }else {
            if (!this.reviewFileData.getAsinReviewMap().containsKey(asin) && !this.qaFileData.getAsinQAMap().containsKey(asin)) {
                findAsinResult.append(this.separator);
                findAsinResult.append("No Reviews or QA associated with the ASIN: " + asin );
                findAsinResult.append(this.separator);

            }else {
                if(this.reviewFileData.getAsinReviewMap().containsKey(asin)) {
                    findAsinResult.append(this.separator);
                    findAsinResult.append("List of all ReviewText for ASIN: " + asin );
                    findAsinResult.append(this.separator);
                    //printing the reviewText of the Review object associated with the entered ASIN.
                    for(Review currentReviewObj: this.reviewFileData.getAsinReviewMap().get(asin)) {
                        findAsinResult.append("ReviewText: " + currentReviewObj.getReviewText() + "\n\n");
                    }
                }else {
                    findAsinResult.append(this.separator);
                    findAsinResult.append("No Reviews associated with the ASIN: " + asin );
                    findAsinResult.append(this.separator);
                }
                if(this.qaFileData.getAsinQAMap().containsKey(asin)) {
                    findAsinResult.append(this.separator);
                    findAsinResult.append("List of all Question/Answer for ASIN: " + asin );
                    findAsinResult.append(this.separator);
                    for (QA currentQAObj : this.qaFileData.getAsinQAMap().get(asin)) {
                        //printing the question and answer of the QAObject associated with the entered ASIN.
                        findAsinResult.append("Question : " + currentQAObj.getQuestion() + "\n");
                        findAsinResult.append("Answer : " + currentQAObj.getAnswer() + "\n\n");
                    }
                }else {
                    findAsinResult.append(this.separator);
                    findAsinResult.append("No QA associated with the ASIN: " + asin );
                    findAsinResult.append(this.separator);
                }
            }
        }
        return String.valueOf(findAsinResult);
    }

    /**
     * method reviewSearch() prints
     * all the reviewText associated with the review document
     * that contains the given word in descending order of the word occurrence in it.
     *
     * @param word  word from reviewText of the review file.
     */
    public String reviewSearch(String word) {
        StringBuilder reviewSearchResult = new StringBuilder();
//        System.out.println(this.separator);

        if (word.matches(this.alphanumeric)) {
            // Storing DocIDs of all the reviews that contains the word.
            ArrayList<Integer> docIDs = this.reviewFileData.getInvertedIndex().getDocIDsForWord(word);
            if (docIDs.size() != 0){
                reviewSearchResult.append(this.separator);
                reviewSearchResult.append("Following are the Reviews containing word: '" + word + "'");
                reviewSearchResult.append(this.separator);
                //printing reviewText of DocIDs
                for (int docID : docIDs) {
                    String reviewText = this.reviewFileData.getReviewList().get(docID).getReviewText();
                    reviewSearchResult.append("ReviewText: " + reviewText + "\n\n");
                }
            } else {
                reviewSearchResult.append(this.separator);
                reviewSearchResult.append("No Available Reviews contains word: '" + word + "'");
            }
        }else {
            reviewSearchResult.append(this.separator);
            reviewSearchResult.append("Invalid search term: '" + word + "', Please enter alphanumeric term only.");
            reviewSearchResult.append(this.separator);
        }
        return String.valueOf(reviewSearchResult);
    }

    /**
     * method reviewPartialSearch() prints
     * all the reviewText associated with the review document
     * that even partially matches the given word.
     *
     * @param partialWord   partial word from reviewText of the review file.
     */
    private void reviewPartialSearch(String partialWord) {
        System.out.println(this.separator);

        // Validating the input word.
        if (partialWord.matches(this.alphanumeric)) {
            // Storing DocIDs of all the document that contains the partial word.
            ArrayList<Integer> docIDs = this.reviewFileData.getInvertedIndex().getDocIDsForPartialWord(partialWord);
            if (docIDs.size() != 0){
                int counter = 1;
                System.out.println("Following are the Reviews containing partial word: '" + partialWord + "'");
                System.out.println(this.separator);
                for (int docID : docIDs) {
                    // Printing reviewText of the review Document that contains the word.
                    System.out.println("\n" + counter);
                    System.out.println("ReviewText: " + this.reviewFileData.getReviewList().get(docID).getReviewText());
                    counter++;
                }
            } else {
                System.out.println("No Available Reviews containing partial word: '" + partialWord + "'");
            }

        }else {
            System.out.println("Invalid partial search word: '"
                    + partialWord
                    +"', Please remove all non-alphanumeric characters.");
        }
        System.out.println(this.separator);
    }

    /**
     * method qaSearch() prints
     * all the question and answer associated with the qa document that contains the given word in
     * descending order of the word occurrence in it
     *
     * @param word word from question/answer of the qa file.
     */
    private void qaSearch(String word) {
        System.out.println(this.separator);

        // Validating the input word.
        if (word.matches(this.alphanumeric)) {
            ArrayList<Integer> docIDs = this.qaFileData.getInvertedIndex().getDocIDsForWord(word);
            if (docIDs.size() != 0){
                int counter = 1;
                System.out.println("Following are the QA containing word: '" + word + "'");
                System.out.println(this.separator);
                for (int docID : docIDs) {
                    //printing all the Question & Answer of the Document that is associated with the given word.
                    System.out.println("\n" + counter);
                    System.out.println("Question : " + this.qaFileData.getQaList().get(docID).getQuestion());
                    System.out.println("Answer   : " + this.qaFileData.getQaList().get(docID).getAnswer());
                    counter++;
                }
            } else {
                System.out.println("No Available QA contains word: '" + word + "'");
            }
        }else {
            System.out.println("Invalid search word: '"
                    + word
                    +"', Please remove all non-alphanumeric characters.");
        }
        System.out.println(this.separator);
    }

    /**
     * method qaPartialSearch() prints
     * all the questions and answers associated with the qa document
     * that partially matches the given word.
     *
     * @param partialWord   partial word from question/answer of the qa file.
     */
    private void qaPartialSearch(String partialWord) {
        System.out.println(this.separator);

        if (partialWord.matches(this.alphanumeric)) {
            // Storing docIDs of all the qa documents that contains partial word.
            ArrayList<Integer> docIDs = this.qaFileData.getInvertedIndex().getDocIDsForPartialWord(partialWord);
            // printing question and answer associated with the docID.
            if (docIDs.size() != 0){
                int counter = 1;
                System.out.println("Following are the Question and Answer containing partial word: '" + partialWord + "'");
                System.out.println(this.separator);
                for (int docID : docIDs) {
                    System.out.println("\n" + counter);
                    System.out.println("Question : " + this.qaFileData.getQaList().get(docID).getQuestion());
                    System.out.println("Answer   : " + this.qaFileData.getQaList().get(docID).getAnswer());
                    counter++;
                }
            } else {
                System.out.println("No Available Question/Answer containing partial word: '" + partialWord + "'");
            }
        }else {
            System.out.println("Invalid partial search word: '"
                    + partialWord
                    +"', Please remove all non-alphanumeric characters.");
        }
        System.out.println(this.separator);
    }

    /**
     * method validateCommand()
     * checks the validity of the input command.
     *
     * @param inputCmdArray input command array
     * @return true/false   true if command is valid and false if command is invalid.
     */
    private boolean validateCommand(String[] inputCmdArray) {
        // ArrayList of all 2 word valid Commands.
        ArrayList<String> validCommands = new ArrayList<>(
                Arrays.asList(
                        "find",
                        "reviewsearch",
                        "qasearch",
                        "reviewpartialsearch",
                        "qapartialsearch"
                ));
        if (inputCmdArray.length == 1 && inputCmdArray[0].equals("help")) {
            return true;
        } else if (inputCmdArray.length == 2 && validCommands.contains(inputCmdArray[0])) {
            return true;
        } else {
            return false;
        }
    }

}
