// 283. Move Zeroes
class Solution {
    // 次优解，把非0的数字复制到前面去，然后最后补0
    public void moveZeroes(int[] nums) {
        int nonZeroPos = 0;
        for(int i = 0; i < nums.length; i++) {
            if(nums[i] != 0) {
                nums[nonZeroPos] = nums[i];
                nonZeroPos++;
            }
        }
        
        for(int i = nonZeroPos; i < nums.length; i++) {
            nums[i] = 0;
        }
    }
}

class Solution {
    // 次优解，把非0的数字交换到前面去，但操作次数不是最优，因为不是所有前面有 0 的非0元素都需要交换位置
    public void moveZeroes(int[] nums) {
        int nonZeroPos = 0;
        for(int i = 0; i < nums.length; i++) {
            if(nums[i] != 0) {
                int tmp = nums[i];
                nums[i] = nums[nonZeroPos];
                nums[nonZeroPos] = tmp;
                nonZeroPos++;
            }
        }
    }
}

/**
 * 一个 follow up 是给出对原数组写入次数最小的 in-place 解，读取次数不限，可以不用保持原始相对位置：
 * 为了让 0 和非 0 数字分开，从 [X, X, X, 0, 0, X, 0, X, X, 0] 变为 
 *                          [X, X, X, X, X, X, 0, 0, 0, 0],
 * 假设 m 个 0 和 n 个 非0，则前 n 个元素中含有的 0 必须被非0 覆盖，而后 m 个 元素中的 非0 必须被 0 覆盖
 * 思路如遍历一次，能得到 m 的值和 “前继元素含有 0 的所有非0 元素”，不过我们无法确定是否所有“前继元素含有 0 的所有非0 元素”都需要移动
 * 如从两头开始同时遍历，保证 left 的左边全是非0 和 right 的右边全是 0 即能最少操作
 */
class Solution {
    // 最优解
    public void moveZeroes(int[] nums) {
        // 保证 left 与 right 相遇之前 left 左边全是非0，right 右边全是0
        int left = 0;
        int right = nums.length - 1;
        while(left < right) {
            while(nums[left] != 0 && left < right) {
                left++;
            }
            while(nums[right] == 0 && left < right) {
                right--;
            }
            int tmp = nums[left];
            nums[left] = nums[right];
            nums[right] = tmp;
            left++;
            right--;
        }
    }
}

class Solution {
    // 最优解另一个版本
    public void moveZeroes(int[] nums) {
        for (int left = 0, right = nums.length - 1; left < nums.length; left++) {
            if (left > right) {
                nums[left] = 0;
                continue;
            }
            // 找到接下来第一个0
            if (nums[left] != 0) {
                continue;
            }
            while (nums[right] == 0) {
                right--;
            }
            nums[left] = nums[right];
            right--;
        }
    }
}

class Solution {
    // 保持原始相对位置的最优解
    public void moveZeroes(int[] nums) {
        int zeroNum = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 0) {
                zeroNum++;
            }
            if (nums[i] != 0) {
                // 如果 zeroNum == 0，说明前面一个0都没有，不用复制过去
                if (zeroNum != 0) {
                    nums[i - zeroNum] = nums[i];
                }
            }
        }
        for (int i = nums.length - zeroNum; i < nums.length; i++) {
            if (nums[i] != 0) {
                nums[i] = 0;
            }
        }
    }
}
