// 151. Reverse Words in a String
public class Solution {
    // 用了栈辅助反转，空间复杂度高了一些
    // 可以不用栈，先反转整个String，然后单独反转每个单词，还可以实现in-place原地反转
    public String reverseWords(String s) {
        if(s == null || s.length() == 0) {
            return "";
        }
        
        Deque<String> stack = new ArrayDeque<>();
        String word = "";
        boolean isWord = false;
        for(Character c : s.toCharArray()) {
            if(c != ' ') {
                if(!isWord) {
                    isWord = true;
                }
                word += c;
            } else {
                if(isWord) {
                    stack.push(word);
                    word = "";
                    isWord = false;
                }
            }
        }
        // 注意处理最后一个单词
        if(isWord) {
            stack.push(word);
        }
        
        String result = "";
        while(!stack.isEmpty()) {
            result += stack.pop();
            result += " ";
        }
        
        return result.trim();
    }
}

public class Solution {
    // in-place版本，但是还要自己写char[]的翻转函数
    public String reverseWords(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }

        char[] str = s.toCharArray();
        reverse(str, 0, str.length - 1);

        int start = 0;
        for (int i = 0; i < str.length; i++) {
            if (str[i] != ' ') {
                if (start != 0) {
                    str[start] = ' ';
                    start++;
                }
                // 相当于一个把后面字符往前面移动的过程
                int j = i;
                while (j < str.length && str[j] != ' ') {
                    str[start] = str[j];
                    start++;
                    j++;
                }
                reverse(str, start - (j - i), start - 1);
                i = j;
            }
        }

        return new String(str, 0, start);
    }

    private void reverse(char[] str, int begin, int end) {
        for (; begin < end; begin++, end--) {
            char tmp = str[begin];
            str[begin] = str[end];
            str[end] = tmp;
        }
    }

}