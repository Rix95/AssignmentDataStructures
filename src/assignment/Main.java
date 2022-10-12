package assignment;
import java.io.*;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws IOException {
        ArrayList<String> arrayLines = parseLines("src/data/tiny1.txt");
        //       ArrayList<Word> wordSentence = new ArrayList<>();
//
//        for (String s : arrayLines) {
//            System.out.println(s);
//            System.out.println("///////////");
//        }

        ArrayList<Sentence> arraySentences = parseSentences(arrayLines);
        System.out.println(arraySentences.size());
        for (Sentence s : arraySentences) {
            System.out.println(s.getSentence());
        }
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
                    arraySentences.add(new Sentence(sentence));
                    sentence = new StringBuilder();
                } else {
                    sentence.append(s.charAt(i));
                }
            }
            if (sentence.length() != 0) {
                arraySentences.add(new Sentence(sentence));
                sentence = new StringBuilder();
            }

        }
        return arraySentences;
    }
}
