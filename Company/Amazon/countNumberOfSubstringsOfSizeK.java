import java.io.*;
import java.util.*;
// find_a_substring_of_size_K_such_that_there_is_exactly_one_character_that_is_repeated_once
class countNumberOfSubstringsOfSizeK {
    public static List<String> solution(String inputStream, int k) {
        Map<Character, Integer> map = new HashMap<>();
        List<String> result = new ArrayList<>();
        for (int i = 0; i + k < inputStream.length(); ++i) {
            int dup_cnt = 0;
            map.clear();
            String substr = inputStream.substring(i, i + k + 1);
            for (Character c : substr.toCharArray()) {
                map.put(c, map.getOrDefault(c, 0) + 1);
                if (map.get(c) > 1) {
                    dup_cnt++;
                }
            }
            if (dup_cnt == 1) {
                result.add(substr);
            }
        }

        return result;
    }

    public static void main(String[] args) {
        String input = "abcabc";
        for (String s : solution(input, 3)) {
            System.out.println(s);
        }
    }
}