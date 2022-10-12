package assignment;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class Word {

    StringBuilder word;
    private int totalWordFrequency;
    protected ArrayList<Sentence> sentencesWithWord = new ArrayList<>();
    protected static ArrayList<Word> ListOfAllWords = new ArrayList<>();
    public Word (StringBuilder word, Sentence sentence) {
        this.word = word;
        totalWordFrequency = 1;
        sentencesWithWord.add(sentence);
        ListOfAllWords.add(this);
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

    public Sentence getSentencesWithHighestFrequency(ArrayList<Sentence> sentenceWord){
        //This currently does not work
        Sentence sentence = new Sentence( new StringBuilder());
        return sentence;
    }

    @Override
    public String toString() {
        return word.toString();
    }

    public void nthMostFrequentWord(int n) {
        int counter = 0;
        ArrayList<String> words = new ArrayList<>();
        PriorityQueue<Word> priorityQueue = new PriorityQueue<>();
        for(Word word : ListOfAllWords) {
            priorityQueue.add(word);
        }
        int currentFreq = priorityQueue.peek().getTotalFrequency();

        while(n != 0) {

            if (priorityQueue.peek().getTotalFrequency() == currentFreq) {
                words.add(priorityQueue.poll().toString());
            }
            else
            {
                n--;
                words.clear();
                currentFreq = priorityQueue.peek().getTotalFrequency();

        }



        }
    }




}
