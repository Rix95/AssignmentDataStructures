package assignment;

import java.util.ArrayList;

public class Word {

    StringBuilder word;
    private int totalWordFrequency;
    protected ArrayList<Sentence> sentencesWithWord = new ArrayList<>();
    protected static ArrayList<Word> ListOfAllWords = new ArrayList<>();
    public Word (StringBuilder word, Sentence sentence) {
        this.word = word;
        WordFrequency = 1;
        sentencesWithWord.add(sentence);
        ListOfAllWords.add(this);
    }

    public int getTotalFrequency() {
        return WordFrequency;
    }

    public void incrementWordFrequency() {
        totalWordFrequency++;
    }
    
    public void addSentence(Sentence sentence){
    //For marissa
    }

    public Sentence getSentencesWithHighestFrequency(ArrayList<Sentence> sentenceWord){
        //This currently does not work
        Sentence sentence = new Sentence( new StringBuilder());
        return sentence;
    }

    @Override
    public String toString() {
        return word.toString();
    }



}
