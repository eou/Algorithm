// 49. Group Anagrams
// 时间复杂度为 O(NK)，where N is the length of strs, and K is the maximum length of a string in strs
class Solution {
    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List> map = new HashMap<>();
        for (String str : strs) {
            String feature = extractFeature(str);
            List<String> list = map.getOrDefault(feature, new ArrayList<>());
            list.add(str);
            map.put(feature, list);
        }

        List<List<String>> res = new ArrayList<>();
        for (List<String> list : map.values()) {
            res.add(list);
        }
        return res;
    }

    private String extractFeature(String str) {
        int[] freq = new int[26];
        for (int i = 0; i < str.length(); i++) {
            freq[str.charAt(i) - 'a']++;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 26; i++) {
            sb.append((char) ('a' + i));
            sb.append(freq[i]);
        }

        return sb.toString();
    }
}

// faster using long but not string
class Solution {
    public List<List<String>> groupAnagrams(String[] strs) {
        Map<Long, List> map = new HashMap<>();
        for (String str : strs) {
            long feature = extractFeature(str);
            List<String> list = map.getOrDefault(feature, new ArrayList<>());
            list.add(str);
            map.put(feature, list);
        }

        List<List<String>> res = new ArrayList<>();
        for (List<String> list : map.values()) {
            res.add(list);
        }
        return res;
    }

    private long extractFeature(String str) {
        int[] freq = new int[26];
        for (int i = 0; i < str.length(); i++) {
            freq[str.charAt(i) - 'a']++;
        }

        long res = 0, base = 1;
        for (int i = 0; i < 26; i++) {
            res += (base * freq[i]);
            base *= 10;
        }
        return res;
    }
}

// O(NKlogK)
class Solution {
    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();
        for (String str : strs) {
            char[] c = str.toCharArray();
            Arrays.sort(c);
            String str1 = String.valueOf(c);
            List<String> list = map.containsKey(str1) ? map.get(str1) : new ArrayList<>();
            list.add(str);
            map.put(str1, list);
        }

        List<List<String>> res = new ArrayList<>();
        for (List<String> list : map.values()) {
            res.add(list);
        }
        return res;
    }
}