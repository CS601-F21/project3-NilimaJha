package searchApplication.invertedIndex;

import java.util.ArrayList;

/**
 * Class FileDataOps contains methods that do operations on the reviewFilData and QAFileData.
 * @author nilimajha
 */
public class FileDataOps {
    private String alphanumeric = "^[A-Za-z0-9]*$";  // Common expression to check
    private String separator = "\n" + "=".repeat(100) + "\n" ;  // Using to separate outputs

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
        } else {
            if (!FileDataInitializer.getReviewFileData().getAsinReviewMap().containsKey(asin) &&
                    !FileDataInitializer.getQAFileData().getAsinQAMap().containsKey(asin)) {
                findAsinResult.append(this.separator);
                findAsinResult.append("No Reviews or QA associated with the ASIN: " + asin );
                findAsinResult.append(this.separator);

            } else {
                if(FileDataInitializer.getReviewFileData().getAsinReviewMap().containsKey(asin)) {
                    findAsinResult.append(this.separator);
                    findAsinResult.append("List of all ReviewText for ASIN: " + asin );
                    findAsinResult.append(this.separator);
                    //printing the reviewText of the Review object associated with the entered ASIN.
                    for(Review currentReviewObj: FileDataInitializer.getReviewFileData().getAsinReviewMap().get(asin)) {
                        findAsinResult.append("ReviewText: " + currentReviewObj.getReviewText() + "\n\n");
                    }
                } else {
                    findAsinResult.append(this.separator);
                    findAsinResult.append("No Reviews associated with the ASIN: " + asin );
                    findAsinResult.append(this.separator);
                }
                if(FileDataInitializer.getQAFileData().getAsinQAMap().containsKey(asin)) {
                    findAsinResult.append(this.separator);
                    findAsinResult.append("List of all Question/Answer for ASIN: " + asin );
                    findAsinResult.append(this.separator);
                    for (QA currentQAObj : FileDataInitializer.getQAFileData().getAsinQAMap().get(asin)) {
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
        if (word.matches(this.alphanumeric)) {
            // Storing DocIDs of all the reviews that contains the word.
            ArrayList<Integer> docIDs = FileDataInitializer.getReviewFileData().getInvertedIndex().getDocIDsForWord(word);
            if (docIDs.size() != 0){
                reviewSearchResult.append(this.separator);
                reviewSearchResult.append("Following are the Reviews containing word: '" + word + "'");
                reviewSearchResult.append(this.separator);
                //printing reviewText of DocIDs
                for (int docID : docIDs) {
                    String reviewText = FileDataInitializer.getReviewFileData().getReviewList().get(docID).getReviewText();
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

}
