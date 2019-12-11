// 38. Count and Say
// 时间复杂度为 O(n^2)，空间复杂度为 O(n)，最长字符串是 2N，最短字符串是 logN + 2，N为输入字符串长度
class Solution {
    public String countAndSay(int n) {
        StringBuilder pre = new StringBuilder("1");
        for(int i = 1; i < n; i++) {
            StringBuilder cur = new StringBuilder();
            int cnt = 1;
            int j = 0;
            while(j < pre.length()) {
                if(j < pre.length() - 1 && pre.charAt(j) == pre.charAt(j + 1)) {
                    cnt++;
                    j++;
                } else {
                    cur.append(cnt);
                    cur.append(pre.charAt(j));
                    j++;
                    cnt = 1;
                }
            }
            pre = cur;
        }

        return pre.toString();
    }
}