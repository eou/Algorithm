// 354. Russian Doll Envelopes
// 此题其实是 300. Longest Increasing Subsequence 的二维版本，同理最终可以拓展到三维盒子堆叠问题
class Solution {
    // DP 版本，时间复杂度为 O(n^2)
    public int maxEnvelopes(int[][] envelopes) {
        if(envelopes.length == 0 || envelopes[0].length == 0) {
            return 0;
        }
        
        Arrays.sort(envelopes, new Comparator<int[]>(){
            public int compare(int[] a, int[] b) {
                if(a[0] == b[0]) {
                    return a[1] - b[1];
                } else {
                    return a[0] - b[0];
                }
            }
        });
        
        int max = 0;
        int[] dp = new int[envelopes.length];
        for(int i = 0; i < envelopes.length; i++) {
            dp[i] = 1;
            for(int j = i - 1; j >= 0; j--) {
                if(envelopes[j][0] < envelopes[i][0] && envelopes[j][1] < envelopes[i][1]) {
                    dp[i] = Math.max(dp[j] + 1, dp[i]);
                }
            }
            max = Math.max(dp[i], max);
        }
        
        return max;
    }
}

class Solution {
    // binary search 版本，时间复杂度为 O(n^2)
    // 虽然答案正确，但是算出的最终序列不一定是正确的，正常做法还是 DP 比较好
    // 如 [[1,2],[2,4],[5,3],[6,1]]，按照这个算法，[1,2],[2,4] => [1,2],[5,3] => [6,1],[5,3]，因为我们只关注序列长度，所以只要保证最后最大尺寸的更新正确即可
    public int maxEnvelopes(int[][] envelopes) {
        if(envelopes == null || envelopes.length == 0 || envelopes[0] == null || envelopes[0].length != 2) {
            return 0;
        }
            
        Arrays.sort(envelopes, new Comparator<int[]>() {
            public int compare(int[] arr1, int[] arr2) {
                if(arr1[0] == arr2[0]) {
                    // 注意第二个参数降序排列，保证同一个宽度下，高度是逐渐减小的
                    return arr2[1] - arr1[1];
                } else {
                    return arr1[0] - arr2[0];
                }
           }
        });
        
        int dp[] = new int[envelopes.length];
        int len = 0;
        for(int[] envelope : envelopes) {
            int index = Arrays.binarySearch(dp, 0, len, envelope[1]);
            if(index < 0) {
                // 负值是第一个比关键字大的元素在数组中的位置索引，而且这个位置索引从1开始！
                index = -(index + 1);
            }
            dp[index] = envelope[1];
            if(index == len) {
                len++;
            } 
        }
        
        return len;
    }
}