// 503. Next Greater Element II
// Use 496. Next Greater Element I way, reverse traverse
class Solution {
    // !!! contain duplicate
    public int[] nextGreaterElements(int[] nums) {
        if (nums == null || nums.length == 0) {
            return new int[]{};
        }
        
        // expand input array
        int[] nums1 = new int[nums.length * 2];
        for (int i = 0; i < nums1.length; i++) {
            nums1[i] = nums[i % nums.length];
        }
        
        int[] res = nextGreaterElement(nums1);
        for (int i = 0; i < nums.length; i++) {
            nums[i] = res[i];
        }
        
        return nums;
    }
    
    // 496
    public int[] nextGreaterElement(int[] nums2) {
        if (nums2 == null || nums2.length == 0) {
            return new int[]{};
        }
        
        int[] res = new int[nums2.length];
        Map<Integer, Integer> map = new HashMap<>();
        // !!! input might contain -1
        map.put(nums2[nums2.length - 1], Integer.MIN_VALUE);
        for (int i = nums2.length - 2; i >= 0; i--) {
            if (nums2[i + 1] > nums2[i]) {
                res[i] = nums2[i + 1];
                map.put(nums2[i], nums2[i + 1]);
            } else {
                int nextGlt = nums2[i + 1];
                while (nextGlt != Integer.MIN_VALUE && nextGlt <= nums2[i]) {
                    nextGlt = map.get(nextGlt);
                }
                res[i] = nextGlt == Integer.MIN_VALUE ? -1 : nextGlt;
                map.put(nums2[i], nextGlt);
            }
        }
        
        return res;
    }
}

// Monotonic stack, traversing-order
class Solution {
    // contain duplicate
    public int[] nextGreaterElements(int[] nums) {
        Deque<Integer> stack = new ArrayDeque<>(); // store index
        
        int[] res = new int[nums.length];
        Arrays.fill(res, -1);
        
        for (int i = 0; i < 2 * nums.length - 1; ++i) {
            int num = nums[i % nums.length];
            while (!stack.isEmpty() && nums[stack.peek()] < num) {
                res[stack.poll()] = num;
            }
            if (i < nums.length) {
                stack.push(i);
            }
        }
        
        return res;
    }
}