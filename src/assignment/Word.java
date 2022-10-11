package assignment;

import java.util.ArrayList;

public class Word {

    StringBuilder word;
    private int totalWordFrequency;
    protected ArrayList<Sentence> sentencesWithWord = new ArrayList<>();
    public Word (StringBuilder word, Sentence sentence) {
        this.word = word;
        totalWordFrequency = 1;
        sentencesWithWord.add(sentence);
    }

    public int getTotalFrequency() {
        return totalWordFrequency;
    }

    public void incrementWordFrequency() {
        totalWordFrequency++;
    }

    public void addSentence(Sentence sentence){
    //For marissa
    }

    public Sentence getSentenceWithHighestFrequency(ArrayList<Sentence> sentenceWord){
        //This currently does not work
        Sentence sentence = new Sentence( new StringBuilder());
        return sentence;
    }

    @Override
    public String toString() {
        return word.toString();
    }



}
