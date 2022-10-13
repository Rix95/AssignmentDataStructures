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
        boolean found = false;
        int currentFreq =1;
        for(ArrayList<Object> wordFreq : wordFrequencyInSentenceList) {
            if (wordFreq.get(0) == word) {
                currentFreq = (int)(wordFreq.get(1)) + 1;
                wordFreq.set(1, currentFreq);
                found = true;
                break;
                }

        }
        if(!found){
            wordFrequencyInSentenceList.add(new ArrayList<>(Arrays.asList(word, currentFreq)));
        }
        //Update Frequencies and wordsWithHighestFrequency!
        if(currentFreq > HighestFrequencyInSentenceValue){ //If current Frequency
            HighestFrequencyInSentenceValue = currentFreq;
            wordsWithHighestFrequencyInSentence.clear();
            wordsWithHighestFrequencyInSentence.add(word);
        } else if(currentFreq == HighestFrequencyInSentenceValue){  //If the word
            wordsWithHighestFrequencyInSentence.add(word);
        }
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
