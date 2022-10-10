package assignment;

public class Word {

    private String word;
    //Track globalFrequency
    private int globalFrequency = 0;

    public Word(String word){

        this.word = word;
    }

    public int getGlobalFrequency() {

        return globalFrequency;
    }



}
