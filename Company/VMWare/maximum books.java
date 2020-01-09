// 有n套丛书，每套书包含X本书，按套卖，每套价值Y 元。
// 有两个等长input array分别代表每套书包含几本书和每套书卖多少钱。 
// 另一个input是int，代表你的预算。求最多能买多少本书。
import java.util.*;

class Solution {
    // 0-1 knapsack problem
    public static int books(int[] books, int[] values, int money) {
        if (books == null || values == null || books.length == 0 || values.length == 0 || money <= 0) {
            return 0;
        }

        int[][] dp = new int[books.length + 1][money + 1];
        for (int i = 1; i <= books.length; i++) {
            for (int j = 1; j <= money; j++) {
                if (j >= values[i - 1]) {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - values[i - 1]] + books[i - 1]);
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }

        return dp[books.length][money];
    }

    public static void main(String[] args) {

    }
}