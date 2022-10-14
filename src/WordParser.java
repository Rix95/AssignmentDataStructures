import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;


public class WordParser {
    public static void main(String[] args) throws IOException {
        //Used to keep count of files created and their distinct name.
        //Initiate the program

        Sentence.parseWords(args[0]);
        printOutputFiles(args[1]);

//        ArrayList<Sentence> coolersentences = new ArrayList<>();
//        int highest = 0;
//        for(Sentence sentence : Sentence.listOfSentences){
//            if (sentence.HighestFrequencyInSentenceValue > highest) {
//                highest = sentence.HighestFrequencyInSentenceValue;
//                coolersentences.clear();
//                coolersentences.add(sentence);
//            }
//            else if(sentence.HighestFrequencyInSentenceValue == highest) {
//                coolersentences.add(sentence);
//            }
//        }
//
//        for(Sentence wayCooler : coolersentences){
//            for (Word wordy : wayCooler.wordsWithHighestFrequencyInSentence){
//                System.out.println(wordy.toString() + ":" + wayCooler.HighestFrequencyInSentenceValue + ":" + wayCooler.toString());
//            }
//        }
    }
    public static void printOutputFiles(String outputName){
        int textFileCounter = 1;
        //1 & 2
        createFile("nthRanking", "1");
        createFile("nthRanking", "3");
        //3


        //4, 5, 6


        //7, 8, 9

    }
    //Creates a file based on the question
    public static void createFile(String function,String ... parameters ){

        switch (function) {
            case "nthRanking" :
                for(String words : Word.nthMostFrequentWord(Integer.parseInt(parameters[0]))){
                    System.out.print(words);
                }
                System.out.println(":" + Word.rankingFrequency);
                break;
            case "wordHighestFreqSentence":  System.out.println("hi");
                break;
            case "sentencesWithMaxWord":  System.out.println("hi");
                break;
            case "sentencesWithMaxPhrase": System.out.println("hi");
                break;

            default: System.out.println("Method not found");
                break;
        }


    }








}

class Sentence {

    private String sentence;
    protected ArrayList<ArrayList<Object>> wordFrequencyInSentenceList = new ArrayList<>();
    protected int HighestFrequencyInSentenceValue = 0;
    protected ArrayList<Word> wordsWithHighestFrequencyInSentence = new ArrayList<>();
    protected static ArrayList<Sentence> listOfSentences= new ArrayList<>();

    //Constructor for sentence class
    public Sentence(String sentence){
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


    //Return Sentence
    public String getSentence(){
        return this.sentence;
    }


    //TO BE POTENTIALLY DELETED, NOT IN USE
    public int getWordFrequency(Word word){
        for(ArrayList<Object> wordInSentence : wordFrequencyInSentenceList){
            if(wordInSentence.get(0) == word){
                //Word wordFound = (Word)wordInSentence.get(0);
                return (int)wordInSentence.get(1);
            }
        }
        return 0;
    }

    public static ArrayList<String> parseLines(String textFile) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(textFile));
        String line;
        ArrayList<String> arrayLines = new ArrayList<>();
        while ((line = bufferedReader.readLine()) != null) {
            arrayLines.add(new String(line.toLowerCase()));
        }
        return arrayLines;
    }

    public static void parseSentences(ArrayList<String> arrayLines) throws IOException {

        StringBuilder sentence = new StringBuilder();
        for (String s : arrayLines) {
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) == '.') {
                    listOfSentences.add(new Sentence(sentence.toString()));
                    sentence = new StringBuilder();
                } else {
                    sentence.append(s.charAt(i));
                }
            }
            if (sentence.length() != 0) {
                listOfSentences.add(new Sentence(sentence.toString()));
                sentence = new StringBuilder();
            }
        }
    }

    public static void parseWords(String textFile) throws IOException {
        parseSentences(parseLines(textFile));
        StringBuilder wordBuilder = new StringBuilder();

        ArrayList<Word> words = new ArrayList<>();

        for (Sentence sentence : Sentence.listOfSentences){
            for(int i = 0; i < sentence.toString().length(); i++){
                //System.out.println(s.charAt(i));
                if(Character.isWhitespace(sentence.toString().charAt(i))) {
                    if (wordBuilder.length() != 0) {
                        Word.addWord(wordBuilder.toString(), sentence);
                        wordBuilder = new StringBuilder();
                    }
                }
                else
                    wordBuilder.append(sentence.toString().charAt(i));
            }
            if(wordBuilder.length() != 0){
                Word.addWord(wordBuilder.toString(), sentence);
                wordBuilder = new StringBuilder();
            }
        }
    }

    public ArrayList<ArrayList<Object>> getWordFrequencyInSentenceList(){
        return wordFrequencyInSentenceList;
    }

    @Override
    public String toString() {
        return this.sentence;
    }


}

class Word implements Comparable<Word>{

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
