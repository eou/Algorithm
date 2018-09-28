// 453. Minimum Moves to Equal Array Elements
// 一道数学题
class Solution {
    // brute-force 必然会TLE
    public int minMoves(int[] nums) {
        int steps = 0;
        while (!allEqual(nums)) {
            Arrays.sort(nums);
            for(int i = 0; i < nums.length - 1; ++i) {
                nums[i]++;
            }
            steps++;
        }
        return steps;
    }
    
    private boolean allEqual(int[] nums) {
        if (nums.length == 1) {
            return true;
        }
        for(int i = 0; i < nums.length - 1; ++i) {
            if (nums[i] != nums[i + 1]) {
                return false;
            }
        }
        return true;
    }
}

class Solution {
    // 先排序，然后每一阶段将最小值增加到与最大值相等，每一阶段多一个相等的数字
    // [1,4,7,9]
    // [9,12,15,9]
    // [15,18,15,15]
    // [18,18,18,18]
    public int minMoves(int[] nums) {
        Arrays.sort(nums);
        int count = 0;
        for (int i = nums.length - 1; i > 0; i--) {
            count += nums[i] - nums[0];
        }
        return count;
    }
}

class Solution {
    // assume we need x steps, (x + min) * n = sum + x * (n - 1)
    // so x = sum - n * min
    public int minMoves(int[] nums) {
        int moves = 0, min = Integer.MAX_VALUE;
        for (int i = 0; i < nums.length; i++) {
            moves += nums[i];
            min = Math.min(min, nums[i]); // 如防止moves溢出，可以先遍历算出min，然后每次加moves都减去min
        }
        return moves - min * nums.length;
    }
}
