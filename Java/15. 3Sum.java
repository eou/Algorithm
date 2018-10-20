// 15. 3Sum
class Solution {
    // 思路直接，数组排序后按顺序在一个元素之后的所有元素中找2Sum
    // 时间复杂度O(n^2)
    public List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> results = new ArrayList<>();
        for(int i = 0; i < nums.length - 2; i++) {
            if(i == 0 || nums[i] != nums[i - 1]) {
                int left = i + 1, right = nums.length - 1, sum = 0 - nums[i];
                while(left < right) {
                    if(nums[left] + nums[right] == sum) {
                        // List<Integer> tmp = new ArrayList<>();
                        // tmp.add(nums[i]);
                        // tmp.add(nums[left]);
                        // tmp.add(nums[right]);
                        // results.add(tmp);
                        // 注意用Arrays.asList获得的数组没有add和remove方法
                        results.add(Arrays.asList(nums[i], nums[left], nums[right]));
                        while(left < right && nums[left] == nums[left + 1]) {
                            left++;
                        }
                        while(left < right && nums[right] == nums[right - 1]) {
                            right--;
                        }
                        left++;
                        right--;
                    } else if(nums[left] + nums[right] < sum) {
                        // 小优化无关紧要
                        // while (left < right && nums[left] == nums[left + 1]) {
                        //     left++;
                        // }
                        left++;
                    } else {
                        // 小优化无关紧要
                        // while (left < right && nums[right] == nums[right - 1]) {
                        //     right--;
                        // }
                        right--;
                    }
                }
            }
        }
        
        return results;
    }
}