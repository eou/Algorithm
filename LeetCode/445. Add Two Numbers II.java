// 445. Add Two Numbers II
// Directly add, WA, overflow
class Solution {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        long num1 = transferToNumber(l1);
        long num2 = transferToNumber(l2);
        long num3 = num1 + num2;
        return transferToList(num3);
    }
    
    public long transferToNumber(ListNode list) {
        long res = 0;
        ListNode cur = list;
        while (cur != null) {
            res = (res * 10) + cur.val;
            cur = cur.next;
        }
        return res;
    }
    
    public ListNode transferToList(long num) {
        ListNode cur = new ListNode(-1);    // dummy head
        ListNode head = cur, pre = cur;
        List<Integer> list = new ArrayList<>();
        if (num == 0) {
            list.add(0);
        } else {
            while (num > 0) {
                list.add((int)(num % 10));
                num /= 10;
            }
        }
        
        for (int i = list.size() - 1; i >= 0; i--) {
            cur.next = new ListNode(list.get(i));
            pre = cur;
            cur = cur.next;
        }
        return head.next;
    }
}

// Stack
class Solution {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        Deque<Integer> stack1 = new ArrayDeque<>();
        Deque<Integer> stack2 = new ArrayDeque<>();
        ListNode cur = l1;
        while (cur != null) {
            stack1.push(cur.val);
            cur = cur.next;
        }
        cur = l2;
        while (cur != null) {
            stack2.push(cur.val);
            cur = cur.next;
        }
        
        int sum = 0;
        ListNode list = new ListNode(0);
        while (!stack1.isEmpty() || !stack2.isEmpty()) {
            if (!stack1.isEmpty()) sum += stack1.pop();
            if (!stack2.isEmpty()) sum += stack2.pop();
            list.val = sum % 10;
            ListNode head = new ListNode(sum / 10);
            head.next = list;
            list = head;
            sum /= 10;
        }

        return list.val == 0 ? list.next : list;    // first carry
    }
}
