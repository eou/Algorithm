>文章本天成，妙手偶得之。粹然无疵瑕，岂复须人力。
>君看古彝器，巧拙两无施。汉最近先秦，固已殊淳漓。
>胡部何为者，豪竹杂哀丝。后夔不复作，千载谁与期？

# [142. Linked List Cycle II](https://leetcode.com/problems/linked-list-cycle-ii/description/) 

```java
public class Solution {
    public ListNode detectCycle(ListNode head) {
        if(head == null || head.next == null) {
            return null;
        }
        ListNode slow = head;
        ListNode fast = head;
        // cycle variable only for readability
        boolean cycle = false;
        while(fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if(slow == fast) { //cycle 
                cycle = true;
                break;
            }
        }
        if (!cycle) {
            return null;
        }
        
        //fast == slow
        slow = head;
        while(fast != slow) {
            fast = fast.next;
            slow = slow.next;
        }
        return slow;
    }
}
```

此题从[141. Linked List Cycle](https://leetcode.com/problems/linked-list-cycle/description/)变化而来，多了一个要求：求交点位置。

141的HashSet方法其实可以原封不动地照搬，遍历一遍之后遇到重复的第一个点就肯定是交点，可惜空间复杂度为$O(n)$.

快慢指针方法的第一部分也跟141一样，根据速度差，如果存在圈，快指针会追上慢指针，此时从头结点出发的指针与从双指针相遇点出发的指针同速运动，会在交点相遇。

可以简单证明，设头部到交点距离为a，圈长度为b，快慢指针相遇点距离交点的长度为x. 则 `a + b + x = (a + x) * 2` 可得 `a + x = b` → `b - x = a`.

------

# [160. Intersection of Two Linked Lists](https://leetcode.com/problems/intersection-of-two-linked-lists/description/)

```java
class Solution {
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        //boundary check
        if(headA == null || headB == null) return null;

        ListNode a = headA;
        ListNode b = headB;

        //if a & b have different len, then we will stop the loop after second iteration
        while(a != b) {
            //for the end of first iteration, we just reset the pointer to the head of another linkedlist
            a = a == null ? headB : a.next;
            b = b == null ? headA : b.next;    
        }

        return a;
    }
}
```

类似的链表相交问题不少。暴力解法就是遍历。

一般都是根据双指针前进长度的规律算出交点位置。

可以证明`A.length = a + c`, `B.length = b + c`. 如果跑完一遍然后互换则总距离为`a + c + b = b + c + a`，可以看出一定相遇，与c，也就是链表相交后的长度无关。

---

# [300. Longest Increasing Subsequence](https://leetcode.com/problems/longest-increasing-subsequence/description/)

```java
class Solution {
    public int lengthOfLIS(int[] nums) {
        if(nums.length == 0) {
            return 0;
        }
        
        List<Integer> list = new ArrayList<>();
        for(int n : nums) {
            int left = 0, right = list.size();
            while(left < right) {
                int mid = left + (right - left) / 2;
                if(list.get(mid) < n) {
                    left = mid + 1;
                } else {
                    right = mid;
                }
            }
            if(left >= list.size()) {
                list.add(n);
            } else {
                list.set(left, n);
            }
        }
        
        return list.size();
    }
}
```

此题为典型动态规划问题，DP解法复杂度为$O(n^2)$.

然而可以遍历一次原数组，直接找出LIS：维护一个新的递增数组保存LIS，确定原数组中每个数字在新数组中的位置，此处查找位置就可以用二分查找，时间复杂度降为$O(nlogn)$.

---

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

从[339. Nested List Weight Sum](https://leetcode.com/problems/nested-list-weight-sum/description/)变形而来。仅仅改变了求和过程中深度的变化条件，本为第1层到第n层，现在为第n层到第1层。如想从339代码直接修改，便是先DFS出深度，然后DFS或者BFS求和即可。

不过无需提前求出深度。

第1层元素×n，第2层元素×(n-1)……这样的规律暗示每当到第k层，第i层（i<k）就应该可以重复计算k - i + 1次，如此到第n层，第i层就总共可以计算n - i + 1次：即（第1层）+（第1层 + 第2层）+（第1层 + 第2层 + 第3层）+ …… +（第1层 + 第2层 + …… + 第n层）. 

---

