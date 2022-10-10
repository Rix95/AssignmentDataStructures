package assignment;
import java.io.*;
import java.sql.Array;
import java.util.ArrayList;


public class Main {
    public static void main(String[] args) throws IOException {
        ArrayList<String> arrayLines = parseLines("src/data/tiny1.txt");
        ArrayList<Word> wordSentence = new ArrayList<>();

        for(String s: arrayLines){
            System.out.println(s);
            System.out.println("///////////");
        }

    }

    public static ArrayList<String> parseLines(String textFile) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(textFile));
        String line;
        ArrayList<String> arrayLines = new ArrayList<>();
        while ((line = bufferedReader.readLine()) != null) {
           //System.out.println(line);
           //System.out.println("/////////////////////");
            arrayLines.add(new String(line));
        }
        return arrayLines;
    }

    public static ArrayList<Sentence> parseSentences(ArrayList<String> arrayLines){



    }
}
