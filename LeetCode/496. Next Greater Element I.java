// 496. Next Greater Element I
class Solution {
    // brute force
    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        int[] results = new int[nums1.length];
        for (int i = 0; i < nums1.length; ++i) {
            boolean isFind = false;
            for (int j = 0; j < nums2.length && !isFind; ++j) {
                if (nums2[j] == nums1[i]) {
                    for (int k = j + 1; k < nums2.length; ++k) {
                        if (nums2[k] > nums2[j]) {
                            results[i] = nums2[k];
                            isFind = true;
                            break;
                        }
                    }
                }
                if (!isFind) {
                    results[i] = -1;
                }
            }
        }

        return results;
    }
}

class Solution {
    // 单调栈
    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        Map<Integer, Integer> map = new HashMap<>();
        Deque<Integer> stack = new ArrayDeque<>();
        for (int i = 0; i < nums2.length; ++i) {
            while (!stack.isEmpty() && stack.peek() < nums2[i]) {
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