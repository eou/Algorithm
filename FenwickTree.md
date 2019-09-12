# Fenwick Tree / Binary Indexed Tree

[Fenwick Tree](https://en.wikipedia.org/wiki/Fenwick_tree)
[Binary Indexed Trees](https://www.topcoder.com/community/competitive-programming/tutorials/binary-indexed-trees)

Given a table of elements, we can get prefix sum in `O(logn)` and update prefix sum in `O(logn)`.

## Template

```java
class FenwickTree {
    public List<Integer> nums;
    FenwickTree(int n) {
        nums = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            nums.add(i);
        }
    }

    // prefix sum of nums[0...i]
    public int query(int i) {
        int sum = 0;
        // from leaf to root
        while (i > 0) {
            sum += nums.get(i);
            i -= lowbit(i);
        }
        return sum;
    }

    // update one element by increasing delta
    public void update(int i, int delta) {
        while (i < nums.size()) {
            nums.set(i, nums.get(i) + delta);
            i += lowbit(i);
        }
    }

    // 5 = 0110, -5 = ~(0110) + 1 = 1010, lowbit(x) = 0110 & 1010 = 0010
    // !!! i should not be 0, otherwise infinite loop because 0 & -0 = 0
    private static int lowbit(int x) {
        return x & (-x);
    }
}
```