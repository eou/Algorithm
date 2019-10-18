// Get all palindromes permutation string from input string
class Solution {
    public List<String> allPalindromes(String s) { 
        List<String> res = new ArrayList<>();
        if (s == null || s.length() == 0) {
            return res;
        }

        Map<Character, Integer> map = new HashMap<>();
        for (Character c : s.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }

        int oddChar = 0;
        Character oddCharacter = null;
        for (Character c : map.keySet()) {
            if (map.get(c) % 2 == 1) {
                oddChar++;
                oddCharacter = c;
                if (oddChar > 1) {
                    return res;
                }
            }
        }

        if (oddChar == 1) {
            String cur = "" + oddCharacter;
            dfs(s, map, res, cur);
        } else {
            dfs(s, map, res, "");
        }
        
        return res;
    } 

    public void dfs(String s, Map<Character, Integer> map, List<String> res, String cur) {
        if (s.length() == cur.length()) {
            res.add(cur);
            return;
        }

        for (Character c : map.keySet()) {
            int frequency = map.get(c);
            if (frequency > 1) {
                String next = new String(cur);
                next = next + c;
                next = c + next;
                map.put(c, map.get(c) - 2);
                dfs(s, map, res, next);
                // backtracking
                map.put(c, frequency);
            }
        }
    }

    public static void main(String[] args)  { 
       Solution s = new Solution();
       for (String str : s.allPalindromes("baba")) {
           System.out.println(str);
       }

       for (String str : s.allPalindromes("cccbaba")) {
           System.out.println(str);
       }
    }
}
