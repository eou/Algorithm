// 13. Roman to Integer
// 重点在于找到 IV, XL, CM 等的计算规律，否则纯判断语句比较繁琐
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