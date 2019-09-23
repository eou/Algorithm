// 312. Burst Balloons
// DFS, TLE
class Solution {
    public int maxCoins(int[] nums) {
        List<Integer> list = Arrays.stream(nums)		// IntStream
									.boxed()  		    // Stream<Integer>
									.collect(Collectors.toList());
        return dfs(list);
    }
    
    public int dfs(List<Integer> list) {
        if (list.isEmpty()) {
            return 0;
        }
        if (list.size() == 1) {
            return list.get(0);
        }
        
        int result = 0;
        for (int i = 0; i < list.size(); i++) {
            int current = 0;
            if (i == 0) {
                current = list.get(i) * list.get(i + 1); 
            } else if (i == list.size() - 1) {
                current = list.get(i) * list.get(i - 1);
            } else {
                current = list.get(i - 1) * list.get(i) * list.get(i + 1);
            }
            List<Integer> list2 = new ArrayList<>(list);
            list2.remove(i);
            result = Math.max(result, current + dfs(list2)); 
        }
        
        return result;
    }
}

// DFS with memo, still TLE
class Solution {
    public Map<List<Integer>, Integer> memo;

    public int maxCoins(int[] nums) {
        memo = new HashMap<>();
        List<Integer> list = Arrays.stream(nums) // IntStream
                .boxed() // Stream<Integer>
                .collect(Collectors.toList());
        return dfs(list);
    }

    public int dfs(List<Integer> list) {
        if (list.isEmpty()) {
            return 0;
        }
        if (list.size() == 1) {
            return list.get(0);
        }
        if (memo.containsKey(list)) {
            return memo.get(list);
        }

        int result = 0;
        for (int i = 0; i < list.size(); i++) {
            int current = 0;
            if (i == 0) {
                current = list.get(i) * list.get(i + 1);
            } else if (i == list.size() - 1) {
                current = list.get(i) * list.get(i - 1);
            } else {
                current = list.get(i - 1) * list.get(i) * list.get(i + 1);
            }
            List<Integer> list2 = new ArrayList<>(list);
            list2.remove(i);
            result = Math.max(result, current + dfs(list2));
        }

        memo.put(list, result);
        return result;
    }
}

// 区间 DP
class Solution {
    public int maxCoins(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        // dp[i][j] means maxCoin we can get from nums[i..j]
        // in nums[i..j], if burst nums[i..k - 1], nums[k + 1..j], then we get nums[i - 1] * nums[k] * nums[j + 1]
        int[][] dp = new int[nums.length][nums.length];
        // length is 1
        for (int i = 0; i < nums.length; i++) {
            int leftNum = i == 0 ? 1 : nums[i - 1];
            int rightNum = i == nums.length - 1 ? 1 : nums[i + 1];
            dp[i][i] = leftNum * nums[i] * rightNum;
        }

        // length start from 2
        for (int i = 2; i <= nums.length; i++) {    // [2, nums.length], <= not <
            // start from ballon j
            for (int j = 0; j < nums.length - i + 1; j++) {
                int left = j, right = j + i - 1;
                // keep balloon k not burst
                for (int k = left; k <= right; k++) {
                    int leftPart = k - 1 < left ? 0 : dp[left][k - 1];
                    int rightPart = k + 1 > right ? 0 : dp[k + 1][right];
                    int leftNum = left - 1 < 0 ? 1 : nums[left - 1];
                    int rightNum = right + 1 > nums.length - 1 ? 1 : nums[right + 1];
                    dp[left][right] = Math.max(dp[left][right], leftPart + rightPart + nums[k] * leftNum * rightNum);
                }
            }
        }

        return dp[0][nums.length - 1];
    }
}