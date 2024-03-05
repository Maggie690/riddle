package geo.wealth.riddle;

import geo.wealth.riddle.services.Dictionary;

import java.io.IOException;
import java.util.List;

public class Solution {

    public static final String A = "A";
    public static final String I = "I";
    private Dictionary dictionary;

    public Solution(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    public void findSubWords(String word) throws IOException {
        StringBuilder tempWord = new StringBuilder(word);

        int wordLength = tempWord.toString().length() - 1;
        while (wordLength > 0) {
            if (wordLength == 1 && (tempWord.toString().contains(A) || tempWord.toString().contains(I))) {
                System.out.println(tempWord.toString().contains(A) ? A : I);
                break;
            }

            List<String> words = dictionary.loadWords();
            for (String wordDictionary : words) {
                if (wordLength == wordDictionary.length() && tempWord.toString().contains(wordDictionary)) {
                    System.out.println(wordDictionary);
                    tempWord = new StringBuilder(wordDictionary);
                    break;
                }
            }
            wordLength--;
        }
    }
}
