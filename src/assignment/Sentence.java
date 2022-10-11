package assignment;

import java.util.ArrayList;
import java.util.Arrays;

public class Sentence {

    private StringBuilder sentence;
    private ArrayList<ArrayList<Object>> wordsInSentence = new ArrayList<>();
    public Sentence(StringBuilder sentence){
        this.sentence = sentence;
    }

    public void addWordWithFrequency(Word word){
        boolean found = false;
        for(ArrayList<Object> wordFreq : wordsInSentence) {
            if (wordFreq.get(0).equals(word)) {
                int currentFreq = (int)(wordFreq.get(1));
                currentFreq++;
                wordFreq.set(1, currentFreq);
                found = true;
            }
            if (found){
                break;
            }
        }
        if(!found){
            wordsInSentence.add(new ArrayList<>(Arrays.asList(word, 1)));
        }


        }


    public StringBuilder getSentence(){
        return this.sentence;
    }

    public Word getWordsWithHighestFrequency(){
        //This method is a placeholder it does not produce intended result
        Word word = new Word(new StringBuilder(), new Sentence(this.sentence));
        return word;
    }
    public ArrayList<ArrayList<Object>> getWordsInSentence(){
        return wordsInSentence;
    }



}
