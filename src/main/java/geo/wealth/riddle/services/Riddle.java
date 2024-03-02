package geo.wealth.riddle.services;

import geo.wealth.riddle.dto.RiddleDto;
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

    // 1 second = 1_000_000_000 nano seconds
    public static final int ONE_SECOND = 1_000_000_000;

    private final Dictionary dictionary;

    private TreeMap<String, List<String>> wordsAlphabetic = new TreeMap<>();

    public void findWords(String word) throws IOException {

        if (wordsAlphabetic.isEmpty()) {
            wordsAlphabetic = dictionary.getDictionary();
        }

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

    public RiddleDto findWordsByRecursion(String word) throws IOException {
        long startTime = System.nanoTime();

        if (word.isBlank()) {
            //do something
            return null;
        }

        if (!word.matches(REGEX_LETTERS)) {
            //do something
            System.out.println("some exception");
            return null;
        }

        if (wordsAlphabetic.isEmpty()) {
            wordsAlphabetic = dictionary.getDictionary();
        }

        Set<String> foundWords = new HashSet<>();

        recursion(word, foundWords);

        Set<String> realWords = checkWordExist(foundWords, wordsAlphabetic);
        double elapsedTimeInSecond = calculateTimeInSeconds(startTime);

        return new RiddleDto(realWords, elapsedTimeInSecond);
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
            List<String> dictionaryByAlphabeticLetter = dictionary.get(String.valueOf(word.charAt(0)));
            if (!dictionaryByAlphabeticLetter.isEmpty() && dictionaryByAlphabeticLetter.contains(word)) {
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

    private static double calculateTimeInSeconds(long startTime) {
        long endTime = System.nanoTime();
        double elapsedTime = (endTime - startTime) / 1e6;
        return elapsedTime / ONE_SECOND;
    }
}
