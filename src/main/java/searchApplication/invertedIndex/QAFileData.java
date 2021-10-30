package searchApplication.invertedIndex;

import java.util.ArrayList;
import java.util.HashMap;

public class QAFileData {
    private InvertedIndex InvertedIndex = new InvertedIndex();
    private HashMap<String, ArrayList<QA>> asinQAMap = new HashMap<>();
    private ArrayList<QA> qaList = new ArrayList<>();

    public void setInvertedIndex(searchApplication.invertedIndex.InvertedIndex invertedIndex) {
        InvertedIndex = invertedIndex;
    }

    public void setAsinQAMap(HashMap<String, ArrayList<QA>> asinQAMap) {
        this.asinQAMap = asinQAMap;
    }

    public void setQAList(ArrayList<QA> qaList) {
        this.qaList = qaList;
    }

    public searchApplication.invertedIndex.InvertedIndex getInvertedIndex() {
        return InvertedIndex;
    }

    public HashMap<String, ArrayList<QA>> getAsinQAMap() {
        return asinQAMap;
    }

    public ArrayList<QA> getQaList() {
        return qaList;
    }
}
