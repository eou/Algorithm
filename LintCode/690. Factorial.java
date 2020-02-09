// 690. Factorial
class Solution {
    public String factorial(int n) {
        // write your code here
        List<Integer> ans = new ArrayList<Integer>();
        ans.add(1);
        for (int i = 2; i <= n; i++) {
            for (int j = 0; j < ans.size(); j++) {
                ans.set(j, ans.get(j) * i);
            }
            // carry
            for (int j = 0; j < ans.size() - 1; j++) {
                ans.set(j + 1, ans.get(j + 1) + ans.get(j) / 10);
                ans.set(j,  ans.get(j) % 10);
            }
            while (ans.get(ans.size() - 1) > 9) {
                // extend one more position
                ans.add(ans.get(ans.size() - 1) / 10);  // add last is faster than add first
                ans.set(ans.size() - 2, ans.get(ans.size() - 2) % 10);
            }
        }
        String s = new String();
        for (int i = ans.size() - 1; i >= 0; i--) {
            s += (char)(ans.get(i) + '0');
        }
        return s;
    }
}