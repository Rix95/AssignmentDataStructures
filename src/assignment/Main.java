package assignment;
import java.io.*;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws IOException {
        ArrayList<String> arrayLines = parseLines("src/data/tiny2.txt");
        ArrayList<Sentence> arraySentences = parseSentences(arrayLines);
        parseWords(arraySentences);
        
        
//        for (Word word : Word.listOfAllWords){
//            System.out.println(word.toString());
//    }

        ArrayList<Sentence> coolersentences = new ArrayList<>();
        int highest = 0;
        for(Sentence sentence : arraySentences){
            if (sentence.HighestFrequencyInSentenceValue > highest) {
                highest = sentence.HighestFrequencyInSentenceValue;
                coolersentences.clear();
                coolersentences.add(sentence);
            }
            else if(sentence.HighestFrequencyInSentenceValue == highest) {
                coolersentences.add(sentence);
            }
        }

        for(Sentence wayCooler : coolersentences){
            for (Word wordy : wayCooler.wordsWithHighestFrequencyInSentence){
                System.out.println(wordy.toString());
                System.out.println(wayCooler.HighestFrequencyInSentenceValue);
                System.out.println(wayCooler.toString());
            }
        }

        for(String words : Word.nthMostFrequentWord(1)){
            System.out.println(words);
        }
        System.out.println(Word.rankingFrequency);

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

    public static ArrayList<Sentence> parseSentences(ArrayList<String> arrayLines) {
        StringBuilder sentence = new StringBuilder();
        ArrayList<Sentence> arraySentences = new ArrayList<>();
        for (String s : arrayLines) {
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) == '.') {
                    arraySentences.add(new Sentence(sentence.toString()));
                    sentence = new StringBuilder();
                } else {
                    sentence.append(s.charAt(i));
                }
            }
            if (sentence.length() != 0) {
                arraySentences.add(new Sentence(sentence.toString()));
                sentence = new StringBuilder();
            }
        }
        return arraySentences;
    }

    public static ArrayList<Word> parseWords(ArrayList<Sentence> sentences) {
        StringBuilder word = new StringBuilder();

        ArrayList<Word> words = new ArrayList<>();

        for (Sentence sentence : sentences){
            for(int i = 0; i < sentence.toString().length(); i++){
                //System.out.println(s.charAt(i));
                if(Character.isWhitespace(sentence.toString().charAt(i))) {
                    if (word.length() != 0) {
                        Word.addWord(word.toString(), sentence);
                        word = new StringBuilder();
                    }
                }
                else
                    word.append(sentence.toString().charAt(i));
            }
            if(word.length() != 0){
                Word.addWord(word.toString(), sentence);
                word = new StringBuilder();
            }
        }
        return words;
    }
}
