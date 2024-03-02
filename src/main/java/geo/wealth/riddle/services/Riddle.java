package geo.wealth.riddle.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class Riddle {

    public static final String A = "A";
    public static final String I = "I";
    public static final String REGEX_LETTERS = "^[a-zA-Z]*$";
    private final Dictionary dictionary;


    public void findWords(String word) throws IOException {

        TreeMap<String, List<String>> wordsAlphabetic = dictionary.getDictionary();
        Set<String> foundWords = new HashSet<>();

        for (int i = 1; i < word.length(); i++) {

            //left
            String left = word.substring(0, word.length() - i);
            List<String> leftWords = wordsAlphabetic.get(String.valueOf(left.charAt(0)));
            if (leftWords.contains(left)) {
                foundWords.add(left);
            }

            //right
            String right = word.substring(i);
            List<String> rightWords = wordsAlphabetic.get(String.valueOf(right.charAt(0)));
            if (rightWords.contains(right)) {
                foundWords.add(right);
            }
        }

        foundWords.forEach(System.out::println);
    }

    public void findWordsByRecursion(String word) throws IOException {
        if (word.isBlank()) {
            //do something
            return;
        }

        if (!word.matches(REGEX_LETTERS)) {
            //do something
            System.out.println("some exception");
            return;
        }

        TreeMap<String, List<String>> wordsAlphabetic = dictionary.getDictionary();
        Set<String> foundWords = new HashSet<>();

        recursion(word, foundWords);
        Set<String> realWords = checkWordExist(foundWords, wordsAlphabetic);

        realWords.forEach(System.out::println);
    }

    private void recursion(String word, Set<String> words) {
        if (!word.isEmpty()) {
            words.add(word);

            recursion(word.substring(1), words);
            recursion(word.substring(0, word.length() - 1), words);
        }
    }

    private Set<String> checkWordExist(Set<String> foundWords, TreeMap<String, List<String>> dictionary) {
        var realWorlds = new TreeSet<String>();
        for (var word : foundWords) {
            List<String> rightWords = dictionary.get(String.valueOf(word.charAt(0)));
            if (rightWords.contains(word)) {
                realWorlds.add(word);
            }
        }

        addExcludedLetterFromDictionary(foundWords, realWorlds, A);
        addExcludedLetterFromDictionary(foundWords, realWorlds, I);

        return realWorlds;
    }

    private static void addExcludedLetterFromDictionary(Set<String> foundWords, TreeSet<String> realWorlds, String letter) {
        if (foundWords.contains(letter)) {
            realWorlds.add(letter);
        }
    }
}
