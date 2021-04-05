// 1340. Jump Game V
// DFS with memoization, O(ND)
class Solution {
    private int[] memo;
    public int maxJumps(int[] arr, int d) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        
        memo = new int[arr.length];
        int res = 0;
        for (int i = 0; i < arr.length; i++) {
            res = Math.max(res, dfs(arr, d, i));
        }

        return res;
    }
    
    private int dfs(int[] arr, int d, int start) {
        if (memo[start] != 0) {
            return memo[start];
        }
        
        int res = 1;
        // jump to left, i - d
        int i = start - 1, curMax = arr[start];
        while (i >= 0 && i >= start - d) {
            curMax = Math.max(curMax, arr[i]);
            if (arr[i] < arr[start] && curMax == arr[start]) {
                res = Math.max(res, 1 + dfs(arr, d, i));
            } else {
                break;
            }
            i--;
        }
        
        i = start + 1;
        curMax = arr[start];
        while (i < arr.length && i <= start + d) {
            curMax = Math.max(curMax, arr[i]);
            if (arr[i] < arr[start] && curMax == arr[start]) {
                res = Math.max(res, 1 + dfs(arr, d, i));
            } else {
                break;
            }
            i++;
        }
        
        memo[start] = res;
        return res;
    }
}

// DP, O(NlogN + ND)
// We need to sort the input first to make sure that dp[0..i-1] has been calculated correctly before dp[i]
// If we use the segment tree, we can query the range maximum with O(logN), instead of O(D)
class Solution {
    public int maxJumps(int[] arr, int d) {
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> (a[0] - b[0]));
        for (int i = 0; i < arr.length; i++) {
            pq.offer(new int[]{arr[i], i});
        }
        
        int[] dp = new int[arr.length];
        Arrays.fill(dp, 1);
        
        int res = 0;
        while (!pq.isEmpty()) {
            int[] start = pq.poll();
            int i = start[1];
            // left
            for (int j = i - 1; j >= i - d; j--) {
                if (j >= 0 && arr[j] < arr[i]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                } else {
                    break;
                }
            }
            // right
            for (int j = i + 1; j <= i + d; j++) {
                if (j < arr.length && arr[j] < arr[i]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                } else {
                    break;
                }
            }
            
            res = Math.max(res, dp[i]);
        }

        return res;
    }
}

// Decreasing Stack + DP, O(N)
// Use a stack to keep the index of decreasing elements sequence
class Solution {
    public int maxJumps(int[] arr, int d) {
        int[] dp = new int[arr.length + 1];
        Arrays.fill(dp, 1);

        Deque<Integer> stack1 = new ArrayDeque<>();
        Deque<Integer> stack2 = new ArrayDeque<>();

        List<Integer> list = new ArrayList<>();
        for (int i : arr) {
            list.add(i);
        }
        // Sentinel. If the elements are always decreasing, we will deal with them at the end of array
        list.add(Integer.MAX_VALUE);

        for (int i = 0; i <= arr.length; i++) {
            // If list.get(stack1.peek()) < list.get(i), we can possibly jump from i to stack1.peek()
            // then we check the distance: i - j <= d
            while (!stack1.isEmpty() && list.get(stack1.peek()) < list.get(i)) {
                // update dp[i] which is after dp[j]
                int pre = list.get(stack1.peek());  // handle duplicate numbers
                while (!stack1.isEmpty() && pre == list.get(stack1.peek())) {
                    int j = stack1.pop();
                    if (i - j <= d) {
                        dp[i] = Math.max(dp[i], dp[j] + 1);
                    }
                    stack2.push(j);
                }

                // update dp[stack1.peek()] which is before dp[j]
                // normally stack2 only contains 1 element, unless duplicate numbers
                while (!stack2.isEmpty()) {
                    int j = stack2.pop();
                    // Since stack1 is decreasing, we can possibly jump from stack1.peek() to j
                    if (!stack1.isEmpty() && j - stack1.peek() <= d) {
                        dp[stack1.peek()] = Math.max(dp[stack1.peek()], dp[j] + 1);
                    }
                }
            }

            // Maintain decreasing stack
            stack1.push(i);
        }

        int res = 0;
        for (int i = 0; i < arr.length; i++) {
            res = Math.max(res, dp[i]);
        }
        return res;
    }
}

// Monotonic stack + DP
// https://leetcode.com/problems/jump-game-v/discuss/933268/C%2B%2B-O(N)-Easy-to-understand
class Solution {
    public int maxJumps(int[] arr, int d) {
        List<List<Integer>> graph = new ArrayList<>();
        for (int i : arr) {
            graph.add(new ArrayList<>());
        }

        Deque<Integer> stack = new ArrayDeque<>();

        // Add edges from i going backwards
        for (int i = 0; i < arr.length; i++) {
            while (!stack.isEmpty() && arr[stack.peek()] < arr[i]) {
                if (i - stack.peek() <= d) {
                    List<Integer> list = graph.get(i);
                    list.add(stack.peek());
                    graph.set(i, list);
                }
                stack.pop();
            }

            stack.push(i);
        }

        stack = new ArrayDeque<>();

        // Add edges from i going forward
        for (int i = arr.length - 1; i >= 0; i--) {
            while (!stack.isEmpty() && arr[stack.peek()] < arr[i]) {
                if (Math.abs(i - stack.peek()) <= d) {
                    List<Integer> list = graph.get(i);
                    list.add(stack.peek());
                    graph.set(i, list);
                }
                stack.pop();
            }

            stack.push(i);
        }

        int res = 1;
        int[] dp = new int[arr.length];
        Arrays.fill(dp, 1);

        // Simple dfs to find maximum depth
        for (int i = 0; i < arr.length; i++) {
            res = Math.max(res, dfs(graph, dp, i));
        }

        return res;
    }

    private int dfs(List<List<Integer>> graph, int[] dp, int start) {
        for (int neighbor : graph.get(start)) {
            if (dp[neighbor] == 0) {
                dp[start] = Math.max(dp[start], 1 + dp[neighbor]);
            } else {
                dp[start] = Math.max(dp[start], 1 + dfs(graph, dp, neighbor));
            }
        }
        return dp[start];
    }
}