package searchApplication.invertedIndex;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class InvertedIndex {
    private HashMap<String, HashMap<Integer, Integer>> wordDocMap= new HashMap<>();

    /**
     * method createAndAddIndex()
     * extracts the alphanumeric words from the input String and
     * maps it with the documentId of the input string
     * which maps it to the Frequency of the word in that particular docID.
     *
     * @param docID DocumentID of the document from which the input String belongs.
     * @param str   String from which words are
     */
    public void createAndAddIndex(int docID, String str) {

        String[] stringArray = str.split(" ");
        for(String word: stringArray) {
            //removing all non-alphanumeric characters and converting the string to lower case.
            word = word.replaceAll("[^A-Za-z0-9]", "").toLowerCase();
            int frequency = 1;
            //word exists in InvertedIndex
            if(wordDocMap.containsKey(word)) {
                //docID already exists for that word, update frequency
                if(wordDocMap.get(word).containsKey(docID)) {
                    frequency = wordDocMap.get(word).get(docID) + 1;
                }
                wordDocMap.get(word).put(docID, frequency);
            }else {
                //word is not in the InvertedIndex. so, adding it.
                HashMap<Integer, Integer> docIDFreqMap = new HashMap<>();
                docIDFreqMap.put(docID, frequency);
                wordDocMap.put(word, docIDFreqMap);
            }
        }
    }

    /**
     * method getDocIDsForWord() returns
     * a sorted list of docId that contains the given word.
     * sorting is done on the basis of the frequency of the word in the documents.
     *
     * @param word
     * @return sortedDocIDList
     */
    public ArrayList<Integer> getDocIDsForWord(String word) {
        ArrayList<Integer> sortedDocIDList = new ArrayList<>();
        word = word.toLowerCase();
        if (wordDocMap.containsKey(word)) {
            HashMap<Integer, Integer> DocIDFreqMap = this.wordDocMap.get(word);
            for (HashMap.Entry<Integer, Integer> entry : DocIDFreqMap.entrySet()) {
                if (sortedDocIDList.isEmpty()) {
                    sortedDocIDList.add(entry.getKey());
                }else{
                    // sorting logic: docIDs Sorted in Descending Order of Frequency of that words occurrence
                    int insertAt = 0; // insertAt index in the sorted DocID List
                    for (Integer docID : sortedDocIDList) {
                        // if current entry DocID Frequency > words frequency, we insert the
                        if (entry.getValue() > DocIDFreqMap.get(docID)) {
                            break;
                        } else if (entry.getValue().equals(DocIDFreqMap.get(docID))){
                            // if words frequency are same
                            // we are looking at DocID(Line Number) for sort order
                            if (entry.getKey() < docID) {
                                break;
                            }
                        }
                        insertAt++;
                    }
                    sortedDocIDList.add(insertAt, entry.getKey());
                }
            }
        }
        return sortedDocIDList;
    }

    /**
     * method getDocIDsForPartialWord() returns
     * a sorted list of docId that contains the given partial word.
     * sorting is done on the basis of the docID.
     *
     * @param partialWord
     * @return partialWordDocIds
     */
    public ArrayList<Integer> getDocIDsForPartialWord(String partialWord){
        partialWord = partialWord.toLowerCase();
        ArrayList<Integer> partialWordDocIDs = new ArrayList<>();
        // Iterating over entire InvertedIndex.
        for (HashMap.Entry<String, HashMap<Integer, Integer>> eachWordDocPair : this.wordDocMap.entrySet()) {
            String word = eachWordDocPair.getKey();
            if (word.contains(partialWord)) {
                // Iterating over the docIDs and frequency map of the word that contains the partial word.
                HashMap<Integer, Integer> docIDFreqMap = eachWordDocPair.getValue();
                for (HashMap.Entry<Integer, Integer> eachDocIDFreqPair: docIDFreqMap.entrySet()) {
                    if (partialWordDocIDs.isEmpty()) {
                        partialWordDocIDs.add(eachDocIDFreqPair.getKey());
                    }else {
                        if (!partialWordDocIDs.contains(eachDocIDFreqPair.getKey())) {
                            partialWordDocIDs.add(eachDocIDFreqPair.getKey());
                        }
                    }
                }
            }
        }
        Collections.sort(partialWordDocIDs);
        return partialWordDocIDs;
    }

    public boolean containsKey (String key) {
        if (wordDocMap.containsKey(key)) {
            return true;
        } else {
            return false;
        }
    }

    public HashMap<Integer, Integer> get (String key) {
        return wordDocMap.get(key);
    }
}
