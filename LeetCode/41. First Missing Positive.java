// 41. First Missing Positive
// brute-force
class Solution {
    public int firstMissingPositive(int[] nums) {
        int[] positive = new int[nums.length + 1];
        for (int i = 0; i < positive.length; i++) {
            positive[i] = i;
        }

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] >= 1 && nums[i] <= nums.length) {
                positive[nums[i]] = -1;
            }
        }

        int res = -1;
        for (int i = 1; i < positive.length; i++) {
            if (positive[i] != -1) {
                res = i;
                break;
            }
        }

        return res == -1 ? nums.length + 1 : res;
    }
}

// brute-force, don't need to init positive array
class Solution {
    public int firstMissingPositive(int[] nums) {
        int[] positive = new int[nums.length + 1];

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] >= 1 && nums[i] <= nums.length) {
                positive[nums[i]] = -1;
            }
        }

        int res = -1;
        for (int i = 1; i < positive.length; i++) {
            if (positive[i] != -1) {
                res = i;
                break;
            }
        }

        return res == -1 ? nums.length + 1 : res;
    }
}

class Solution {
    public int firstMissingPositive(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] >= 1 && nums[i] <= nums.length) {
                int num = nums[i];
                if (num != i + 1) {
                    if (nums[num - 1] == nums[i]) {
                        // remove duplicate
                        nums[i] = -1;
                    } else {
                        // swap nums[i] with nums[nums[i] - 1]
                        int tmp = num;
                        nums[i] = nums[num - 1];
                        nums[num - 1] = tmp;
                    }

                    i--;
                }
            }
        }

        int res = -1;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != i + 1) {
                res = i + 1;
                break;
            }
        }

        return res == -1 ? nums.length + 1 : res;
    }
}

class Solution {
    public int firstMissingPositive(int[] nums) {
        // 1 should be in nums[0], 2 should be in nums[1], ..., n should be in nums[n - 1]
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > 0 && nums[i] <= nums.length && nums[i] != nums[nums[i] - 1]) {
                swap(nums, i, nums[i] - 1);
            }
        }

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != i + 1) {
                return i + 1;
            }
        }

        return nums.length + 1; // 如果全部是正确排列，就返回数组中最大正整数的后一个
    }

    private void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }
}