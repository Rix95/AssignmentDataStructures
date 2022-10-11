package assignment;

import java.util.ArrayList;

public class Word {

    StringBuilder word;
    private int totalWordFrequency;
    protected ArrayList<Sentence> sentenceWWord;
    public Word (StringBuilder word, Sentence sentence) {
        this.word = word;
        totalWordFrequency = 1;
    }

    public int getTotalFrequency() {
        return totalWordFrequency;
    }

    public void incrementWordFrequency() {
        totalWordFrequency++;
    }




}
