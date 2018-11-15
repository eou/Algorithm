// 23. Merge k Sorted Lists
// 此题解法颇多，但无甚特别技巧：
// 1. 将链表值保存在数组中，排序，然后转换为链表。时间复杂度 O(nlogn)
// 2. 比较每个链表的头结点，找出当前最小的结点，添加在最终链表中。时间复杂度 O(nk)，空间复杂度 O(n) + O(1)
// 3. 用优先队列优化方法2，把所有结点保存在优先队列中，然后一个个弹出。时间复杂度 O(nlogk)，空间复杂度 O(n) + O(min(k, n))，建堆时间是O(k)
// 4. 两两合并链表，合并次数为 k - 1。时间复杂度 O(nk)，空间复杂度 O(1)
// 5. 分治法合并，一次合并 k / 2 个链表，一共合并 logk 次。时间复杂度 O(nlogk)，空间复杂度 O(1)
class Solution {
    // 优先队列版本
    public ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) {
            return null;
        }

        PriorityQueue<ListNode> pq = new PriorityQueue<>(new Comparator<ListNode>() {
            public int compare(ListNode node1, ListNode node2) {
                return node1.val - node2.val;
            }
        });

        ListNode dummy = new ListNode(-1);
        ListNode curr = dummy;

        // 把 k 个链表头结点放入队列中
        for (ListNode list : lists) {
            if (list != null) {
                pq.add(list);
            }
        }

        while (!pq.isEmpty()) {
            curr.next = pq.poll();
            curr = curr.next;

            if (curr.next != null) {
                pq.add(curr.next);
            }
        }

        return dummy.next;
    }
}

class Solution {
    // 分治合并版本
    public ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) {
            return null;
        }
        return partitionSort(lists, 0, lists.length - 1);
    }

    private ListNode partitionSort(ListNode[] lists, int start, int end) {
        if (start >= end) {
            return lists[start];
        }

        int mid = start + (end - start) / 2;
        ListNode l1 = partitionSort(lists, start, mid);
        ListNode l2 = partitionSort(lists, mid + 1, end);
        return merge(l1, l2);
    }

    // 21. Merge Two Sorted Lists，递归版本简洁，时间复杂度一样 O(n + m)，但是空间复杂度是 O(m + n)，迭代是 O(1)
    // The first call to mergeTwoLists does not return until the ends of both l1 and l2 have been reached, so n + mn+m stack frames consume O(n + m)O(n+m) space
    private ListNode merge(ListNode l1, ListNode l2) {
        if (l1 == null) {
            return l2;
        }
        if (l2 == null) {
            return l1;
        }

        if (l1.val < l2.val) {
            l1.next = merge(l1.next, l2);
            return l1;
        } else {
            l2.next = merge(l2.next, l1);
            return l2;
        }
    }
}

class Solution {
    // compare one by one
    public ListNode mergeKLists(ListNode[] lists) {
        int minList = 0;
        ListNode dummy = new ListNode(-1);
        ListNode cur = dummy;

        while (true) {
            boolean isEnd = true;
            int min = Integer.MAX_VALUE;
            for (int i = 0; i < lists.length; i++) {
                if (lists[i] != null && lists[i].val < min) {
                    minList = i;
                    min = lists[i].val;
                    isEnd = false;
                }
            }
            if (isEnd) {
                break;
            }
            cur.next = lists[minList];
            cur = cur.next;
            lists[minList] = lists[minList].next;
        }

        return dummy.next;
    }
}