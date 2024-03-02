package geo.wealth.riddle.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class Riddle {

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

}
