// 295. Find Median from Data Stream
// two heap
class MedianFinder {
    public PriorityQueue<Integer> lower;
    public PriorityQueue<Integer> higher;
    
    public MedianFinder() {
        // max-heap
        lower = new PriorityQueue<>((a, b) -> {return b - a;});
        // min-heap
        higher = new PriorityQueue<>();
    }
    
    public void addNum(int num) {
        if (lower.size() == 0) {
            lower.add(num);
            return;
        }
        
        if (higher.size() == 0) {
            higher.add(num);
            return;
        }
        
        if (num > higher.peek()) {
            higher.add(num);
        } else {
            lower.add(num);
        }
        
        // balance, make sure the size of lower part is no larger than the size of higher part
        while (higher.size() > lower.size()) {
            lower.offer(higher.poll());
        }
        
        while (lower.size() > higher.size()) {
            higher.offer(lower.poll());
        }
    }
    
    public double findMedian() {
        if (lower.size() == 0 && higher.size() == 0) {
            return 0;
        }
        
        if (lower.size() == 0) {
            return higher.peek();
        }
        
        if (higher.size() == 0) {
            return lower.peek();
        }
        
        int lowerSize = lower.size();
        int higherSize = higher.size();
        if ((lowerSize + higherSize) % 2 == 0) {
            return (double)(lower.peek() + higher.peek()) / 2;
        } else {
            return higher.peek();
        }
    }
}

// simplify
class MedianFinder {
    /** initialize your data structure here. */
    public PriorityQueue<Integer> lower;
    public PriorityQueue<Integer> higher;
    
    public MedianFinder() {
        // max-heap
        lower = new PriorityQueue<>((a, b) -> {return b - a;});
        // min-heap
        higher = new PriorityQueue<>();
    }
    
    public void addNum(int num) {
        lower.offer(num);
        
        // balance
        higher.offer(lower.poll());
        if (lower.size() < higher.size()) {
            lower.offer(higher.poll());
        }
    }
    
    public double findMedian() {
        return lower.size() > higher.size() ? lower.peek() : (double)(lower.peek() + higher.peek()) / 2;
    }
}

// buckets
// https://leetcode.com/problems/find-median-from-data-stream/discuss/74057/Tired-of-TWO-HEAPSET-solutions-See-this-segment-dividing-solution-(c%2B%2B)
public class MedianFinder {
    private LinkedList<LinkedList<Integer>> buckets; // store all ranges
    private int total_size;

    public MedianFinder() {
        total_size = 0;
        buckets = new LinkedList<>();
        buckets.add(new LinkedList<>());
    }

    public void addNum(int num) {
        LinkedList<Integer> correctRange = new LinkedList<>();
        int targetIndex = 0;

        // find the correct range to insert given num
        for (int i = 0; i < buckets.size(); i++) {
            if (buckets.size() == 1 ||
                (i == 0 && num <= buckets.get(i).getLast()) ||
                (i == buckets.size() - 1 && num >= buckets.get(i).getFirst()) ||
                (buckets.get(i).getFirst() <= num && num <= buckets.get(i).getLast()) ||
                (num > buckets.get(i).getLast() && num < buckets.get(i + 1).getFirst())) {
                    correctRange = buckets.get(i);
                    targetIndex = i;
                    break;
            }
        }

        // put num at back of correct range, and sort it to keep increasing sequence
        total_size++;
        correctRange.add(num);
        Collections.sort(correctRange);

        // if currentRange's size > threshold, split it into two halves and add them back to buckets
        int len = correctRange.size();
        if (len * len > total_size) {
            LinkedList<Integer> half1 = new LinkedList<>(correctRange.subList(0, (len) / 2));
            LinkedList<Integer> half2 = new LinkedList<>(correctRange.subList((len) / 2, len));

            buckets.set(targetIndex, half1); //replaces
            buckets.add(targetIndex + 1, half2); //inserts
        }

    }

    // iterate thru all ranges in buckets to find median value
    public double findMedian() {
        if (total_size==0) {
            return 0;
        }
            

        int mid1 = total_size / 2;
        int mid2 = mid1 + 1;

        int leftCount = 0;
        double first = 0.0, second = 0.0;
        for (List<Integer> bucket : buckets) {
            if (leftCount < mid1 && mid1 <= leftCount + bucket.size()) {
                first = bucket.get(mid1 - leftCount - 1);
            }

            if (leftCount < mid2 && mid2 <= leftCount + bucket.size()) {
                second = bucket.get(mid2 - leftCount - 1);
                break;
            }
            
            leftCount += bucket.size();
        }

        if (total_size % 2 != 0) {
            return second;
        } else {
            return (first + second) / 2;
        }
    }
}

// BST
class MedianFinder {
    class TreeNode {
        int val;
        TreeNode parent, left, right;

        public TreeNode(int val, TreeNode parent) {
            this.val = val;
            this.parent = parent;
            this.left = null;
            this.right = null;
        }

        public void add(int num) {
            if (num >= val) {
                if (right == null) {
                    right = new TreeNode(num, this);
                } else {
                    right.add(num);
                }
            } else {
                if (left == null) {
                    left = new TreeNode(num, this);
                } else {
                    left.add(num);
                }
            }
        }

        public TreeNode next() {
            TreeNode res;
            if (right != null) {
                res = right;
                while (res.left != null) {
                    res = res.left;
                }
            } else {
                res = this;
                while (res.parent.right == res) {
                    res = res.parent;
                }
                res = res.parent;
            }
            return res;
        }

        public TreeNode prev() {
            TreeNode res;
            if (left != null) {
                res = left;
                while (res.right != null)
                    res = res.right;
            } else {
                res = this;
                while (res.parent.left == res) {
                    res = res.parent;
                }
                res = res.parent;
            }
            return res;
        }
    }

    int total;
    TreeNode root, midNode;

    // Adds a number into the data structure.
    public void addNum(int num) {
        if (root == null) {
            // init
            root = new TreeNode(num, null);
            midNode = root;
            total = 1;
        } else {
            root.add(num);
            total++;
            if (total % 2 == 1) {
                if (midNode.val <= num) {
                    midNode = midNode.next();
                }
            } else if (midNode.val > num) {
                midNode = midNode.prev();
            }
        }
    }

    // Returns the median of current data stream
    public double findMedian() {
        if (total % 2 == 0) {
            return ((double) midNode.next().val + midNode.val) / 2;
        } else {
            return midNode.val;
        }
    }
};

// Order statistic tree. 顺序统计树