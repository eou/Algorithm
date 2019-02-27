import java.io.*;
import java.util.*;

// leetcode 819
class retrieveMostFrequentlyUsedWords {
    public List<String> solution1(String literatureText, String[] wordsToExclude) {
        List<String> results = new ArrayList<>();

        String lowerCaseText = literatureText.toLowerCase();
        String[] splitText = lowerCaseText.split("[^a-z]");
        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i < splitText.length; i++) {
            // delete empty string
            if (splitText[i].length() > 0) {
                map.put(splitText[i], map.getOrDefault(splitText[i], 0) + 1);
            }
        }
        for (int i = 0; i < wordsToExclude.length; i++) {
            wordsToExclude[i] = wordsToExclude[i].toLowerCase();
        }

        int mostFrequency = 0;
        for(String key : map.keySet()) {
            if (!Arrays.asList(wordsToExclude).contains(key)) {
                mostFrequency = mostFrequency < map.get(key) ? map.get(key) : mostFrequency;
            }
        }
        for (String key : map.keySet()) {
            if (map.get(key) == mostFrequency && !Arrays.asList(wordsToExclude).contains(key)){
                results.add(key);
            }
        }

        return results;
    }

    public static void main(String[] args) {
        retrieveMostFrequentlyUsedWords s = new retrieveMostFrequentlyUsedWords();
        String input = "Jack and Jill went to the market to buy bread and cheese. Cheese is Jack's and Jill's favority food.";
        String[] wordsToExclude = {"and", "he", "the", "to", "is", "Jack", "Jill"};
        List<String> results = s.solution1(input, wordsToExclude);
        for (String str : results) {
            System.out.println(str);
        }
    }
}