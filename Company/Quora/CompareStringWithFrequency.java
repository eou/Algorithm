import java.util.*;

// 比较两个 string，只有小写字母
// 每个 stirng 内部可以任意换位置，所以位置不重要。每个 string 内部两个 letter 出现的频率也可以互换，所以这题只需要两个 string 每个 frequency 出现的次数要一样
class Solution {
    public boolean CompareStringWithFrequency(String str1, String str2) { 
        if (str1.length() != str2.length()) {
            return false;
        }

        Map<Character, Integer> map1 = new HashMap<>();
        for (Character c : str1.toCharArray()) {
            map1.put(c, map1.getOrDefault(c, 0) + 1);
        }
        Map<Character, Integer> map2 = new HashMap<>();
        for (Character c : str2.toCharArray()) {
            map2.put(c, map2.getOrDefault(c, 0) + 1);
        }

        for (Character c : map1.keySet()) {
            if (!map2.containsKey(c)) {
                return false;
            }
        }
        for (Character c : map2.keySet()) {
            if (!map2.containsKey(c)) {
                return false;
            }
        }

        Map<Integer, Integer> countS1 = new HashMap<>();
        for (Integer freq : map1.values()) {
            countS1.put(freq, countS1.getOrDefault(freq, 0) + 1);
        }

        Map<Integer, Integer> countS2 = new HashMap<>();
        for (Integer freq : map1.values()) {
            countS2.put(freq, countS2.getOrDefault(freq, 0) + 1);
        }

        for (int freq : countS1.keySet()) {
            if (countS1.get(freq) != countS2.get(freq)) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) { 
        Solution s = new Solution();
        System.out.println(s.CompareStringWithFrequency("babzccc", "abbzczz"));
        System.out.println(s.CompareStringWithFrequency("babzcccm", "bbazzczl"));
    }
}
