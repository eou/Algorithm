// 354. Russian Doll Envelopes
// 此题其实是 300. Longest Increasing Subsequence 的二维版本，同理最终可以拓展到三维盒子堆叠问题
class Solution {
    // DP 版本，时间复杂度为 O(n^2)
    public int maxEnvelopes(int[][] envelopes) {
        if(envelopes.length == 0 || envelopes[0].length == 0) {
            return 0;
        }
        
        Arrays.sort(envelopes, (a, b) -> a[0] == b[0] ? a[1] - b[1] : a[0] - b[0]);

        int res = 0;
        int[] dp = new int[envelopes.length];
        for (int i = 0; i < envelopes.length; i++) {
            dp[i] = 1;
            for (int j = i - 1; j >= 0; j--) {
                if (envelopes[j][0] < envelopes[i][0] && envelopes[j][1] < envelopes[i][1]) {
                    dp[i] = Math.max(dp[j] + 1, dp[i]);
                }
            }
            res = Math.max(res, dp[i]);
        }

        return res;
    }
}

// binary search 版本，时间复杂度为 O(nlogn)
// 虽然答案正确，但是算出的最终序列不一定是正确的
// 如 [[1,2],[2,4],[5,3],[6,1]]，按照这个算法，[1,2],[2,4] => [1,2],[5,3] => [6,1],[5,3]，因为我们只关注序列长度，所以只要保证最后最大尺寸的更新正确即可
class Solution {
    public int maxEnvelopes(int[][] envelopes) {
        if(envelopes == null || envelopes.length == 0 || envelopes[0] == null || envelopes[0].length != 2) {
            return 0;
        }
        
        // 在宽度相等的时候，让高度不能出现“上升的子序列”。即首先按照宽度“升序排序”，在宽度相等的时候，按照高度“降序排序”，
        // 这样就能保证宽度相等的信封，最多只能选一个，这种策略保证了结果的正确性
        Arrays.sort(envelopes, (a, b) -> a[0] == b[0] ? b[1] - a[1] : a[0] - b[0]);
        
        // find LIS in envelopes[i][1]
        // dp[i] 表示长度为 i 的最长上升子序列的末尾元素的最小值
        int[] dp = new int[envelopes.length];
        dp[0] = envelopes[0][1];

        // res 表示有序数组 dp 的最后一个已经赋值元素的索引
        int res = 0;
        for (int i = 0; i < envelopes.length; i++) {
            int cur = envelopes[i][1];
            if (cur > dp[res]) {
                // find a new tail element for LIS with length res
                res++;
                dp[res] = cur;
            } else {
                // update existence LIS, find matched LIS, we want to find first element no smaller than current number
                int left = 0, right = res;
                while (left < right) {
                    int mid = left + (right - left) / 2;
                    if (dp[mid] < cur) {
                        left = mid + 1;
                    } else {
                        right = mid;
                    }
                }
                dp[left] = cur;
            }
        }

        return res + 1;
    }
}