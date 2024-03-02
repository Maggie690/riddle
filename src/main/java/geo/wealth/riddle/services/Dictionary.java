package geo.wealth.riddle.services;

import lombok.NoArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Service
@NoArgsConstructor
public class Dictionary {

    @Cacheable("dictionaryMap")
    public TreeMap<String, List<String>> getDictionary() throws IOException {
        List<String> dictionary = loadWords();

        var wordsAlphabetic = new TreeMap<String, List<String>>();
        for (String word : dictionary) {

            String letter = word.toUpperCase().substring(0, 1);
            if (!wordsAlphabetic.containsKey(letter)) {
                wordsAlphabetic.put(letter, new LinkedList<>());
            }
            wordsAlphabetic.get(letter).add(word);
        }
        return wordsAlphabetic;
    }

    @Cacheable("dictionary")
    private List<String> loadWords() throws IOException {
        URL url = new URL("https://raw.githubusercontent.com/Maggie690/riddle/main/src/main/resources/dictionary.txt");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream()))) {
            return reader.lines().skip(2).collect(Collectors.toList());
        }
    }
}
