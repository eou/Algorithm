// 1. Two Sum
class Solution {
    // brute-force
    public int[] twoSum(int[] nums, int target) {
        int[] res = new int[2];
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[j] == target - nums[i]) {
                    res[0] = i;
                    res[1] = j;
                    return res;
                }
            }
        }
        // 这里不写return 会编译报错，加一个抛出异常可以过
        // throw new IllegalArgumentException("No two sum solution");
        return res;
    }
}

class Solution {
    // HashMap
    public int[] twoSum(int[] nums, int target) {
        List<Integer> list = new ArrayList<>();
        int[] res = new int[2];
        // Map<number, index>
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; ++i) {
            if (map.containsKey(target - nums[i])) {
                res[0] = map.get(target - nums[i]);
                res[1] = i;
                return res;
            }
            map.put(nums[i], i);
        }
        // throw new IllegalArgumentException("No two sum solution");
        return res;
    }
}