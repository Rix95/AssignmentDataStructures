import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;


public class WordParser {
    public static void main(String[] args) throws IOException {
        //Initiate the program, parse words from file.
        Sentence.parseWords(args[0]);
        //1 & 2
        createFile("nthRanking", args[1], "1", "1");
        createFile("nthRanking",args[1], "2", "3");
        //3
        createFile("wordHighestFreqSentence", args[1], "3");
        //4, 5, 6
        createFile("sentencesWithMaxWord",args[1], "4",  "the");
        createFile("sentencesWithMaxWord",args[1], "5",  "of");
        createFile("sentencesWithMaxWord",args[1], "6",  "was");
        //7, 8, 9
        createFile("sentencesWithMaxPhrase",  args[1], "7",  "but the");
        createFile("sentencesWithMaxPhrase",  args[1], "8",  "it was");
        createFile("sentencesWithMaxPhrase",  args[1], "9",  "in my");
        System.out.println("9 output files created successfully.");
    }

    //Creates a file based on the requested elements and function name
    public static void createFile(String function, String outputName, String ... parameters ) throws IOException {
        //writer to create files
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputName+parameters[0] + ".txt")));

        switch (function) {
            //1-2 problem
            case ("nthRanking") -> {
                for (String words : Word.getNthMostFrequentWord(Integer.parseInt(parameters[1]))) {
                    writer.write(words + "\n");
                }
            }
            //3 problem
            case ("wordHighestFreqSentence") -> {
                for(Sentence sentence : Sentence.getSentencesWithHighestFrequentWords()){
                    for (String wordInSentence : sentence.getListOfWordsWithHighestFrequencyInSentence()){
                        writer.write(wordInSentence + "\n");
                    }
                }
            }
            //4-6
            case ("sentencesWithMaxWord") -> {
                for (String maxWordSentence : Sentence.getSentencesWithMaximumWordOccurrences(parameters[1])){
                writer.write(maxWordSentence + "\n");
                }
            }

            //7-9
            case ("sentencesWithMaxPhrase") -> {
                for (String maxSubstringSentence : Sentence.getSentencesWithMaximumSubstringOccurrences(parameters[1])) {
                    writer.write(maxSubstringSentence + "\n") ;
                }
            }
            default -> System.out.println("Method not found");
        }
        writer.close();
    }
}

// sentence class to hold frequency data to get outputs.
class Sentence {

    private final String sentence;
    protected static ArrayList<Sentence> listOfSentences= new ArrayList<>();
    private final ArrayList<ArrayList<Object>> listOfWordsWithFrequencyInSentence = new ArrayList<>();
    private final ArrayList<String> listOfWordsWithHighestFrequencyInSentence = new ArrayList<>();
    private int highestWordFrequencyValueInSentence = 0;


    //Constructor for sentence class
    public Sentence(String sentence){
        this.sentence = sentence;
    }

    // Method to parse lines from file.
    private static ArrayList<String> parseLines(String textFile) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(textFile));
        String line;
        ArrayList<String> arrayLines = new ArrayList<>();
        while ((line = bufferedReader.readLine()) != null) {
            arrayLines.add(line.toLowerCase());
        }
        return arrayLines;
    }
    // Method to parse sentences from lines.
    private static void parseSentences(ArrayList<String> arrayLines) {

        StringBuilder sentence = new StringBuilder();
        for (String s : arrayLines) {
            for (int i = 0; i < s.length(); i++) {
                if(Character.isWhitespace(s.charAt(i))) {
                    if (sentence.length() != 0) {
                        sentence.append(s.charAt(i));
                    }
                }
                else if (s.charAt(i) == '.') {
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
    // Method to parse words from sentences.
    protected static void parseWords(String textFile) throws IOException {
        parseSentences(parseLines(textFile));
        StringBuilder wordBuilder = new StringBuilder();

        for (Sentence sentence : Sentence.listOfSentences){
            for(int i = 0; i < sentence.toString().length(); i++){
                if(Character.isWhitespace(sentence.toString().charAt(i))) {
                    Sentence.addWord(wordBuilder.toString(), sentence);
                    wordBuilder = new StringBuilder();
                }
                else{
                    wordBuilder.append(sentence.toString().charAt(i));
                }
            }
            if(wordBuilder.length() != 0){
                Sentence.addWord(wordBuilder.toString(), sentence);
                wordBuilder = new StringBuilder();
            }
        }
        System.out.println(Word.listOfAllWords.size() + " Unique Words parsed successfully from " + Sentence.listOfSentences.size() + " sentences.");
    }

    //Method to add word to list of all words, increase frequency if already found.
    private static void addWord(String newWord, Sentence currentSentence) {
        Word wordToAdd =  findWordInListOfAllWords(newWord);
        if (wordToAdd == null) {
            wordToAdd = new Word(newWord);
        }
        //If it already exists we should increase its total frequency.
        else{
            wordToAdd.totalWordFrequency++;
        }
        currentSentence.addWordWithFrequencyInSentence(wordToAdd);
    }

    //Method to add words to sentence, update their respective frequency.
    private void addWordWithFrequencyInSentence(Word word){

        ArrayList<Object> wordWithFrequency = findWordWithFrequencyInSentence(word);
        if (wordWithFrequency == null) {
            listOfWordsWithFrequencyInSentence.add(new ArrayList<>(Arrays.asList(word, 1)));
            //Condition if the highest sentence frequency is only 1
            if (highestWordFrequencyValueInSentence <= 1) {
                highestWordFrequencyValueInSentence = 1;
            }
        } else {
            int currentFreq = (int) wordWithFrequency.get(1) + 1;
            wordWithFrequency.set(1, currentFreq);
            //Update Frequencies and wordsWithHighestFrequency!
            //If the current freq is higher, then start list again.
            updateHighestFrequencyInSentence(currentFreq, word);
        }

    }

    //Helper method to update the highest frequency as needed.
    private void updateHighestFrequencyInSentence(int currentFreq, Word word){
        if (currentFreq > highestWordFrequencyValueInSentence) {
            highestWordFrequencyValueInSentence = currentFreq;
            listOfWordsWithHighestFrequencyInSentence.clear();
            listOfWordsWithHighestFrequencyInSentence.add(word + ":" + currentFreq + ":" + sentence);
        } else if (currentFreq == highestWordFrequencyValueInSentence) {   //If it is the same just add
            listOfWordsWithHighestFrequencyInSentence.add(word + ":" + currentFreq + ":" + sentence);
        }
    }

    // Method to find words in list of all words.
    private static Word findWordInListOfAllWords(String wordString){
        for (Word word : Word.listOfAllWords) {
            if (word.toString().equals(wordString)) {
                return word;
            }
        }
        return null;
    }

    // Method to find word frequency in current sentence.
    private ArrayList<Object> findWordWithFrequencyInSentence(Word wordToBeFound){
        for (ArrayList<Object> wordWithFrequency : listOfWordsWithFrequencyInSentence) {
            if (wordWithFrequency.get(0) == wordToBeFound) {
                return wordWithFrequency;
            }
        }
        return null;
    }

    // Method to find substring frequency in current sentence.
    private static int findSubstringFrequency(String sentence, String substring){
        int frequency = 0;
        int sentencePointer = 0;
        int substringPointer = 0;
        while(sentencePointer <= (sentence.length() - substring.length())){
            if(substringPointer== substring.length()){
                frequency++;
                substringPointer =0;
            }
            else if (sentence.charAt(sentencePointer) == substring.charAt(substringPointer)){
                sentencePointer++;
                substringPointer++;
            }
            else {
                sentencePointer++;
                substringPointer = 0;
            }
        }
        return frequency;
    }

    //Getter method for words with the highest frequency in sentence.
    protected ArrayList<String> getListOfWordsWithHighestFrequencyInSentence(){
        return listOfWordsWithHighestFrequencyInSentence;
    }
    // finds sentences with max word occurrences

    //Getter method for words with the highest frequency in sentence.
    protected static ArrayList<Sentence> getSentencesWithHighestFrequentWords() {
        ArrayList<Sentence> sentencesWithHighestWordFrequency = new ArrayList<>();
        int highest = 0;
        for (Sentence sentence : Sentence.listOfSentences) {
            if (sentence.highestWordFrequencyValueInSentence > highest) {
                highest = sentence.highestWordFrequencyValueInSentence;
                sentencesWithHighestWordFrequency.clear();
                sentencesWithHighestWordFrequency.add(sentence);
            } else if (sentence.highestWordFrequencyValueInSentence == highest) {
                sentencesWithHighestWordFrequency.add(sentence);
            }
        }
        return sentencesWithHighestWordFrequency;
    }

    //Getter method for sentences with maximum occurrences of certain word.
    protected static ArrayList<String> getSentencesWithMaximumWordOccurrences(String word){
        Word wordToFind = findWordInListOfAllWords(word);
        ArrayList<String> maximumOccurrences = new ArrayList<>();
        int maxFrequency = 0;
        for(Sentence sentence : listOfSentences){
            //use findWord.... to retrieve the word and its frequency, then compare to the current max frequency, if its bigger clear the list and start adding again updating maxFrequency value
          //  System.out.print(sentence);
            ArrayList<Object> existingWord = sentence.findWordWithFrequencyInSentence(wordToFind);
            if (existingWord != null){
                int currFrequency = (int)existingWord.get(1);
                if(currFrequency > maxFrequency){
                    maxFrequency = currFrequency;
                    maximumOccurrences.clear();
                    maximumOccurrences.add(word + ":" + currFrequency + ":" + sentence);
                } else if(currFrequency == maxFrequency){
                    maximumOccurrences.add(word + ":" + currFrequency + ":" + sentence);
                }
            }
        }
        return maximumOccurrences;
    }

    //Getter method for sentences with maximum occurrences of certain substring.
    public static ArrayList<String> getSentencesWithMaximumSubstringOccurrences(String substring)  {
        int highestFrequency = 0;
        int currentFrequency;
        ArrayList<String> sentencesWithHighestSubstringFrequency = new ArrayList<>();

        for(Sentence sentence: listOfSentences){
            currentFrequency = findSubstringFrequency(sentence.toString(), substring);
            if (currentFrequency > highestFrequency){
                highestFrequency = currentFrequency;
                sentencesWithHighestSubstringFrequency.clear();
                sentencesWithHighestSubstringFrequency.add(substring + ":" + currentFrequency + ":" + sentence);
            }
            else if(currentFrequency == highestFrequency){
                sentencesWithHighestSubstringFrequency.add(substring + ":" + currentFrequency + ":" + sentence);
            }
        }
        return sentencesWithHighestSubstringFrequency;
    }

    //Overriding toString method.
    @Override
    public String toString () {
        return this.sentence;
    }


}
class Word implements Comparable<Word>{

    private final String word;
    protected int totalWordFrequency;
    protected static ArrayList<Word> listOfAllWords = new ArrayList<>();
    public Word (String word) {
        this.word = word;
        totalWordFrequency = 1;
        //sentencesWithWord.add(sentence); not being used atm
        listOfAllWords.add(this);
    }
    public static ArrayList<String> getNthMostFrequentWord(int ranking) {
        //int counter = 0; Not being used atm
        ArrayList<String> words = new ArrayList<>();
        PriorityQueue<Word> priorityQueue = new PriorityQueue<>();
        priorityQueue.addAll(listOfAllWords);

        int currentFreq = 0;
        if (priorityQueue.peek() != null) {
            currentFreq = priorityQueue.peek().totalWordFrequency;
        }
        //<5,5,5,3,1>
        //Until we reach to our desired ranking
        int rankingFrequency;
        while (ranking != 1 && !priorityQueue.isEmpty()) {
            if (priorityQueue.peek().totalWordFrequency == currentFreq) {
                priorityQueue.poll();
            } else {
                ranking--;
                currentFreq = priorityQueue.peek().totalWordFrequency;

            }

        }
        assert priorityQueue.peek() != null;
        rankingFrequency = priorityQueue.peek().totalWordFrequency;
        //Once We take words with higher priority we proceed to add the ones we desire.
        //Then we poll the last element and add it, after finding one with a smaller value we end the loop
        while(priorityQueue.peek() != null && currentFreq == priorityQueue.peek().totalWordFrequency){
            words.add(priorityQueue.poll().toString() + ":" + rankingFrequency);
        }
        return words;

    }
    @Override
    public int compareTo(Word word){
        return word.totalWordFrequency > this.totalWordFrequency ? 1 : -1;
    }
    @Override
    public String toString() {
        return word;
    }
}
