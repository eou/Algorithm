import java.io.*;
import java.util.*;

class longestPalindromicSubstring {
    public boolean isPalindromicString(String substr) {
        for(int i = 0, j = substr.length() - 1; i < j; i++, j--) {
            if (substr.charAt(i) != substr.charAt(j)) {
                return false;
            }
        }
        return true;
    }

    // O(n^3), brute force
    public String solution1(String inputStream) {
        String result = "";
        int maxLength = 0;
        for (int i = 0; i < inputStream.length(); i++) {
            for (int j = i; j < inputStream.length(); j++) {
                if (inputStream.charAt(i) == inputStream.charAt(j)) {
                    if (isPalindromicString(inputStream.substring(i, j + 1))) {
                        if (maxLength < (j - i + 1)) {
                            maxLength = j - i + 1;
                            result = inputStream.substring(i, j + 1);
                        }
                    }
                }
            }
        }
        return result;
    }

    // O(n^3), expand from center
    public String solution2(String inputStream) {
        String result = "";
        int maxLength = 0;
        for (int i = 0; i < inputStream.length(); i++) {
            // aba
            int j = i, k = i;
            for (; j >= 0 && k < inputStream.length(); j--, k++) {
                if (!isPalindromicString(inputStream.substring(j, k + 1))) {
                    break;
                }
            }
            if (maxLength < (k - j - 1)) {
                maxLength = k - j - 1;
                result = inputStream.substring(j + 1, k);
            }

            // abba
            j = i;
            k = i + 1;
            for (; j >= 0 && k < inputStream.length(); j--, k++) {
                if (!isPalindromicString(inputStream.substring(j, k + 1))) {
                    break;
                }
            }
            if (maxLength < (k - j - 1)) {
                maxLength = k - j - 1;
                result = inputStream.substring(j + 1, k);
            }
        }

        return result;
    }

    // O(n^2), expand from center, optimized
    public String solution2_2(String inputStream) {
        String result = "";
        int maxLength = 0;
        for (int i = 0; i < inputStream.length(); i++) {
            // aba
            int j = i, k = i;
            for (; j >= 0 && k < inputStream.length(); j--, k++) {
                // if (!isPalindromicString(inputStream.substring(j, k + 1))) {
                //     break;
                // }
                if (inputStream.charAt(j) != inputStream.charAt(k)) {
                    break;
                }
            }
            if (maxLength < (k - j - 1)) {
                maxLength = k - j - 1;
                result = inputStream.substring(j + 1, k);
            }

            // abba
            j = i;
            k = i + 1;
            for (; j >= 0 && k < inputStream.length(); j--, k++) {
                // if (!isPalindromicString(inputStream.substring(j, k + 1))) {
                //     break;
                // }
                if (inputStream.charAt(j) != inputStream.charAt(k)) {
                    break;
                }
            }
            if (maxLength < (k - j - 1)) {
                maxLength = k - j - 1;
                result = inputStream.substring(j + 1, k);
            }
        }

        return result;
    }

    // O(n^2), reverse
    public String solution3(String inputStream) {
        String result = "";
        int maxLength = 0;
        String reverseInput = new StringBuilder(inputStream).reverse().toString();
        for (int i = 0; i < inputStream.length(); i++) {
            for (int j = i; j < inputStream.length(); j++) {
                if (reverseInput.contains(inputStream.substring(i, j + 1))) {
                    if (isPalindromicString(inputStream.substring(i, j + 1))) {
                        if (maxLength < j - i + 1) {
                            maxLength = j - i + 1;
                            result = inputStream.substring(i, j + 1);
                        }
                    }
                }
            }
        }

        return result;
    }

    // O(n) Manacher's algorithm

    public static void main(String[] args) {
        longestPalindromicSubstring s = new longestPalindromicSubstring();

        String input1 = "abcbx";
        String input2 = "xyzzyabc";
        String input3 = "a";
        String input4 = "baab";
        String input5 = "";
        System.out.println(s.solution1(input1));
        System.out.println(s.solution1(input2));
        System.out.println(s.solution1(input3));
        System.out.println(s.solution1(input4));
        System.out.println(s.solution1(input5));

        System.out.println(s.solution2(input1));
        System.out.println(s.solution2(input2));
        System.out.println(s.solution2(input3));
        System.out.println(s.solution2(input4));
        System.out.println(s.solution2(input5));

        System.out.println(s.solution3(input1));
        System.out.println(s.solution3(input2));
        System.out.println(s.solution3(input3));
        System.out.println(s.solution3(input4));
        System.out.println(s.solution3(input5));
    }
}