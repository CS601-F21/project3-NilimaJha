package searchApplication.invertedIndex;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * InvertedIndex that maps alphanumeric word of a string to its documentId.
 * @author nilimajha
 */
public class InvertedIndex {
    private HashMap<String, HashMap<Integer, Integer>> wordDocMap= new HashMap<>();
    private static final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

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
        lock.writeLock().lock();
        try {
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
        } finally {
            lock.writeLock().unlock();
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
        lock.readLock().lock();
        try {
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
        } finally {
            lock.readLock().unlock();
        }
    }

    /**
     * Returns a hashmap that contains the word in its text .
     * @param key
     * @return
     */
    public HashMap<Integer, Integer> get (String key) {
        lock.readLock().lock();
        try {
            return wordDocMap.get(key);
        } finally {
            lock.readLock().unlock();
        }
    }
}
