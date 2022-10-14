package assignment;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class Word implements Comparable<Word>{

    static int rankingFrequency;
    String word;
    private int totalWordFrequency;
    protected ArrayList<Sentence> sentencesWithWord = new ArrayList<>();
    protected static ArrayList<Word> listOfAllWords = new ArrayList<>();
    public Word (String word) {
        this.word = word;
        totalWordFrequency = 1;
        //sentencesWithWord.add(sentence); not being used atm
        listOfAllWords.add(this);
    }

    public static void addWord(String newWord, Sentence currentSentence){
        boolean found = false;
        Word wordToAdd = null;
        for(Word word : listOfAllWords){

            //If it already exists we should increase its total frequency.
            if (word.toString().equals(newWord)){
                word.totalWordFrequency++;
                found = true;
                wordToAdd = word;
            }
            //If word is not in list of words.

        }
        if(found == false){
            wordToAdd = new Word(newWord);
        }
        //add to the current list of words for current sentence.
        currentSentence.addWordWithFrequency(wordToAdd);
    }

    public int getTotalFrequency() {
        return totalWordFrequency;
    }

    public void incrementTotalWordFrequency() {
        totalWordFrequency++;
    }
    
    public void addSentence(Sentence sentence){
    //For marissa
    }

    public Sentence getSentencesWithHighestFrequency(ArrayList<Sentence> sentenceWord){
        //This currently does not work
        Sentence sentence = new Sentence( new String());
        return sentence;
    }


    @Override
    public int compareTo(Word word){
        return word.totalWordFrequency > this.totalWordFrequency ? 1 : -1;
    }
    public static ArrayList<String> nthMostFrequentWord(int ranking) {
        //int counter = 0; Not being used atm
        ArrayList<String> words = new ArrayList<>();
        PriorityQueue<Word> priorityQueue = new PriorityQueue<>();
        for(Word word : listOfAllWords) {
            priorityQueue.add(word);
        }
        int currentFreq = priorityQueue.peek().totalWordFrequency;
        //<5,5,5,3,1>
        //Until we reach to our desired ranking
        while (ranking != 1 && !priorityQueue.isEmpty()) {
            if (priorityQueue.peek().totalWordFrequency == currentFreq) {
                rankingFrequency = priorityQueue.peek().totalWordFrequency;
                priorityQueue.poll();
            } else {
                ranking--;
                currentFreq = priorityQueue.peek().totalWordFrequency;

            }

        }
        rankingFrequency = priorityQueue.peek().totalWordFrequency;
        //Once We take words with higher priority we proceed to add the ones we desire.
        //Then we poll the last element and add it, after finding one with a smaller value we end the loop
        while(priorityQueue.peek() != null && currentFreq == priorityQueue.peek().totalWordFrequency){
            words.add(priorityQueue.poll().toString());
        }
        return words;

    }
    @Override
    public String toString() {
        return word.toString();
    }

}
