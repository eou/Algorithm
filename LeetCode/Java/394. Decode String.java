// 394. Decode String
// 此题类似计算数学表达式，不用栈，用一个栈，用两个栈都能做
// 不用栈的思路就是脱括号然后递归计算括号内部字符串，DFS
// 用栈的思路是轮流保存数字和字母，遇到 ']' 就弹出一个数字和一个字符串进行叠加，很像表达式计算
class Solution {
    // 不用栈版本，本质是一层一层脱括号然后递归计算，只需考虑第一个字符是数字或者是字母，括号不管
    public String decodeString(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }

        // a2[c] or abc, start with letters
        if (Character.isLetter(s.charAt(0))) {
            int i = 0;
            while (i < s.length() && Character.isLetter(s.charAt(i))) {
                i++;
            }
            String s1 = s.substring(0, i);

            // it is impossible that a letter followed by a '[' or a digit
            String s2 = s.substring(i);
            return s1 + decodeString(s2);
        } else {
            // 2[c]3[b], start with numbers
            StringBuilder res = new StringBuilder();
            // get the repetition
            int i = 0;
            while (i < s.length() && Character.isDigit(s.charAt(i))) {
                i++;
            }
            int rep = Integer.parseInt(s.substring(0, i));

            int j = i;
            int parenthesis = 0;
            while (j < s.length()) {
                if (s.charAt(j) == '[') {
                    parenthesis++;
                }
                if (s.charAt(j) == ']') {
                    parenthesis--;
                    if (parenthesis == 0) {
                        break;
                    }
                }
                j++;
            }

            // remove the square brackets
            String s1 = decodeString(s.substring(i + 1, j));
            while (rep > 0) {
                res.append(s1);
                rep--;
            }
            return res.append(decodeString(s.substring(j + 1))).toString();
        }
    }
}

class Solution {
    // 另一个不用栈的递归版本，本质也是去括号
    public String decodeString(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                int digitStart = i;
                while (s.charAt(i) != '[') {
                    i++;
                }
                int num = Integer.parseInt(s.substring(digitStart, i));

                int parenthesis = 1;
                int letterStart = i + 1;
                i++;
                while (parenthesis != 0) {
                    if (s.charAt(i) == '[') {
                        parenthesis++;
                    } else if (s.charAt(i) == ']') {
                        parenthesis--;
                    }
                    i++;
                }
                i--;
                String str = decodeString(s.substring(letterStart, i));

                for (int j = 0; j < num; j++) {
                    result.append(str);
                }
            } else {
                result.append(c);
            }
        }

        return result.toString();
    }
}

class Solution {
    // 一个栈的版本，把字母和数字都当做字符串保存
    public String decodeString(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }

        StringBuilder result = new StringBuilder();
        Deque<StringBuilder> stack = new ArrayDeque<>();
        int i = 0;
        while(i < s.length()) {
            char c = s.charAt(i);
            // 由于只用一个栈，可能会混淆数字和字母，因此当遇到一个数字就必须将之前的字符串放入栈，然后找出整个数字字符串放入栈
            if(Character.isDigit(c)) {
                stack.push(result);

                int start = i;
                while(Character.isDigit(s.charAt(i))) {
                    i++;
                }
                // store the number as string in the stack
                stack.push(new StringBuilder(s.substring(start, i)));
            } else if(c == '[') {
                result = new StringBuilder();
                i++;
            } else if(c == ']') {
                int rep = Integer.parseInt((stack.pop()).toString());
                StringBuilder tmp = stack.pop();
                for (int j = 0; j < rep; j++) {
                    tmp.append(result);
                }
                result = tmp;
                i++;
            } else {
                result.append(c);
                i++;
            }
        }
        return result.toString();
    }
}

class Solution {
    // 两个栈的版本，一个存数字，一个存字母
    public String decodeString(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }

        Deque<Integer> intStack = new ArrayDeque<>();
        Deque<StringBuilder> strStack = new ArrayDeque<>();
        StringBuilder result = new StringBuilder();
        int k = 0;

        int i = 0;
        while( i < s.length()) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                // 由于数字和字母独立存储，可以一位一位计算
                k = k * 10 + (c - '0');
            } else if(c == '[') {
                intStack.push(k);
                strStack.push(result);
                result = new StringBuilder();
                k = 0;
                i++;
            } else if(c == ']') {
                StringBuilder tmp = result;
                result = strStack.pop();
                for (k = intStack.pop(); k > 0; k--) {
                    result.append(tmp);
                }
                i++;
            } else {
                result.append(c);
                i++;
            }
        }

        return result.toString();
    }
}

class Solution {
    // 两个栈的版本模仿一个栈的版本略做改动
    public String decodeString(String s) {
        if(s == null || s.length() == 0) {
            return "";
        }

        Deque<Integer> intStack = new ArrayDeque<>();
        Deque<StringBuilder> strStack = new ArrayDeque<>();
        StringBuilder result = new StringBuilder();
        int k = 0;

        int i = 0;
        while(i < s.length()) {
            char c = s.charAt(i);
            if(Character.isDigit(c)) {
                strStack.push(result);

                // 直接算出整个数字
                while(Character.isDigit(s.charAt(i))) {
                    k = k * 10 + (s.charAt(i) - '0');
                    i++;
                }
                intStack.push(k);
            } else if(c == '[') {
                result = new StringBuilder();
                k = 0;
                i++;
            } else if(c == ']') {
                StringBuilder tmp = result;
                result = strStack.pop();
                for (k = intStack.pop(); k > 0; k--) {
                    result.append(tmp);
                }
                i++;
            } else {
                result.append(c);
                i++;
            }
        }

        return result.toString();
    }
}