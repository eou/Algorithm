// https://leetcode.com/discuss/interview-experience/394242/amazon-sde1-portland-sep-2019-offer
import java.util.*;

class Solution {
    // Given array of sentences, return the set of words that are in one sentence but not all.
    public List<String> solution1(String[] sentences) {
        List<String> res = new ArrayList<>();
        if (sentences == null || sentences.length == 0) {
            return res;
        }

        // brute force is to for any words in one sentence, scan all other sentences to check if it exists in other senetences
        // a better idea is to collect all words' sentences of all sentences in a map/dictionary, and then scan the sentences one by one to check each word's owner's size
        Map<String, Set<Integer>> map = new HashMap<>();     // dic: word => frequency
        // store all words into this dic
        // 1. break each sentences
        // 2. store words
        for (int i = 0; i < sentences.length; i++) {
            String[] words = sentences[i].split(" ");
            for (String word : words) {
                Set<Integer> set = map.containsKey(word) ? map.get(word) : new HashSet<>();
                set.add(i);
                map.put(word, set);
            }
        }

        for (String word : map.keySet()) {
            if (map.get(word).size() == 1) {
                res.add(word);
            }
        }
        
        return res;
    }

    public List<String> solution2(String[] sentences) {
        List<String> res = new ArrayList<>();
        if (sentences == null || sentences.length == 0) {
            return res;
        }
        
        // use 2 sets to record target words and all checked words
        Set<String> results = new HashSet<>();
        Set<String> duplicated = new HashSet<>();
        for (int i = 0; i < sentences.length; i++) {
            String[] words = sentences[i].split(" ");
            Set<String> set = new HashSet<>(Arrays.asList(words));  // need to unique current sentence
            if (i == 0) {
                // do nothing for first sentence
                results = new HashSet<>(Arrays.asList(words));
            } else {
                for (String word : set) {
                    if (results.contains(word)) {
                        // duplicate word, need to remove
                        results.remove(word);
                        duplicated.add(word);
                    } else {
                        if (!duplicated.contains(word)) {
                            results.add(word);
                        }
                    }
                }
            }
        }

        for (String s : results) {
            res.add(s);
        }
        
        return res;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        String[] sentences = new String[]{"My dog eats food", "She eats food too", "My dog food is good good"};
        List<String> res = s.solution2(sentences);
        for (int i = 0; i < res.size(); i++) {
            System.out.println(res.get(i));
        }
    }
}