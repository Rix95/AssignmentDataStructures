import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;


public class WordParser {
    public static void main(String[] args) throws IOException {
        //Initiate the program
        Sentence.parseWords(args[0]);
        //
        //String[] testCases = [[]]
        //1 & 2
        createFile("nthRanking", args[1], "1", "1");
        System.out.println("//////////2");
        createFile("nthRanking",args[1], "2", "3");
        System.out.println("//////////3");
        //3
        createFile("wordHighestFreqSentence", args[1], "3");
        System.out.println("//////////4-6");
        //4, 5, 6
        createFile("sentencesWithMaxWord",args[1], "4",  "the");
        createFile("sentencesWithMaxWord",args[1], "5",  "of");
        createFile("sentencesWithMaxWord",args[1], "6",  "was");
        System.out.println("//////////7-9");
        //7, 8, 9
        createFile("sentencesWithMaxPhrase",  args[1], "7",  "but the");
        createFile("sentencesWithMaxPhrase",  args[1], "8",  "it was");
        createFile("sentencesWithMaxPhrase",  args[1], "9",  "in my");
    }

    //Creates a file based on the requested elements and function name
    public static void createFile(String function, String outputName, String ... parameters ) throws IOException {
        //writer.txt to create files
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputName+parameters[0] + ".txt")));

        switch (function) {
            //problem 1-2, printing out the most frequent word along with its sentence and frequency
            case "nthRanking" -> {
                for (String words : Word.nthMostFrequentWord(Integer.parseInt(parameters[1]))) {
                    System.out.println(words + ":" + Word.rankingFrequency);
                    writer.write(Word.rankingFrequency);
                    writer.close();
                }
            }
            //Problem 3, printing out the sentence with highest frequency of a word
            case "wordHighestFreqSentence" -> {
                for(Sentence sentence : Sentence.getSentencesWithMostFrequentWords()){
                    for (Word wordInSentence : sentence.wordsWithHighestFrequencyInSentence){
                        System.out.println(wordInSentence + ":" + Sentence.getSentencesWithMostFrequentWords().get(0).HighestFrequencyInSentenceValue + ":" + sentence);
                    }
                }
            }
            // Printing out the sentences with the most words
            case "sentencesWithMaxWord" -> {
                for (String maxWordSentence : Sentence.getSentencesWithMaximumWordOccurrences(parameters[1])){
                    System.out.println(maxWordSentence);
                }
            }
            //printing out sentences with the max frequency of a phrase
            case "sentencesWithMaxPhrase" -> System.out.println("Coming soon");
            default -> System.out.println("Method not found");
        }
    }
}

class Sentence {
    // creating strings and arrayLists
    private final String sentence;
    private ArrayList<ArrayList<Object>> wordWithFrequencyInSentenceList = new ArrayList<>();
    protected int HighestFrequencyInSentenceValue = 0;
    protected ArrayList<Word> wordsWithHighestFrequencyInSentence = new ArrayList<>();
    protected static ArrayList<Sentence> listOfSentences= new ArrayList<>();

    //Constructor for sentence class
    public Sentence(String sentence){
        this.sentence = sentence;
    }

    // arrayList for parseLines that will save each line
    public static ArrayList<String> parseLines(String textFile) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(textFile));
        String line;
        ArrayList<String> arrayLines = new ArrayList<>();
        while ((line = bufferedReader.readLine()) != null) {
            arrayLines.add(line.toLowerCase());
        }
        return arrayLines;
    }
    // arrayList for parseSentences that will save each sentence
    public static void parseSentences(ArrayList<String> arrayLines) {
        // new StringBuilder to store sentences
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
    // parseWords, method to read each word
    public static void parseWords(String textFile) throws IOException {
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
    }

    //addWord, increase frequency if already found
    public static void addWord(String newWord, Sentence currentSentence) {
        Word wordToAdd =  findWordInListOfAllWords(newWord);
        if (wordToAdd == null) {
            wordToAdd = new Word(newWord);
        }
        //If it already exists we should increase its total frequency.
        else{
            wordToAdd.totalWordFrequency++;
        }
        currentSentence.addWordWithFrequency(wordToAdd);
    }

    //Core Function, this adds words, update frequency, also bind words to sentences
    public void addWordWithFrequency (Word word){

        ArrayList<Object> wordWithFrequency = findWordWithFrequencyInSentence(word);
        if (wordWithFrequency == null) {
            wordWithFrequencyInSentenceList.add(new ArrayList<>(Arrays.asList(word, 1)));
            //Condition if the highest sentence frequency is only 1
            if (HighestFrequencyInSentenceValue <= 1) {
                wordsWithHighestFrequencyInSentence.add(word);
                HighestFrequencyInSentenceValue = 1;
            }
        } else {
            int currentFreq = (int) wordWithFrequency.get(1) + 1;
            wordWithFrequency.set(1, currentFreq);

            //Update Frequencies and wordsWithHighestFrequency!
            //If the current freq is higher, then start list again.
            if (currentFreq > HighestFrequencyInSentenceValue) {
                HighestFrequencyInSentenceValue = currentFreq;
                wordsWithHighestFrequencyInSentence.clear();
                wordsWithHighestFrequencyInSentence.add(word);
            } else if (currentFreq == HighestFrequencyInSentenceValue) {   //If it is the same just add
                wordsWithHighestFrequencyInSentence.add(word);
            }
        }
    }
    // method to find specific words
    public static Word findWordInListOfAllWords(String wordString){
        for (Word word : Word.listOfAllWords) {
            if (word.toString().equals(wordString)) {
                return word;
            }
        }
        return null;
    }
    // method to find specific sentences and their frequency
    public ArrayList<Object> findWordWithFrequencyInSentence(Word wordToBeFound){
        for (ArrayList<Object> wordWithFrequency : wordWithFrequencyInSentenceList) {
            if (wordWithFrequency.get(0) == wordToBeFound) {
                return wordWithFrequency;
            }
        }

        return null;
    }

    // method to get the sentences with highest frequency and retrieve them from the Sentence array
    public static ArrayList<Sentence> getSentencesWithMostFrequentWords () {
        ArrayList<Sentence> sentencesWithHighestWordFrequency = new ArrayList<>();
        int highest = 0;
        for (Sentence sentence : Sentence.listOfSentences) {
            if (sentence.HighestFrequencyInSentenceValue > highest) {
                highest = sentence.HighestFrequencyInSentenceValue;
                sentencesWithHighestWordFrequency.clear();
                sentencesWithHighestWordFrequency.add(sentence);
            } else if (sentence.HighestFrequencyInSentenceValue == highest) {
                sentencesWithHighestWordFrequency.add(sentence);
            }
        }
        return sentencesWithHighestWordFrequency;
    }

    // method to get the sentences with max words
    public static ArrayList<String> getSentencesWithMaximumWordOccurrences (String word){
        Word wordToFind = findWordInListOfAllWords(word);
        ArrayList<String> maximumOccurrences = new ArrayList<>();
        int maxFrequency = 0;
        for(Sentence sentence : listOfSentences){
            //use findWord.... to retrieve the word and its frequency, then compare to the current max frequency, if its bigger clear the list and start adding again updtaing maxFrequency value
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

//    public static ArrayList<Sentence> getSentencesWithMaximumSubstrsingOcurrences(String substring)  {

//    }

    @Override
    public String toString () {
        return this.sentence;
    }
    }

class Word implements Comparable<Word>{

    // creating names for different variables and a new arrayList for all words
    protected static int rankingFrequency;
    private final String word;
    protected int totalWordFrequency;
    protected static ArrayList<Word> listOfAllWords = new ArrayList<>();
    public Word (String word) {
        this.word = word;
        totalWordFrequency = 1;
        //sentencesWithWord.add(sentence); not being used atm
        listOfAllWords.add(this);
    }
    // method to rank the most frequent words
    public static ArrayList<String> nthMostFrequentWord(int ranking) {
        //int counter = 0; Not being used atm
        ArrayList<String> words = new ArrayList<>();
        PriorityQueue<Word> priorityQueue = new PriorityQueue<>();
        priorityQueue.addAll(listOfAllWords);

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
    public int compareTo(Word word){
        return word.totalWordFrequency > this.totalWordFrequency ? 1 : -1;
    }
    @Override
    public String toString() {
        return word;
    }


}
