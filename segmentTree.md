# Segment Tree

[Segment Tree](https://en.wikipedia.org/wiki/Segment_tree)

> This article is about the data structure used to store segments. For the data structure used to efficiently calculate prefix sums, see Fenwick tree.

For other similar data structure, visit [What are the differences between segment trees, interval trees, binary indexed trees and range trees?](https://stackoverflow.com/questions/17466218/what-are-the-differences-between-segment-trees-interval-trees-binary-indexed-t)

Each leaf node represents an element in the array.

Each non-leaf node covers the union of its children's range.

- build: `O(n)`
- update: `O(logn)`
- rangeQuery: `O(logn + k)`

## Template

[SegmentTree.java (Array version)](https://algs4.cs.princeton.edu/99misc/SegmentTree.java.html)

```java
class SegmentTree {
    class SegmentTreeNode {
        int start;  // interval start
        int end;    // interval end
        int size;   // interval length
        int sum;
        // optional
        // int min;
        // int max;
        SegmentTreeNode left;
        SegmentTreeNode right;
    }

    // O(n)
    SegmentTree(int[] arr, int l, int r) {
        if (l == r) {
            SegmentTreeNode node = new SegmentTreeNode();
            node.start = l;
            node.end = r;
            node.sum = arr[l];
            node.size = 1;
            node.left = null;
            node.right = null;
            return node;
        }

        int mid = l + (r - l) / 2;
        SegmentTreeNode left = SegmentTree(arr, l, mid);
        SegmentTreeNode right = SegmentTree(arr, mid, r);
        SegmentTreeNode root = new SegmentTreeNode();
        root.start = left.start;
        root.end = right.end;
        root.sum = left.sum + right.sum;
        root.left = left;
        root.right = right;
        return root;
    }

    // O(logn)
    public void update(SegmentTreeNode node, int index, int newValue) {
        if (root.start == root.end && index = root.start) {
            root.sum = newValue;
            return;
        }

        int mid = root.start + (root.end - root.start) / 2;
        if (index <= mid) {
            update(root.left, index, newValue);
        } else {
            update(root.right, index, newValue);
        }
        root.sum = root.left.sum + root.right.sum;
    }

    // O(logn + k), k is number of node covered
    public int query(SegmentTreeNode root, int i, int j) {  // [i, j]
        if (root.start == i && root.end == j) {
            return root.sum;
        }

        int mid = root.start + (root.end - root.start) / 2;
        if (i > mid) {
            return query(root.right, i, j);
        }
        if (j <= mid) {
            return query(root.left, i, j);
        }
        return query(root.left, i, mid) + query(root.right, mid + 1, j);
    }
}
```