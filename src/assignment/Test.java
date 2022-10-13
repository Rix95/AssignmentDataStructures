package assignment;

public class Test {
    public static void main(String[] args) {
        Sentence sentence1 = new Sentence("hello the the the world.");
        Sentence sentence2 = new Sentence("how are you");
        Sentence sentence3 = new Sentence("i am very very good");
        Sentence sentence4 = new Sentence("i am way way better");
        Word.addWord("the",sentence1);
        for (Word word : Word.listOfAllWords){
            System.out.println(word.toString());
        }
        Word.addWord("the",sentence1);
        Word.addWord("the",sentence1);
        System.out.println(sentence1.HighestFrequencyInSentenceValue);
        System.out.println(sentence1.wordsWithHighestFrequencyInSentence);
        Word.addWord("lol",sentence1);
        Word.addWord("lol",sentence1);
        System.out.println(sentence1.HighestFrequencyInSentenceValue);
        System.out.println(sentence1.wordsWithHighestFrequencyInSentence);
        Word.addWord("lol",sentence1);
        System.out.println(sentence1.HighestFrequencyInSentenceValue);
        System.out.println(sentence1.wordsWithHighestFrequencyInSentence);
        Word.addWord("lol",sentence1);
        System.out.println(sentence1.HighestFrequencyInSentenceValue);
        System.out.println(sentence1.wordsWithHighestFrequencyInSentence);
    }
}

