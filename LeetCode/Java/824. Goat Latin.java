// 824. Goat Latin
class Solution {
    public String toGoatLatin(String S) {
        StringBuilder result = new StringBuilder();
        Set<Character> vowel = new HashSet<>();
        vowel.add('a');
        vowel.add('e');
        vowel.add('i');
        vowel.add('o');
        vowel.add('u');
        
        int index = 1;
        for(String s : S.split(" ")) {
            StringBuilder tmp = new StringBuilder(s);
            if(!vowel.contains(Character.toLowerCase(tmp.charAt(0)))) {
                tmp.append(tmp.charAt(0));
                tmp.deleteCharAt(0);
            }
            tmp.append("ma");
            for(int i = 0; i < index; i++) {
                tmp.append("a");
            }
            result.append(tmp);
            result.append(" ");
            index++;
        }
        
        return result.toString().trim();
    }
}

class Solution {
    public String toGoatLatin(String S) {
        StringBuilder result = new StringBuilder();
        // 也可以用 String 来判断，但是稍慢
        String vowel = "aeiouAEIOU";

        int index = 1;
        for (String s : S.split(" ")) {
            StringBuilder tmp = new StringBuilder(s);
            String first = tmp.substring(0, 1);
            if (!vowel.contains(first)) {
                tmp.append(tmp.charAt(0));
                tmp.deleteCharAt(0);
            }
            tmp.append("ma");
            for (int i = 0; i < index; i++) {
                tmp.append("a");
            }
            result.append(tmp);
            result.append(" ");
            index++;
        }

        return result.toString().trim();
    }
}