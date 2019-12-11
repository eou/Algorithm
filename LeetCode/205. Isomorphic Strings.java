// 205. Isomorphic Strings
class Solution {
    public boolean isIsomorphic(String s, String t) {
        if (s == null || t == null || s.length() != t.length()) {
            return false;
        }
        
        // 1 map is not enough
        Map<Character, Character> maps = new HashMap<>();
        Map<Character, Character> mapt = new HashMap<>();
        // actually 3 situations:
        // 1. cs and ct both exist in map, check the two direction mapping
        // 2. only cs or ct exist in one map, false
        // 3. either of them exist in two maps, save into map
        for (int i = 0; i < s.length(); i++) {
            char cs = s.charAt(i);
            char ct = t.charAt(i);
            maps.putIfAbsent(cs, ct); 
            mapt.putIfAbsent(ct, cs);
            // binjection
            // "ab" <=> "aa"
            if (maps.get(cs) != ct || mapt.get(ct) != cs) {
                return false;
            }
        }
        return true;
    }
}

// use array other than map
class Solution {
    public boolean isIsomorphic(String s, String t) {
        int[] m1 = new int[256];
        int[] m2 = new int[256];
        for (int i = 0; i < s.length(); ++i) {
            // previous mapping
            if (m1[s.charAt(i)] != m2[t.charAt(i)]) {
                return false;
            }
            // start from 1
            m1[s.charAt(i)] = i + 1;
            m2[t.charAt(i)] = i + 1;
        }
        return true;
    }
}