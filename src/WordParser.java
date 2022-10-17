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
        //writer to create files
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputName+parameters[0] + ".txt")));

        switch (function) {
            //1-2 problem
            case "nthRanking" -> {
                for (String words : Word.nthMostFrequentWord(Integer.parseInt(parameters[1]))) {
                writer.write(words + "\n");
                }
                writer.close();
            }
            //3 problem
            case "wordHighestFreqSentence" -> {
                for(Sentence sentence : Sentence.getSentencesWithMostFrequentWords()){
                    for (String wordInSentence : sentence.wordsWithHighestFrequencyInSentence){
                    writer.write(wordInSentence + "\n");
                    }
                    writer.close();
                }
            }
            //4-6
            case "sentencesWithMaxWord" -> {
                for (String maxWordSentence : Sentence.getSentencesWithMaximumWordOccurrences(parameters[1])){
                    writer.write(maxWordSentence + "\n");
                }
                writer.close();
            }
            case "sentencesWithMaxPhrase" -> System.out.println("Coming soon");
            default -> System.out.println("Method not found");

        }
    }
}

class Sentence {
    // sentence class to hold strings and array lists
    private final String sentence;
    private ArrayList<ArrayList<Object>> wordWithFrequencyInSentenceList = new ArrayList<>();
    protected int HighestFrequencyInSentenceValue = 0;
    protected ArrayList<Word> wordsWithHighestFrequencyInSentence1 = new ArrayList<>();
    protected ArrayList<String> wordsWithHighestFrequencyInSentence = new ArrayList<>();
    protected static ArrayList<Sentence> listOfSentences= new ArrayList<>();

    //Constructor for sentence class
    public Sentence(String sentence){
        this.sentence = sentence;
    }

    // constructor for array list parseLines and new bufferedReader to read from other files while they are full
    public static ArrayList<String> parseLines(String textFile) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(textFile));
        String line;
        ArrayList<String> arrayLines = new ArrayList<>();
        while ((line = bufferedReader.readLine()) != null) {
            arrayLines.add(line.toLowerCase());
        }
        return arrayLines;
    }
    // constructor for parseSentences to read through each sentence in the arraylist
    public static void parseSentences(ArrayList<String> arrayLines) {

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
    // constructor for parseWords to read words and add them to a stringBuilder if they meet certain requirements
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
                wordsWithHighestFrequencyInSentence1.add(word);
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
                wordsWithHighestFrequencyInSentence.add(word + ":" + currentFreq + ":" + sentence);
            } else if (currentFreq == HighestFrequencyInSentenceValue) {   //If it is the same just add
                wordsWithHighestFrequencyInSentence.add(word + ":" + currentFreq + ":" + sentence);
            }
        }
    }
    // constructor to find certain words
    public static Word findWordInListOfAllWords(String wordString){
        for (Word word : Word.listOfAllWords) {
            if (word.toString().equals(wordString)) {
                return word;
            }
        }
        return null;
    }
    // finds words and their frequency
    public ArrayList<Object> findWordWithFrequencyInSentence(Word wordToBeFound){
        for (ArrayList<Object> wordWithFrequency : wordWithFrequencyInSentenceList) {
            if (wordWithFrequency.get(0) == wordToBeFound) {
                return wordWithFrequency;
            }
        }

        return null;
    }

    // arrayList with the most frequent words
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

    // finds sentences with max word occurrences
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

    public static int findSubstringFrequency(String sentence, String substring){
       int frequency = 0;
        int ptr1 = 0;
       int ptr2 = 0;
       while(ptr1 <= (sentence.length() - substring.length())){
           //if substring is found
           if(ptr2== substring.length()){
               frequency++;
               ptr2 =0;
           }
           else if (sentence.charAt(ptr1) == substring.charAt(ptr2)){
               ptr1++;
               ptr2++;
           }
           else
               ptr1++;
           ptr2 = 0;
       }
       return frequency;
    }

    public static ArrayList<String> findHighestFrequency(String substring){
        int highestFrequency = 0;
        int currentFrequency;
         ArrayList<String> list = new ArrayList<>();

        for(Sentence sentence: listOfSentences){
            currentFrequency = findSubstringFrequency(sentence.toString(), substring);
            if (currentFrequency > highestFrequency){
                highestFrequency = currentFrequency;
                list.clear();
                list.add(substring + ":" + currentFrequency + ":" + sentence);
            }
            else if(currentFrequency == highestFrequency){
                list.add(substring + ":" + currentFrequency + ":" + sentence);
            }
        }
        return list;
        }
    }
/// ranks most frequent words
class Word implements Comparable<Word>{

    private static int rankingFrequency;
    private final String word;
    protected int totalWordFrequency;
    protected static ArrayList<Word> listOfAllWords = new ArrayList<>();
    public Word (String word) {
        this.word = word;
        totalWordFrequency = 1;
        //sentencesWithWord.add(sentence); not being used atm
        listOfAllWords.add(this);
    }
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
