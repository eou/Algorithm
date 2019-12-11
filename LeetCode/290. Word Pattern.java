// 290. Word Pattern
class Solution {
    public boolean wordPattern(String pattern, String str) {
        if (pattern == null || str == null || pattern.length() == 0 || str.length() == 0) {
            return false;
        }
        
        Map<Character, Integer> map1 = new HashMap<>(); // string => previous position
        Map<String, Integer> map2 = new HashMap<>(); // string => previous position
        String[] words = str.split(" ");
        if (words.length != pattern.length()) {
            return false;
        }
        for (int i = 0; i < words.length; i++) {
            // unboxing from Integer to int
            if ((int)map1.getOrDefault(pattern.charAt(i), -1) != (int)map2.getOrDefault(words[i], -1)) {
                return false;
            }
            map1.put(pattern.charAt(i), i);
            map2.put(words[i], i);
        }
        
        return true;
    }
}

class Solution {
    public boolean wordPattern(String pattern, String str) {
        String[] words= str.split(" ");
        if (words.length!= pattern.length()) {
            return false;
        }
        
        // char <=> string
        Map<Character, String> map = new HashMap<>();
        for (int i = 0; i < words.length; i++) {
            char c = pattern.charAt(i);
            if (map.containsKey(c)) {
                if(!map.get(c).equals(words[i])) {
                    return false;
                }
            } else {
                // c must correspond to words[i]
                if (map.containsValue(words[i])) {
                    return false;
                }
                map.put(c, words[i]);
            }    
        }
        
        return true;
    }
}

class Solution {
    // "abba"
    // "dog dog dog dog"
    public boolean wordPattern(String pattern, String str) {
        // pattern means character a and b maybe the same or different in one String
        // 1. find pattern in pattern string
        // 2. use pattern on str string
        if (pattern == null || str == null || pattern.length() == 0 || str.length() == 0) {
            return false;
        }
        
        Map<Character, Set<Integer>> map = new HashMap<>();
        for (int i = 0; i < pattern.length(); i++) {
            char c = pattern.charAt(i);
            Set<Integer> set = map.containsKey(c) ? map.get(c) : new HashSet<>();
            set.add(i);
            map.put(c, set);
        }
        
        Set<Set<Integer>> set1 = new HashSet<>();
        for (Set<Integer> set : map.values()) {
            set1.add(set);
        }
        
        String[] words = str.split(" ");
        Map<String, Set<Integer>> map2 = new HashMap<>();
        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            Set<Integer> set = map2.containsKey(word) ? map2.get(word) : new HashSet<>();
            set.add(i);
            map2.put(word, set);
        }
        
        Set<Set<Integer>> set2 = new HashSet<>();
        for (Set<Integer> set : map2.values()) {
            set2.add(set);
        }
        
        for (Set<Integer> set : set1) {
            if (!set2.contains(set)) {
                return false;
            }
        }
        return true;
    }
}