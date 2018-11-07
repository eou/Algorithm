// 364. Nested List Weight Sum II
class Solution {
    // 直观解法，先 DFS 求深度然后再 DFS 计算求和
    public int depthSumInverse(List<NestedInteger> nestedList) {
        if (nestedList == null) {
            return 0;
        }

        int maxDepth = depthHelper(nestedList);
        return helper(nestedList, maxDepth);
    }

    private int depthHelper(List<NestedInteger> nestedList) {
        int max = 1;
        for (NestedInteger n : nestedList) {
            if (!n.isInteger()) {
                // 注意尽量用 Math.max 求最大值，用三元运算符会再 DFS 重复一次导致 TLE
                // max = depthHelper(n.getList()) + 1 > max ? depthHelper(n.getList()) + 1 : max;
                max = Math.max(depthHelper(n.getList()) + 1, max);
            }
        }
        return max;
    }

    private int helper(List<NestedInteger> nestedList, int depth) {
        int sum = 0;
        for (NestedInteger n : nestedList) {
            if (n.isInteger()) {
                sum += depth * n.getInteger();
            } else {
                sum += helper(n.getList(), depth - 1);
            }
        }
        return sum;
    }
}

class Solution {
    // LeetCode上最简练的解法，第一层需要计算n次，第二层需要计算n-1次等等暗示每一层计算求和都要把之前的层的元素都再加一次
    // 1
    // 1 2
    // 1 2 3
    // 1 2 3 4
    // ...
    public int depthSumInverse(List<NestedInteger> nestedList) {
        int unweighted = 0, weighted = 0;
        while (!nestedList.isEmpty()) {
            List<NestedInteger> nextLevel = new ArrayList<>();
            for (NestedInteger ni : nestedList) {
                if (ni.isInteger()) {
                    unweighted += ni.getInteger();
                } else {
                    nextLevel.addAll(ni.getList());
                }
            }
            weighted += unweighted;
            nestedList = nextLevel;
        }
        return weighted;
    }
}

class Solution {
    // 上一个版本改成层次遍历
    public int depthSumInverse(List<NestedInteger> nestedList) {
        Deque<NestedInteger> queue = new ArrayDeque<>(nestedList);

        int sum = 0, currentSum = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                NestedInteger ni = queue.poll();
                if (ni.isInteger()) {
                    currentSum += ni.getInteger();
                } else {
                    for (NestedInteger n : ni.getList()) {
                        queue.offer(n);
                    }
                }
            }
            sum += currentSum;
        }

        return sum;
    }
}