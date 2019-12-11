// 13. Roman to Integer
// 重点在于找到 IV, XL, CM 等的计算规律，否则纯判断语句比较繁琐
// M CM XC IV
class Solution {
    public int romanToInt(String s) {
        int res = 0;
        // backward, last digit is first position
        for (int i = s.length() - 1; i >= 0; i--) {
            char c = s.charAt(i);
            switch (c) {
            case 'I':
                res += (res >= 5 ? -1 : 1);
                break;
            case 'V':
                res += 5;
                break;
            case 'X':
                res += 10 * (res >= 50 ? -1 : 1);
                break;
            case 'L':
                res += 50;
                break;
            case 'C':
                res += 100 * (res >= 500 ? -1 : 1);
                break;
            case 'D':
                res += 500;
                break;
            case 'M':
                res += 1000;
                break;
            }
        }
        return res;
    }
}

class Solution {
    public int romanToInt(String s) {
        // java map 初始化是个大问题
        Map<Character, Integer> map = new HashMap<>();
        map.put('I', 1);
        map.put('V', 5);
        map.put('X', 10);
        map.put('L', 50);
        map.put('C', 100);
        map.put('D', 500);
        map.put('M', 1000);
        
        int result = 0;
        for(int i = s.length() - 1; i >= 0; i--) {
            if(i == s.length() - 1 || map.get(s.charAt(i)) >= map.get(s.charAt(i + 1))) {
                result += map.get(s.charAt(i));
            } else {
                result -= map.get(s.charAt(i));
            }
        }
        return result;
    }
}