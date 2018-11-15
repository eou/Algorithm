/**
 * csvEncoderDecoder.java
 * 原题是 Airbnb 的高频，此题为 Facebook 的变形，简化了一些规则
 * [abc, cde] -> abc;cde
 * [a"bc, cde] -> a""bc;cde
 * [a;bc, cde] -> "a;bc";cde
 * ["a;bc, cde] -> """a;bc";cde
 */
import java.util.*;

class csvEncoderDecoder {
    // if there is a semicolon ; in the string, add ""
    // if there is a single quote in the string, add " before the single quote
    // add ; between two string
    public String csvEncoder(String[] input) {
        StringBuilder result = new StringBuilder();
        boolean hasSemi = false;
        for(String s : input) {
            StringBuilder strBuilder = new StringBuilder();
            hasSemi = false;
            for(char c : s.toCharArray()) {
                if(c == ';') {
                    hasSemi = true;
                }
                if(c == '"') {
                    strBuilder.append('"');
                }
                strBuilder.append(c);
            }
            if(hasSemi) {
                strBuilder.insert(0, '"');
                strBuilder.append('"');
            }
            strBuilder.append(';');
            result.append(strBuilder);
        }
        if(result.length() > 0){
            result.deleteCharAt(result.length() - 1);
        }
        
        return result.toString();
    }

    // decoder略繁琐
    public String[] csvDecoder(String str) {
        List<String> list = new ArrayList<>();
        int start = 0;
        // 每次从 start 出发，找到当前字符串的结尾
        for(int i = 0; i < str.length(); i++) {
            start = i;
            // 没有双引号包围说明里面没有分号，正常解码
            if(str.charAt(start) != '"') {
                while(i < str.length() && str.charAt(i) != ';') {
                    i++;
                }
                list.add(format(str.substring(start, i)));
            } else {
                // 最外围有双引号的时候，说明内部有分号，所以遇到分号时，判断是否是结尾必须保证分号前的双引号是成对的
                int quoteNum = 0;
                while(i < str.length()) {
                    if(str.charAt(i) == ';' && quoteNum % 2 == 0) {
                        break;
                    } else if (str.charAt(i) == '"') {
                        quoteNum++;
                    }
                    i++;
                }
                // "xxx"; 从 start + 1 开始，到 i - 2 结束 
                list.add(format(str.substring(start + 1, i - 1)));
            }
        }
        
        String[] result = new String[list.size()];
        for(int i = 0; i < result.length; i++) {
            result[i] = list.get(i);
        }
        
        return result;
    }

    // 解码当前字符串，str 不包括两头的双引号
    private static String format(String str) {
        StringBuilder strBuilder = new StringBuilder();
        // 额外处理的情况就是遇到两个双引号，只输出一个
        boolean isQuote = false;
        for(char c : str.toCharArray()) {
            if(c == '"') {
                if(!isQuote){
                    isQuote = true;
                    continue;
                } else {
                    strBuilder.append('"');
                    isQuote = false;
                }
            } else {
                strBuilder.append(c);
            }
        }

        return strBuilder.toString();
    }

    public static void main(String[] args) {
        String[] input = { "\"a;b;;c", "c\"\"\"de;" };
        String res = new csvEncoderDecoder().csvEncoder(input);
        System.out.println(res);
        
        String[] output = new csvEncoderDecoder().csvDecoder(res);
        for (String s : output) {
            System.out.println(s);
        }

    }
}