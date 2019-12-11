// 771. Jewels and Stones
class Solution {
    public int numJewelsInStones(String J, String S) {
        int cnt = 0;
        for(char c : S.toCharArray()) {
            if(J.indexOf(c) != -1) {
                cnt++;
            }
        }
        return cnt;
    }
}