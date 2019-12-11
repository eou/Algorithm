// 189. Rotate Array
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

class Solution {
    public void rotate(int[] nums, int k) {
        k %= nums.length;
        reverse(nums, 0, nums.length - 1);
        reverse(nums, 0, k - 1);
        reverse(nums, k, nums.length - 1);
    }

    public void reverse(int[] nums, int start, int end) {
        while(start < end) {
            int tmp = nums[start];
            nums[start] = nums[end];
            nums[end] = tmp;
            start++;
            end--;
        }
    }
}