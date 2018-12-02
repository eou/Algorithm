/**
 * Reconstruct Word List.java
 * 将输入的单词保持任意两个单词的先后顺序后打乱，输出所有相同长度的单词字符串，如 aba => aba, bab
 */
import java.util.*;
import java.io.*;

class Solution {
    public List<List<String>> reconstructWordList(List<String> words) {
        List<List<String>> results = new ArrayList<>();
        if(words == null || words.size() == 0) {
            return results;
        }

        // word => successor
        Map<String, Set<String>> map = new HashMap<>();
        for(int i = 0; i < words.size() - 1; i++) {
            Set<String> set;
            if(!map.containsKey(words.get(i))) {
                set = new HashSet<>();
            } else {
                set = map.get(words.get(i));
            }
            set.add(words.get(i + 1));
            map.put(words.get(i), set);
        }

        for(String word : map.keySet()) {
            List<String> tmp = new ArrayList<>();
            tmp.add(word);
            helper(word, map, words.size(), tmp, results);
        }

        return results;
    }

    public void helper(String word, Map<String, Set<String>> map, int len, List<String> cur, List<List<String>> results) {
        if(cur.size() == len) {
            results.add(new ArrayList<>(cur));
            return;
        }
        
        if(map.containsKey(word)) {
            for (String successor : map.get(word)) {
                cur.add(successor);
                helper(successor, map, len, cur, results);
                cur.remove(cur.size() - 1);
            }
        }
    }

    public static void main(String[] args) {
        List<String> input = Arrays.asList("A", "B", "C", "A", "E");

        String str = "he is good and he and great";
        List<String> input2 = Arrays.asList(str.split(" "));

        Solution s = new Solution();
        for(List<String> result : s.reconstructWordList(input2)) {
            for(String word : result) {
                System.out.print(word + ", ");
            }
            System.out.println();
        }
    }
}