// 276. Paint Fence
// DFS, TLE
class Solution {
    public int numWays(int n, int k) {
        if (n == 0 || k == 0) {
            return 0;
        }
        int[] paint = new int[n];
        return dfs(n, k, 0, paint);
    }
    
    public int dfs(int n, int k, int post, int[] paint) {
        if (post == n) {
            return 1;
        }
        
        int ways = 0;
        // use 1..k to represent k colors
        for (int i = 1; i <= k; i++) {
            // short-circuit evaluation
            if (post == 0 || post == 1 
                || paint[post - 1] != i 
                || (paint[post - 1] == i && paint[post - 2] != i)
               ) {
                paint[post] = i;
                ways += dfs(n, k, post + 1, paint);
            }
        }
        
        return ways;
    }
}

// O(n) space, dp
class Solution {
    public int numWays(int n, int k) {
        if (n == 0 || k == 0) {
            return 0;
        }

        if (n == 1) {
            return k;
        }

        int[] same = new int[n];
        int[] diff = new int[n];
        same[0] = same[1] = k;
        diff[0] = k;
        diff[1] = k * (k - 1);
        for (int i = 2; i < n; ++i) {
            same[i] = diff[i - 1];
            diff[i] = (k - 1) * (same[i - 1] + diff[i - 1]);
        }
        return same[n - 1] + diff[n - 1];
    }
}

// O(1) space, dp
class Solution {
    public int numWays(int n, int k) {
        if (n == 0 || k == 0) {
            return 0;
        }

        if (n == 1) {
            return k;
        }

        int diff = k * (k - 1);
        int same = k;
        for (int i = 2; i < n; i++) {
            int tmp = diff;
            diff = (diff + same) * (k - 1); // all colors except last one
            same = tmp; // must be last diff
        }

        return diff + same;
    }
}