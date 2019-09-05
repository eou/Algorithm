// 315. Count of Smaller Numbers After Self
// The nature of this problem is to sort nums[i...nums.length - 1] to get the position of nums[i]
class Solution {
    // for nums[i], if we sort nums[i..nums.length - 1], the position of nums[i] will show the number of smaller numbers
    public List<Integer> countSmaller(int[] nums) {
        List<Integer> count = new ArrayList<>();
        List<Integer> list = new ArrayList<>();
        for (int i = nums.length - 1; i >= 0; i--) {
            count.add(0, findPos(nums[i], list));
        }
        return count;
    }

    private int findPos(int num, List<Integer> list) {
        if (list.size() == 0) {
            list.add(num);
            return 0;
        }

        int left = 0, right = list.size();
        // after loop, left == right
        // we need final pointer points to the element
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (list.get(mid) < num) {
                left = mid + 1;
            } else {
                // if num == list.get(num), find leftmost element
                right = mid;
            }
        }

        // 0, [1, 2, 3] => left = 0
        // 5, [1, 2, 3] => left = 3
        // 3, [3, 3, 3] => left = 0
        list.add(left, num);
        return left;
    }
}


// Fenwick Tree / Binary Indexed Tree
class Solution {
    class FenwickTree {
        public int[] sums;

        public FenwickTree(int n) {
            sums = new int[n];
        }

        public int query(int i) {
            int sum = 0;
            while (i > 0) {
                sum += sums[i];
                i -= lowbit(i);
            }
            return sum;
        }

        public void update(int i, int delta) {
            while (i < sums.length) {
                sums[i] += delta;
                i += lowbit(i);
            }
        }

        private int lowbit(int i) {
            return i & (-i);
        }
    }

    public List<Integer> countSmaller(int[] nums) {
        List<Integer> result = new ArrayList<>();

        int[] nums2 = new int[nums.length];
        System.arraycopy(nums, 0, nums2, 0, nums.length);
        Arrays.sort(nums2);
        Map<Integer, Integer> map = new HashMap<>(); // number => rank
        for (int i = 0, rank = 1; i < nums2.length; i++) {
            if (!(i == 0 || nums2[i] == nums2[i - 1])) {
                rank++;
            }
            map.put(nums2[i], rank);
        }

        FenwickTree tree = new FenwickTree(nums.length);
        for (int i = nums.length - 1; i >= 0; i--) {
            result.add(0, tree.query(map.get(nums[i]) - 1));
            tree.update(map.get(nums[i]), 1);       // rank start from 1, otherwise infinite loop
        }

        return result;
    }
}

// BST
class Solution {
    class Node {
        int val;
        int count;
        int left_count;     // not smaller counts
        Node left;
        Node right;

        public Node(int val) {
            this.val = val;
            this.count = 1;
        }
    }

    public List<Integer> countSmaller(int[] nums) {
        List<Integer> ans = new ArrayList<>();
        if (nums.length == 0) {
            return ans;
        }

        // use fake root
        // Node root = new Node(Integer.MAX_VALUE);
        // for (int i = nums.length - 1; i >= 0; --i) {
        //     ans.add(0, insert(root, nums[i]));
        // }
        Node root = new Node(nums[nums.length - 1]);
        ans.add(0);
        for (int i = nums.length - 2; i >= 0; --i) {
            ans.add(0, insert(root, nums[i]));
        }
        return ans;
    }

    private int insert(Node root, int val) {
        if (root.val == val) {
            root.count++;
            return root.left_count;
        } else if (val < root.val) {
            root.left_count++;
            if (root.left == null) {
                root.left = new Node(val);
                return 0;
            }
            return insert(root.left, val);
        } else {
            if (root.right == null) {
                root.right = new Node(val);
                return root.count + root.left_count;
            }
            return root.count + root.left_count + insert(root.right, val);
        }
    }
}
