// 189. Rotate Array
// LintCode - 8. Rotate String
class Solution {
    public void rotate(int[] nums, int k) {
        // LeetCode has no empty input, but LintCode does.
        if (nums.length == 0) {
            return;
        }

        k %= nums.length;
        reverse(nums, nums.length - k, nums.length - 1);
        reverse(nums, 0, nums.length - k - 1);
        reverse(nums, 0, nums.length - 1);
    }

    private void reverse(int[] nums, int start, int end) {
        for (int left = start, right = end; left >= 0 && left < right; left++, right--) {
            int mid = nums[left];
            nums[left] = nums[right];
            nums[right] = mid;
        }
    }
}

class Solution {
    public void rotate(int[] nums, int k) {
        int[] helper = new int[nums.length];
        for(int i = 0; i < nums.length; i++) {
            helper[(i + k) % nums.length] = nums[i];
        }
        for(int i = 0; i < nums.length; i++) {
            nums[i] = helper[i];
        }
    }
}

class Solution {
    public void rotate(int[] nums, int k) {
        k %= nums.length;
        for(int i = 0, count = 0; count < nums.length; i++) {
            int curr = i;
            do {
                int next = (curr + k) % nums.length;
                int tmp = nums[next];
                nums[next] = nums[i];
                nums[i] = tmp;

                curr = next;
                count++;
            } while(i != curr);
        }
    }
}