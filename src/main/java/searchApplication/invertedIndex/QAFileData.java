package searchApplication.invertedIndex;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * QAFileData class contains the dataStructure related to QA File.
 */
public class QAFileData {
    private InvertedIndex invertedIndex = new InvertedIndex();
    private HashMap<String, ArrayList<QA>> asinQAMap = new HashMap<>();
    private ArrayList<QA> qaList = new ArrayList<>();

    /**
     * setter for invertedIndex
     * @param invertedIndex
     */
    public void setInvertedIndex(searchApplication.invertedIndex.InvertedIndex invertedIndex) {
        this.invertedIndex = invertedIndex;
    }

    /**
     * setter for asinQAMap.
     * @param asinQAMap
     */
    public void setAsinQAMap(HashMap<String, ArrayList<QA>> asinQAMap) {
        this.asinQAMap = asinQAMap;
    }

    /**
     * setter for qaList.
     * @param qaList
     */
    public void setQAList(ArrayList<QA> qaList) {
        this.qaList = qaList;
    }

    /**
     * Getter for attribute invertedIndex.
     * @return invertedIndex
     */
    public searchApplication.invertedIndex.InvertedIndex getInvertedIndex() {
        return invertedIndex;
    }

    /**
     * Getter for attribute asinQAMap which is an hashmap that maps asin with QA object
     * @return asinQAMap
     */
    public HashMap<String, ArrayList<QA>> getAsinQAMap() {
        return asinQAMap;
    }

    /**
     * getter for attribute qaList which is an ArrayList that stores QA obj.
     * @return qaList
     */
    public ArrayList<QA> getQaList() {
        return qaList;
    }
}
