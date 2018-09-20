# 1 二分模板

```java
class Solution {
    public int binarySearch(int[] nums, int target) {
        // 预处理
        if (nums == null || nums.length == 0) {
            return -1;
        }
        
        int start = 0, end = nums.length - 1;
        // 循环终止条件
        while (start <= end) {
            // 注意直接计算 (start + end) / 2 可能溢出
            // 使用 int mid = start + (end - start) >> 1 也可
            int mid = start + (end - start) / 2;
            // 核心
            if (nums[mid] == target) {
                return mid;
            }
            else if (nums[mid] < target) {
                start = mid + 1; 
            }
            else {
                end = mid - 1;
            }
        }
        
        return -1;
    }
}
```

## 循环终止条件/首尾指针移动

- `start <= end`：跳出循环时 start - 1 = end，查找元素失败
- `start < end`：跳出循环时 start = end，定位唯一元素
- `start + 1 < end`：跳出循环时 start + 1 = end，最后需要继续比较两个元素

一般地，首尾指针`start`和`end`移动靠中间指针`mid`的变化。

注意`start = mid`和`start = mid + 1`，`end = mid`和`end = mid - 1`的区别，如使用两个前者一不小心容易造成死循环。

一种可能：当`start`与`end`相邻之时，此时`start == mid`可能导致指针不再移动，可以使用`start = mid + 1`避免，或者循环条件改为`start + 1 < end`.

因此，使用`start <= end`或者`start < end`当做循环条件，指针移动必须至少含有一个`start = mid + 1`或者`end = mid - 1`以避免死循环。

保险方法是使用`start + 1 < end`与`start = mid`和`end = mid`，最后跳出循环处理一下剩余两个相邻元素。

## 递归形式

每次二分得到的子数组都是可以继续二分，本质是递归。

但递归形式少见，而且性能差。

```java
class Solution {
    private int binarySearch(int[] nums, int start, int end, int target) {
        int mid = start + (end - start) / 2;
        
        if (nums[mid] == target) {
            return mid;
        }
        // 注意一下终止条件，由于提前判断了nums[mid] == target，因此如果此时start == end 要跳出
        // 这里就很像非递归形式中 while (start >= end) {... start = mid + 1; ... end = mid - 1; ...}
        if (start >= end) {
            return -1;
        }
        else if (nums[mid] < target) {
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



# 2 二分变式

基本的二分查找可以拓展很多变式。精髓就是去除无解的一半，保留有解的另一半：

- 满足不同的条件
- 查找数列中锚点
- 筛选排除部分解

一般地，能优化到$O(logn)$时间复杂度的题很可能涉及到二分查找。

[34. Find First and Last Position of Element in Sorted Array](https://leetcode.com/problems/find-first-and-last-position-of-element-in-sorted-array/description/)

[278. First Bad Version](https://leetcode.com/problems/first-bad-version/description/)

[74. Search a 2D Matrix](https://leetcode.com/problems/search-a-2d-matrix/description/)

[702. Search in a Sorted Array of Unknown Size](https://leetcode.com/problems/search-in-a-sorted-array-of-unknown-size/description/)

[153. Find Minimum in Rotated Sorted Array](https://leetcode.com/problems/find-minimum-in-rotated-sorted-array/description/)

[154.Find Minimum in Rotated Sorted Array II](https://leetcode.com/problems/find-minimum-in-rotated-sorted-array-ii/)

[162. Find Peak Element](https://leetcode.com/problems/find-peak-element/)

[33. Search in Rotated Sorted Array](https://leetcode.com/problems/search-in-rotated-sorted-array/description/)

[81. Search in Rotated Sorted Array II](https://leetcode.com/problems/search-in-rotated-sorted-array-ii/description/)

还有一些比较难且怪异的变式。

## First / Last Position

查找数组中某个重复元素的首尾位置。

```java
class Solution {
    public int[] findFirstPosition(int[] nums, int target) {
        if(nums == null || nums.length == 0) {
            return -1;
        }
        
        int start = 0, end = nums.length - 1;
        while (start + 1 < end) {
            int mid = start + (end - start) / 2;
            if (nums[mid] == target) {
                // end = mid 意味着子数组尾部不断向前缩进，重复元素首部不会丢失
                // 反之如 start = mid 则是查找 last position
                end = mid;
            }
            else if (nums[mid] < target) {
                start = mid;
            }
            // 这个else分支可以合并，但为了直观不宜合并
            else {
                end = mid;
            }
        }
        
        // 因为总要保留两个元素，不清楚前者还是后者为target
        // 如查找 last position，先判断 nums[end] 再判断 nums[start]
        if (nums[start] == target) {
            return start;
        }
        else if(nums[end] == target) {
            return end;
        }
        
        // 题目前提是总存在重复元素，不会到最后无数可返
    }
}
```

## 寻找锚点

一般地，锚点的位置查找有点类似于查找重复元素的首元素。但是要研究一下首尾指针移动满足的条件。

这种返回一个元素的题一般用`start < end`比较简便，但是注意指针移动，避免死循环。

```java
class Solution {
    // Solution 1: 比较 nums[0]
    public int findMinimumInRotatedSortedArray(int[] nums) {
        int start = 0, end = nums.length - 1;
        // 这个处理很微妙，不是用于简化算法！
        // 去掉它会导致极端情形也就是sorted array运行结果错误
        if (nums[start] < nums[end]) {
            return nums[start];
        }
        
        // 以下部分只适用于[O,O,O,O,...,O,X,X,X,...,X]，必须有O,X转折点存在
        while (start < end) {
            int mid = (start + end) / 2;
            // 此时 mid 在整个数组的后半部分(也就是这里无法适用于sorted array)
            // 注意这是跟 nums[0]比较，判断 mid 在整个数组的前半还是后半部分
            if (nums[mid] < nums[0]) {
                // 相当于找rotated sorted array后半部分的首元素
                end = mid;
            }
            else {
            	start = mid + 1;
            }
        }
        
        return nums[start];
    }
    
    // Solution 2：比较 nums[start]
    public int findMinimumInRotatedSortedArray(int[] nums) {
        int start = 0, end = nums.length - 1;
        
        while (start < end) {
            // 由于后面是跟 nums[start] 比较，当处于“子数组”的后半部分时候不能start = mid + 1了，而是直接取递增数组的首元素
       		if (nums[start] < nums[end]) {
                return nums[start];
            }
            int mid = (start + end) / 2;
            
            if (nums[mid] < nums[start]) {
                end = mid;
            }
            else {
            	start = mid + 1;
            }
        }
        
        return nums[start];
    }
    
    // Solution 3：比较 nums[nums.length - 1]
    public int findMinimumInRotatedSortedArray(int[] nums) {
        int start = 0, end = nums.length - 1;
        
        // 用 start + 1 < end 需要最后处理两个数
        //         while (start + 1 < end) {
        //             int mid = start + (end - start) / 2;
        //             if (nums[mid] <= nums[nums.length - 1]) {
        //                 end = mid;
        //             } 
        // 				else {
        //                 start = mid;
        //             }
        //         }

        //         if (nums[start] <= nums[nums.length - 1]) {
        //             return nums[start];
        //         }
        //         else {
        //             return nums[end];
        //         }
        
        while (start < end) {
            int mid = start + (end - start) / 2;
            if (nums[mid] <= nums[nums.length - 1]) {
                end = mid;
            } 
            // 大于的情况肯定不是最小值，可以 +1
            else {
                start = mid + 1;
            }
        }
        
        return nums[start];
    }
}
```

```java
class Solution {
    public int firstBadVersion(int n) {
        int start = 1, end = n;
        
        while (start < end) {
            int mid = start + (end - start) / 2;
            if (isBadVersion(mid)) {
                // 相当于找后半部分的首元素
                end = mid;
            }
            else {
                start = mid + 1;
            }
        }
        return start;
    }
}
```

```java
class Solution {
    public int findPeakElement(int[] nums) {
        int start = 0, end = nums.length - 1;
        while (start < end) {
            int mid = start + (end - start) / 2;
            // 此时 mid 在递减数列上
            // 不能 end = mid - 1; 因为 mid 本身可能是极大值
            if (nums[mid] > nums[mid + 1]) {
                end = mid;
            }
            // 不大于的时候可以 +1，因为 mid 本身肯定不会是极大值
            else {
                start = mid + 1;
            }
        }
        return start;
    }
}
```

明显看出这类题目代码结构很相似，在满足条件的时候`end = mid`，其他情况可以`start = mid + 1`.

PS. 注意到增加重复元素后，Find Minimum in Rotated Sorted Array II最坏复杂度为$O(n)$，因此一般for循环即可。

```java
// someone's solution
class Solution {
    public int findMinimumInRotatedSortedArrayII (int[] nums) {
        int start = 0, end = nums.length - 1;
        while (start < end) {
            int mid = start + (end - start) / 2;
            if (nums[mid] > nums[end]) {
                start = mid + 1;
            }
            else if (nums[mid] < nums[end]) {
                end = mid;
            }
            // 此时不清楚哪边是sorted，所以不能用 end = mid，有可能会跳过最小值，[3,3,3,1,3]
            // 这里时间复杂度就接近线性了
            else {
                end--;
            }
        }
        return nums[start];
    }
}
```

## 筛选一半

典型的就是[33. Search in Rotated Sorted Array](https://leetcode.com/problems/search-in-rotated-sorted-array/description/)了。

```java
class Solution {
    public int search (int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return -1;
        }
        
        int start = 0, end = nums.length - 1;
        while (start < end) {
            int mid = start + (end - start) / 2;
            if (nums[mid] == target) {
                return mid;
            }
            
            // 由于没有重复元素，当 nums[mid] == nums[start] 成立的时候就是 mid == start 的时候，此时 start + 1 = end
            // 因为这种情况下之前已经验证 nums[mid] != target，所以需要 start = mid + 1
            // 这也可以单独拿出来作为一个else语句，更加直观
            if (nums[mid] >= nums[start]) {
                if (nums[mid] > target && nums[start] <= target) {
                    end = mid - 1;
                }
                else {
                    start = mid + 1;
                }
            }
            else {
                if (nums[mid] < target && nums[end] >= target) {
                    start = mid + 1;
                }
                else {
                    end = mid - 1;
                }
            }
        }
        
        if (nums[start] == target) {
            return start;
        }
        return -1;
    }
}
```

对于Search in Rotated Sorted Array II，需要多一个对`nums[start] == nums[mid] == nums[end]`的处理，也就是`start++; end--;`.

