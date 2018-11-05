// 339. Nested List Weight Sum
// 就是层次遍历 或者 DFS
// [[1,1], 2, [1,1]]
//     *, 2, *
//    /       \
//   1,1      1,1
// [1, [4, [6]]]
//    1, *
//        \
//        4,  *
//             \
//              6
class Solution {
    public int depthSum(List<NestedInteger> nestedList) {
        if (nestedList == null) {
            return 0;
        }

        Deque<NestedInteger> queue = new ArrayDeque<>(nestedList);
        int level = 1, sum = 0;
        while (queue.size() > 0) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                NestedInteger tmp = queue.poll();
                if (tmp.isInteger()) {
                    sum += tmp.getInteger() * level;
                } else {
                    for (NestedInteger n : tmp.getList()) {
                        queue.offer(n);
                    }
                    // queue.addAll(tmp.getList());
                }
            }
            level++;
        }

        return sum;
    }
}

class Solution {
    public int depthSum(List<NestedInteger> nestedList) {
        return helper(nestedList, 1);
    }

    private int helper(List<NestedInteger> nestedList, int depth) {
        int sum = 0;
        for(NestedInteger n : nestedList) {
            if(n.isInteger()) {
                sum += n.getInteger() * depth;
            } else {
                sum += helper(n.getList(), depth + 1);
            }
        }
        return sum;
    }
}