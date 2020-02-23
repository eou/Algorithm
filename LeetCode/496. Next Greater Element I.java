// 496. Next Greater Element I
// brute-force
class Solution {
    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        for (int i = 0; i < nums1.length; i++) {
            boolean targetExist = false;
            int j = 0;
            for (; j < nums2.length; j++) {
                if (targetExist && nums2[j] > nums1[i]) {
                    nums1[i] = nums2[j];
                    break;
                }
                if (nums2[j] == nums1[i]) {
                    // find target
                    targetExist = true;
                }
            }
            if (!targetExist || j == nums2.length) {
                nums1[i] = -1;
            }
        }

        return nums1;
    }
}

// Map, reverse traversing-order
// Rule: start from last num to first num
// if nums2[i] is bigger than nums2[i - 1], thus next greater element of nums2[i - 1] is nums2[i]the next greater element of nums2[i - 1]
// else the next greater element of nums2[i - 1] is the next greater element of nums2[i - 1] or the next greater element of "the next greater element of nums2[i - 1]"
class Solution {
    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        if (nums2 == null || nums2.length == 0) {
            return new int[]{};
        }
        
        Map<Integer, Integer> map = new HashMap<>();
        map.put(nums2[nums2.length - 1], -1);
        for (int i = nums2.length - 2; i >= 0; i--) {
            if (nums2[i + 1] > nums2[i]) {
                map.put(nums2[i], nums2[i + 1]);
            } else {
                int nextGlt = nums2[i + 1];
                while (nextGlt != -1 && nextGlt <= nums2[i]) {
                    nextGlt = map.get(nextGlt);
                }
                map.put(nums2[i], nextGlt);
            }
        }
        
        for (int i = 0; i < nums1.length; i++) {
            // subset, must contains
            nums1[i] = map.get(nums1[i]);
        }
        
        return nums1;
    }
}

// Monotonic stack, forward
class Solution {
    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        Map<Integer, Integer> map = new HashMap<>();
        Deque<Integer> stack = new ArrayDeque<>();
        for (int i = 0; i < nums2.length; ++i) {
            while (!stack.isEmpty() && stack.peek() < nums2[i]) {
                // find a next greater element for stack top element
                map.put(stack.pop(), nums2[i]);
            }
            stack.push(nums2[i]);
        }

        int[] results = new int[nums1.length];
        for (int i = 0; i < nums1.length; ++i) {
            results[i] = map.getOrDefault(nums1[i], -1);
        }
        return results;
    }
}