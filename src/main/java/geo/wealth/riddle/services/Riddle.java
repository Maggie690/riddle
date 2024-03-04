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

        TreeMap<String, List<String>> wordsAlphabetic = dictionary.getDictionary();

        Set<String> foundWords = new HashSet<>();

        recursion(word, foundWords);

        Set<String> realWords = checkWordExist(foundWords, wordsAlphabetic);
        Set<String> matchedWords = getFirstMatches(word, realWords);

        long endTime = System.nanoTime();
        double elapsedTimeInSecond = calculateTimeInSeconds(startTime, endTime);

        return new RiddleDto(matchedWords, elapsedTimeInSecond);
    }

    private Set<String> getFirstMatches(String primaryWord, Set<String> words) {
        Set<String> matchedWords = new HashSet<>();

        int worldLength = primaryWord.length() - 1;

        while (worldLength > 0) {
            for (String w : words) {
                if (w.length() == worldLength) {
                    matchedWords.add(w);
                    break;
                }
            }
            worldLength--;
        }
        return matchedWords;
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

    private void addExcludedLetterFromDictionary(Set<String> foundWords, TreeSet<String> realWorlds, String letter) {
        if (foundWords.contains(letter)) {
            realWorlds.add(letter);
        }
    }

    private double calculateTimeInSeconds(long startTime, long endTime) {
        double elapsedTime = (endTime - startTime) / 1e6;
        return elapsedTime / ONE_SECOND;
    }
}
