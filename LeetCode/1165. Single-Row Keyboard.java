// 1165. Single-Row Keyboard
class Solution {
    public int calculateTime(String keyboard, String word) {
        int[] position = new int[26];
        int idx = 0;
        for (Character c : keyboard.toCharArray()) {
            position[(int)(c - 'a')] = (idx++);
        }
        
        int time = 0;
        int pre = 0;
        for (Character c : word.toCharArray()) {
            time += Math.abs(position[(int)(c - 'a')] - pre);
            pre = position[(int)(c - 'a')];
        }
        
        return time;
    }
}