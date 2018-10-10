>文章本天成，妙手偶得之。粹然无疵瑕，岂复须人力。
>君看古彝器，巧拙两无施。汉最近先秦，固已殊淳漓。
>胡部何为者，豪竹杂哀丝。后夔不复作，千载谁与期？

# [364. Nested List Weight Sum II](https://leetcode.com/problems/nested-list-weight-sum-ii/description/)

```java
class Solution {
    public int depthSumInverse(List<NestedInteger> nestedList) {
        int unweighted = 0, sum = 0;
        while (!nestedList.isEmpty()) {
            List<NestedInteger> nextLevel = new ArrayList<>();
            for (NestedInteger ni : nestedList) {
                if (ni.isInteger()) {
                    unweighted += ni.getInteger();
                } else {
                    nextLevel.addAll(ni.getList());
                }  
            }
            sum += unweighted;
            nestedList = nextLevel;
        }
        return sum;
    }
}
```

从[339. Nested List Weight Sum](https://leetcode.com/problems/nested-list-weight-sum/description/)而来。仅仅改变了求和过程中深度的变化条件，本为第1层到第n层，现在为第n层到第1层。如想从339代码直接修改，便是先DFS出深度，然后DFS求和即可。

但是第1层元素×n，第2层元素×(n-1)……这样的规律暗示每当到第k层，第i层（i<k）就应该计算了k - i + 1次，如此到第n层，第i层就总共可以计算n - i + 1次。即（第1层）+（第1层 + 第2层）+（第1层 + 第2层 + 第3层）+ …… +（第1层 + 第2层 + …… + 第n层）.

