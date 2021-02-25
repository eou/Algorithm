// 153. Find Minimum in Rotated Sorted Array
// 1. 比较数组首尾元素
// 2. 比较区间首尾元素
// 推荐比较数组 / 区间尾元素，因为跟数组 / 区间首元素比较都要处理 sorted array 极端情况
// 比较区间（子数组）尾元素更优，因为每一次二分实际上都抛弃了原数组的一部分，比较数组尾元素不适用于
class Solution {
    // Solution 1：比较 nums[nums.length - 1]
    public int findMin(int[] nums) {
        int left = 0, right = nums.length - 1;
        // test 2 cases: rotated sorted array and sorted array
        // test 2 ways: compare with first or last number?
        while (left + 1 < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] > nums[nums.length - 1]) {
                left = mid;
            } else if (nums[mid] < nums[nums.length - 1]) {
                right = mid;
            } else {
                // [0, 1, 1, 1]
                right = mid;
            }
        }

        // [7, 0]
        if (nums[left] < nums[right]) {
            return nums[left];
        } else {
            return nums[right];
        }
    }
}

class Solution {
    // Solution 1：比较 nums[nums.length - 1]
    public int findMin(int[] nums) {
        int start = 0, end = nums.length - 1;

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

class Solution {
    // Solution 2: 比较 nums[end]
    // 154. Find Minimum in Rotated Sorted Array II
    public int findMin(int[] nums) {
        if (nums == null || nums.length == 0) {
            return -1;
        }

        int start = 0, end = nums.length - 1;
        while (start + 1 < end) {
            int mid = start + (end - start) / 2;
            if (nums[mid] > nums[end]) {
                start = mid;
            } else {
                end = mid;
            }
        }

        if (nums[start] < nums[end]) {
            return nums[start];
        } else {
            return nums[end];
        }
    }
}

class Solution {
    // Solution 3: 比较 nums[0]
    public int findMin(int[] nums) {
        int start = 0, end = nums.length - 1;
        // 这个处理不是用于简化算法！
        // 去掉它会导致极端情形也就是 sorted array 运行结果错误
        if (nums[start] < nums[end]) {
            return nums[start];
        }

        // 以下部分只适用于 [O,O,O,O,...,O,X,X,X,...,X]，必须有O,X转折点存在
        while (start < end) {
            int mid = (start + end) / 2;
            // 此时 mid 在整个数组的后半部分(也就是这里无法适用于sorted array)
            // 注意这是跟 nums[0]比较，判断 mid 在整个数组的前半还是后半部分
            if (nums[mid] < nums[0]) {
                // 相当于找rotated sorted array后半部分的首元素
                end = mid;
            } else {
                start = mid + 1;
            }
        }

        return nums[start];
    }
}

class Solution {
    // Solution 4：比较 nums[start]
    public int findMin(int[] nums) {
        int start = 0, end = nums.length - 1;

        while (start < end) {
            // 由于后面是跟 nums[start] 比较，当处于“子数组”的后半部分时候不能start = mid + 1了，而是直接取递增数组的首元素
            if (nums[start] < nums[end]) {
                return nums[start];
            }
            int mid = (start + end) / 2;

            if (nums[mid] < nums[start]) {
                end = mid;
            } else {
                start = mid + 1;
            }
        }

        return nums[start];
    }
}