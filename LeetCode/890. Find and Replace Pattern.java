// 890. Find and Replace Pattern
// use set to store pattern
class Solution {
    public List<String> findAndReplacePattern(String[] words, String pattern) {
        List<String> list = new ArrayList<>();
        if (words == null || words.length == 0 || pattern.length() == 0) {
            return list;
        }
        // need to store pattern
        // store groups which seperately contains same characters' index
        // (0), (1,2)
        Set<Set<Integer>> patternTemplate = getPattern(pattern);
        
        // compare pattern in words array
        for (String word : words) {
            Set<Set<Integer>> wordPattern = getPattern(word);
            boolean isMatch = true;
            for (Set<Integer> set : wordPattern) {
                if (!patternTemplate.contains(set)) {
                    isMatch = false;
                    break;
                }
            }
            if (isMatch) {
                list.add(word);
            }
        }
        
        return list;
    }
    
    public Set<Set<Integer>> getPattern(String s) {
        // use map to seperate different groups
        // store groups into a big hashset
        Map<Character, Set<Integer>> map = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            Set<Integer> set = map.containsKey(c) ? map.get(c) : new HashSet<>();
            set.add(i);
            map.put(c, set);
        }
        
        Set<Set<Integer>> pattern = new HashSet<>();
        for (Set<Integer> set : map.values()) {
            pattern.add(set);
        }
        return pattern;
    }
}

// use array to store pattern
class Solution {
    public List<String> findAndReplacePattern(String[] words, String pattern) {
        int[] p = getPattern(pattern);
        List<String> res = new ArrayList<String>();
        for (String word : words) {
            if (Arrays.equals(getPattern(word), p)) {
                res.add(word);
            }
        }
        return res;
    }

    public int[] getPattern(String s) {
        HashMap<Character, Integer> map = new HashMap<>();
        int n = s.length();
        int[] res = new int[n];
        for (int i = 0; i < n; i++) {
            map.putIfAbsent(s.charAt(i), map.size());   // index is i-th distinct character in string
            res[i] = map.get(s.charAt(i));
        }
        return res;
    }
}

class Solution {
    public List<String> findAndReplacePattern(String[] words, String pattern) {
        List<String> res = new LinkedList<>();
        for (String word : words) {
            int[] p = new int[26], s = new int[26];
            boolean same = true;
            for (int i = 0; i < word.length(); i++) {
                if (s[word.charAt(i) - 'a'] != p[pattern.charAt(i) - 'a']) {
                    same = false;
                    break;
                } else {
                    // start from 1 because default value in array is 0
                    s[word.charAt(i) - 'a'] = p[pattern.charAt(i) - 'a'] = i + 1;  // i + 1 not i
                }
            }
            if (same) {
                res.add(word);
            }
        }

        return res;
    }
}