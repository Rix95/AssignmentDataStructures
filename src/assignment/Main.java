package assignment;

import java.io.*;
import java.sql.Array;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        ArrayList<Sentence> arraySentence = parseSentences("src/data/tiny1.txt");
        ArrayList<Word> wordSentence = new ArrayList<>();

//        for (int i=0; i < arraySentence.size(); i++){
//            System.out.println(arraySentence.get(i).getSentence());
//            System.out.println("///////////");
//        }

        for (Sentence sentence : arraySentence){
            System.out.println(sentence.getSentence());
            System.out.println("///////////");
        }

    }

    public static ArrayList<Sentence> parseSentences(String textFile) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(textFile));
        String line;
        ArrayList<Sentence> arraySentence = new ArrayList<>();
        while ((line = bufferedReader.readLine()) != null) {
           //System.out.println(line);
           //System.out.println("/////////////////////");
            arraySentence.add(new Sentence(line));
        }
        return arraySentence;
    }

}
