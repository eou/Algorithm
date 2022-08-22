# Binary Search

[Binary search algorithm](https://en.wikipedia.org/wiki/Binary_search_algorithm)

## Template

```java
class Solution {
    // iterative
    public int binarySearch(int[] nums, int target) {
        // preprocess
        if (nums == null) {
            return -1;
        }
        
        int start = 0, end = nums.length - 1;
        // terminate condition: ..., end, start, ...
        while (start <= end) {
            // note that `(start + end) / 2` may overflow
            // or `start + (end - start) >> 1;`
            int mid = start + (end - start) / 2;
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] < target) {
                start = mid + 1; 
            } else {
                end = mid - 1;
            }
        }
        
        // 35. Search Insert Position: left will be the first larger element index
        // from 0 to nums.length
        return -1;
    }
}
```

```java
class Solution {
    // recursive
    private int binarySearch(int[] nums, int start, int end, int target) {
        int mid = start + (end - start) / 2;
        
        if (nums[mid] == target) {
            return mid;
        }
        // note that `nums[mid] == target` has been checked. Thus if start == end, need to return
        // similer with non-recursion version: while (start < end) {... start = mid + 1; ... end = mid - 1; ...}
        if (start > end) {
            return -1;
        } else if (nums[mid] < target) {
            return binarySearch(nums, mid + 1, end, target);
        }
        else {
            return binarySearch(nums, start, end - 1, target);
        }
    }
    
    public int search(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return -1;
        }
        return binarySearch(nums, 0, nums.length - 1, target);
    }
}
```

**Good for finding first/last position of duplicate targets**:

```java
class Solution {
    public int binarySearch(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return -1;
        }
        
        int start = 0, end = nums.length - 1;
        // terminate condition: ..., start, end, ...
        while (start + 1 < end) {
            int mid = start + (end - start) / 2;
            if (nums[mid] == target) {
                start = mid;
            } else if (nums[mid] < target) {
                start = mid;
            } else {
                end = mid;
            }
        }

        if (nums[end] == target) {
            return end;
        }
        
        if (nums[start] == target) {
            return start;
        }

        return -1;
    }
}
```

## Right shift / Integer division

```java
// reason why we use right shift but not integer division
// is that right shift is rounded down while integer division is rounded to zero
// which means integer division might not work for negative numbers in binary search
// normally the numbers are non-positive in most situations since we usually use array index
// -3 / 2; (-1) is not the same as -3 >> 1; (-2)
while (l < r) {
  int mid = (l + r) >> 1;		// logical right shift
  // find the first one of repeat elements
  // find smallest elements which >= x
  if (a[mid] >= x) {
    r = mid;
  } else {
    l = mid + 1;
  }
}
return a[l];

while (l < r) {
  int mid = (l + r + 1) >> 1;		// l = r, mid == (l + r) >> 1 will trap in infinite loop if always jump into l = mid; branch
  // find the last one of repeat elements
  // find largest elements which <= x
  if (a[mid] <= x) {
    l = mid;
  } else {
    r = mid - 1;
  }
}
return a[l];

// notice that the priority of >> or << are less than + -
// thus mid = mid = l + (r - l) >> 1; is wrong, should be mid = (r + l) >> 1; or mid = l + ((r - l) >> 1);
// If use mid = l + ((r - l) >> 1); to find largest elements which <= x
// maybe infinite loop, the reason is the same as above
// we should use mid = l + ((r - l + 1) >> 1);
// which is the same as mid = (l + r + 1) >> 1;
// but will avoid overflow
```

## Termination

- `start <= end`：after breaking the loop, start - 1 = end，no target
- `start < end`：after breaking the loop, start = end，target only one element (infinite loop might happen)
```java
while (left < right) {
    int mid = left + (right - left) / 2;
    if (nums[mid] == target) {
        // infinite loop since when left + 1 = right, left is always equal with mid
        left = mid;
    } else if (nums[mid] < target) {
        left = mid + 1;
    } else {
        right = mid - 1;
    }
}
```
- `start + 1 < end`：after breaking the loop, start + 1= end，need compare last two elements

