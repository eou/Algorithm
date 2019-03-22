// 16. 3Sum Closest
class Solution {
    public int threeSumClosest(int[] nums, int target) {
        Arrays.sort(nums);
        int diff = Integer.MAX_VALUE;
        int sum = 0;
        for (int i = 0; i < nums.length; ++i) {
            int left = i + 1, right = nums.length - 1;
            while (left < right) {
                int current = nums[i] + nums[left] + nums[right];
                if (Math.abs(current - target) < diff) {
                    diff = Math.abs(current - target);
                    sum = current;
                }
                if (current < target) {
                    ++left;
                } else {
                    --right;
                }
            }
        }
        
        return sum;
    }
}

class Solution {
    public int threeSumClosest(int[] nums, int target) {
        Arrays.sort(nums);
        int diff = Integer.MAX_VALUE;
        int closest = Integer.MAX_VALUE;
        for (int i = 0; i < nums.length - 2; i++) {
            if (i == 0 || nums[i] != nums[i - 1]) {
                int left = i + 1, right = nums.length - 1;
                while (left < right) {
                    int sum = nums[i] + nums[left] + nums[right];
                    if (Math.abs(target - sum) < diff) {
                        diff = Math.abs(target - sum);
                        closest = sum;
                    }
                    // 这里左右指针是轮流移动而不是同时移动
                    if (sum < target) {
                        while (left < right && nums[left] == nums[left + 1]) {
                            left++;
                        }
                        left++;
                    } else {
                        while (left < right && nums[right] == nums[right - 1]) {
                            right--;
                        }
                        right--;
                    }
                }
            }
        }

        return closest;
    }
}
