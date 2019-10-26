import java.util.*;

// 输入一组 words 和一组 valid letters，判断有多少个 words 是 valid
// input: a = "Hello, my dear friend!", b = ['h', 'e', 'l', 'o', 'm']
// output: 1 题目是键盘坏了，只剩下b中的字母按键和所有的数字和符号案件能用，同时shift键是好的， 所以可以切换大小写。问a中的单词有几个可以用当前坏掉的键盘打出来。 
class Solution {
    public int ValidWords(String[] words, Character[] letters) {
        int res = 0;
        Set<Character> set = new HashSet<>();
        for (Character c : letters) {
            set.add(Character.toLowerCase(c));
        }

        for (String str : words) {
            boolean isValid = true;
            for (Character c : str.toCharArray()) {
                if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
                    if (!set.contains(Character.toLowerCase(c))) {
                        isValid = false;
                        break;
                    }
                }
            }
            if (isValid) {
                res++;
            }
        }
        return res;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        String[] words = new String[] { "hEllo##", "This^^" };
        Character[] letters = new Character[] { 'h', 'e', 'l', 'o', 't', 'h', 's' };
        System.out.println(s.ValidWords(words, letters));
    }
}
