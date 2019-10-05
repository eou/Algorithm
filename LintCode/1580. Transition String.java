// 1580. Transition String
public class Solution {
    public boolean canTransfer(String startString, String endString) {
        // no need to transfer
        if (startString.equals(endString)) {
            return true;
        }
        
        // impossible
        if (startString.length() != endString.length()) {
            return false;
        }
        
        Map<Character, Character> map = new HashMap<>();
        for (int i = 0; i < startString.length(); i++) {
            Character start = startString.charAt(i);
            Character end = endString.charAt(i);
            map.putIfAbsent(start, end);
            // one character map with different characters
            if (map.get(start) != end) {
                return false;
            }
            
            // check infinite loop: a => b => c => a, but not c => c
            char c = start;
            while (map.containsKey(c) && map.get(c) != c) {
                c = map.get(c);
                if (c == start) {
                    return false;
                }
            }
        }
        
        return true;
    }
}

// use array not map
public class Solution {
    public boolean canTransfer(String startString, String endString) {
        // no need to transfer
        if (startString.equals(endString)) {
            return true;
        }
        
        // impossible
        if (startString.length() != endString.length()) {
            return false;
        }
        
        int[] letter = new int[26];
        Arrays.fill(letter, -1);   // 0 is used
        for (int i = 0; i < startString.length(); i++) {
            Character start = startString.charAt(i);
            Character end = endString.charAt(i);
            if (letter[start - 'a'] != -1 && letter[start - 'a'] != (end - 'a')) {
                return false;
            }
            letter[start - 'a']  = (end - 'a');
            
            // check loop a => b => c => a, but not self loop c => c
            char c = start;
            while (letter[c - 'a'] != -1 && letter[c - 'a'] != c - 'a') {
                c = (char)(letter[c - 'a'] + 'a');
                if (c == start) {
                    return false;
                }
            }
        }
        
        return true;
    }
}