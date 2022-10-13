package assignment;

import java.util.ArrayList;
import java.util.Arrays;

public class Sentence {

    private StringBuilder sentence;
    private ArrayList<ArrayList<Object>> wordFrequencyInSentenceList = new ArrayList<>();
    private int HighestFrequencyInSentenceValue = 0;
    ArrayList<Word> wordsWithHighestFrequencyInSentence = new ArrayList<>();

    public Sentence(StringBuilder sentence){
        this.sentence = sentence;
    }

    public void addWordWithFrequency(Word word){

        ArrayList<Object> wordWithFrequency = findWordWithFrequency(word);
        if(wordWithFrequency == null){
            wordFrequencyInSentenceList.add(new ArrayList<>(Arrays.asList(word, 1)));
            //Condition if the highest sentence frequency is only 1
            if (HighestFrequencyInSentenceValue <= 1) {
                wordsWithHighestFrequencyInSentence.add(word);
                HighestFrequencyInSentenceValue = 1;
            }
        } else {
            int currentFreq = (int)wordWithFrequency.get(1) + 1;
            wordWithFrequency.set(1, currentFreq);

            //Update Frequencies and wordsWithHighestFrequency!
            //If the current freq is higher, then start list again.
            if(currentFreq > HighestFrequencyInSentenceValue){
                HighestFrequencyInSentenceValue = currentFreq;
                wordsWithHighestFrequencyInSentence.clear();
                wordsWithHighestFrequencyInSentence.add(word);
            } else if(currentFreq == HighestFrequencyInSentenceValue){   //If its the same just add
                wordsWithHighestFrequencyInSentence.add(word);
            }
        }
    }

    public ArrayList<Object> findWordWithFrequency(Word wordToBeFound) {
        for (ArrayList<Object> wordWithFrequency : wordFrequencyInSentenceList) {
            if (wordWithFrequency.get(0) == wordToBeFound) {
                return wordWithFrequency;
            }
        }
        return null;
    }
    public int getHighestFrequencyInSentenceValue(){
        return HighestFrequencyInSentenceValue;
    }


    //Method to check if a word is in a sentence

    //Return Sentence
    public StringBuilder getSentence(){
        return this.sentence;
    }

    public int getWordFrequency(Word word){
        for(ArrayList<Object> wordInSentence : wordFrequencyInSentenceList){
            if(wordInSentence.get(0) == word){
                //Word wordFound = (Word)wordInSentence.get(0);
                return (int)wordInSentence.get(1);
            }
        }
        return 0;
    }
    //This method will get the word/s with the highest frequency in a list,(1 or more)
//    public ArrayList<Word> getWordsWithHighestFrequency(){
//
////        for(ArrayList<Object> wordList : wordFrequencyInSentenceList){
////
////
////        }
//
//    }

    public ArrayList<ArrayList<Object>> getWordFrequencyInSentenceList(){
        return wordFrequencyInSentenceList;
    }



}
