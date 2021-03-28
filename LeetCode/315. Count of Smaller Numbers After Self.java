// 315. Count of Smaller Numbers After Self
// The nature of this problem is to sort nums[i...nums.length - 1] to get the position of nums[i]
// for nums[i], if we sort nums[i..nums.length - 1], the position of nums[i] will show the number of smaller numbers
class Solution {
    public List<Integer> countSmaller(int[] nums) {
        List<Integer> res = new ArrayList<>();
        if (nums == null || nums.length == 0) {
            return res;
        }

        List<Integer> cnt = new ArrayList<>();
        for (int i = nums.length - 1; i >= 0; i--) {
            res.add(0, findSmallerCount(nums[i], cnt));
        }

        return res;
    }

    private int findSmallerCount(int num, List<Integer> list) {
        if (list.size() == 0) {
            list.add(num);
            return 0;
        }

        int left = 0, right = list.size() - 1;
        while (left + 1 < right) {
            int mid = left + (right - left) / 2;
            if (list.get(mid) >= num) {
                right = mid;
            } else {
                left = mid;
            }
        }

        // ..., left, right, ...
        // we can only insert 3 postions: left, right, right + 1
        if (list.get(right) < num) {
            list.add(right + 1, num);
            return right + 1;
        }
        if (list.get(left) >= num) {
            list.add(left, num);
            return left;
        }
        list.add(right, num);
        return right;
    }
}

// Another binary search
class Solution {
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

// Fenwick Tree / Binary Indexed Tree / BIT
// O(nlogn)
class Solution {
    public List<Integer> countSmaller(int[] nums) {
        List<Integer> res = new ArrayList<>();

        Map<Integer, Integer> map = getRank(nums); // number => rank

        // [5,2,6,1] => discretization [3,2,4,1] (rank starts from 1, not 0)
        // [0,0,0,0] => 1 prefix: 0 smaller => update [1,0,0,0]
        // [1,0,0,0] => 4 prefix: 1 smaller => update [1,0,0,1]
        // [1,0,0,1] => 2 prefix: 1 smaller => update [1,1,0,1]
        // [1,1,0,1] => 3 prefix: 1 + 1 = 2 smaller, update [1,1,1,1]
        FenwickTree tree = new FenwickTree(nums.length);
        for (int i = nums.length - 1; i >= 0; i--) {
            int rank = map.get(nums[i]);
            // not query(rank), we're looking for smaller numbers
            res.add(0, tree.query(rank - 1));
            // we can only update BIT after this query. If we update all number in BIT, then the answer would be "all smaller number in the array" but not "after self"
            tree.update(rank, 1);
        }

        return res;
    }

    // Actually this is discretization since we only care about their orders.
    private Map<Integer, Integer> getRank(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            set.add(num);
        }
        List<Integer> list = new ArrayList<>(set);
        Collections.sort(list);

        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < list.size(); i++) {
            // number -> rank, rank starts from 1
            map.put(list.get(i), i + 1);
        }
        return map;
    }

    class FenwickTree {
        public int[] sums;

        // we don't need to init the tree here since the tree is built by 'update' in this problem
        public FenwickTree(int n) {
            sums = new int[n + 1];
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
}

// Merge sort, O(nlogn)
class Solution {
    class Pair {
        int idx;
        int val;
        public Pair(int idx, int val) {
            this.idx = idx;
            this.val = val;
        }
    }
    
    // The smaller numbers on the right of a number are exactly those that jump from its right to its left during a stable sort (merge sort)
    public List<Integer> countSmaller(int[] nums) {
        List<Integer> res = new ArrayList<>();
        if (nums == null || nums.length == 0) {
            return res;
        }

        Pair[] arr = new Pair[nums.length];
        Integer[] smaller = new Integer[nums.length];
        Arrays.fill(smaller, 0); // otherwise will all be null

        for (int i = 0; i < nums.length; i++) {
            arr[i] = new Pair(i, nums[i]);
        }

        mergeSort(arr, smaller);

        res.addAll(Arrays.asList(smaller));
        return res;
    }

    private Pair[] mergeSort(Pair[] arr, Integer[] smaller) {
        if (arr.length <= 1) {
            return arr;
        }

        int mid = arr.length / 2;
        Pair[] left = mergeSort(Arrays.copyOfRange(arr, 0, mid), smaller);
        Pair[] right = mergeSort(Arrays.copyOfRange(arr, mid, arr.length), smaller);
        
        // merge
        int i = 0, l = 0, r = 0;
        while (l < left.length && r < right.length) {
            // !!! <= not <, if left[l].val == right[r].val, we don't move right pointer since the number of smaller elements in right side didn't change
            if(left[l].val <= right[r].val) {
                arr[i] = left[l];
                smaller[left[l].idx] += r;
                l++;
            } else {
                arr[i] = right[r];
                r++;
            }
            i++;
        }

        while (l < left.length) {
            arr[i] = left[l];
            smaller[left[l].idx] += r;
            l++;
            i++;
        }

        while (r < right.length) {
            arr[i] = right[r];
            r++;
            i++;
        }

        return arr;
    }
}

// BST, TLE
class Solution {
    class Node {
        int val;
        // duplicate
        int count;
        // smaller elements
        int smaller;
        Node left;
        Node right;

        public Node(int val) {
            this.val = val;
            this.count = 1;
        }
    }

    public List<Integer> countSmaller(int[] nums) {
        List<Integer> res = new ArrayList<>();
        if (nums.length == 0) {
            return res;
        }

        Node root = new Node(nums[nums.length - 1]);
        res.add(0);
        for (int i = nums.length - 2; i >= 0; --i) {
            res.add(0, insert(root, nums[i]));
        }
        return res;
    }

    private int insert(Node root, int val) {
        if (root.val == val) {
            root.count++;
            return root.smaller;
        } else if (val < root.val) {
            root.smaller++;
            if (root.left == null) {
                root.left = new Node(val);
                return 0;
            }
            return insert(root.left, val);
        } else {
            if (root.right == null) {
                root.right = new Node(val);
                return root.smaller + root.count;  // root is also the smaller one currently
            }
            return root.count + root.smaller + insert(root.right, val);
        }
    }
}
