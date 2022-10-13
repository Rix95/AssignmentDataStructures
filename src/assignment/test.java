package assignment;

public class test {
    public static void main(String[] args) {
        Sentence sentence1 = new Sentence("hello the world.");
        Sentence sentence2 = new Sentence("how are you");
        Sentence sentence3 = new Sentence("i am very very good");
        Sentence sentence4 = new Sentence("i am way way better");
        Word.addWord("the",sentence1);
        System.out.print(sentence1.wordsWithHighestFrequencyInSentence);
    }
}

