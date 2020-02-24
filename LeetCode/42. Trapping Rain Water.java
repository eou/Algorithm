// 42. Trapping Rain Water
// calculate each bar's water depends on height, minimum height between left highest bar and right highest bar
class Solution {
    public int trap(int[] height) {
        int[] leftMost = new int[height.length];
        int[] rightMost = new int[height.length];
        int leftMax = 0;
        for (int i = 0; i < height.length; i++) {
            leftMax = Math.max(leftMax, height[i]);
            leftMost[i] = Math.max(height[i], leftMax);
        }
        int rightMax = 0;
        for (int i = height.length - 1; i >= 0; i--) {
            rightMax = Math.max(rightMax, height[i]);
            rightMost[i] = Math.max(height[i], rightMax);
        }

        int result = 0;
        for (int i = 0; i < height.length; i++) {
            int level = Math.min(leftMost[i], rightMost[i]);
            result += level - height[i];
        }
        return result;
    }
}

// time complexity O(n), space complexity O(1)
class Solution {
    public int trap(int[] height) {
        int leftMax = 0, rightMax = 0;
        int result = 0;
        int left = 0, right = height.length - 1;
        while (left < right) {
            // tricky thing is that only when the opposite side has higher bar, pointer will return to another side thus we can use max bar in this side as a level
            if (height[left] < height[right]) {
                // use shorter side as critical side
                if (height[left] > leftMax) {
                    leftMax = height[left];
                } else {
                    result += leftMax - height[left];
                }
                left++;
            } else {
                // use shorter side as critical side
                if (height[right] > rightMax) {
                    rightMax = height[right];
                } else {
                    result += rightMax - height[right];
                }
                right--;
            }
        }
        return result;
    }
}

// simpler way
class Solution {
    public int trap(int[] height) {
        int l = 0, r = height.length - 1, level = 0, res = 0;
        while (l < r) {
            int lower = height[(height[l] < height[r]) ? l++ : r--];
            level = Math.max(level, lower);
            res += level - lower;
        }
        return res;
    }
}

// O(n^2)
// horizontal calculate
class Solution {
    public int trap(int[] height) {
        int highest = 0;
        for (int h : height) {
            highest = Math.max(h, highest);
        }
        int result = 0;
        for (int i = highest; i >= 0; i--) {
            // confirm left and right border
            int left = 0, right = height.length - 1;
            for (int j = 0; j < height.length; j++) {
                if (height[j] >= i) {
                    left = j;
                    break;
                }
            }
            for (int j = height.length - 1; j >= 0; j--) {
                if (height[j] >= i) {
                    right = j;
                    break;
                }
            }
            // no border
            if (left >= right) {
                continue;
            }
            // start from border
            for (int j = left; j < right; j++) {
                if (height[j] < i) {
                    result++;
                }
            }
        }

        return result;
    }
}

class Solution {
    public int trap(int[] height) {
        // Monotonic non-increasing stack
        Deque<Integer> s = new ArrayDeque<>();
        int i = 0, n = height.length, result = 0;
        while (i < n) {
            if (s.isEmpty() || height[i] <= height[s.peek()]) {
                // build left border
                s.push(i++);
            } else {
                int t = s.pop();
                // no left border
                if (s.isEmpty()) {
                    continue;
                }
                // height[t] is the bottom
                // Math.min(height[i], height[s.peek()]) is the border
                // i - s.peek() - 1 is the width
                result += (Math.min(height[i], height[s.peek()]) - height[t]) * (i - s.peek() - 1);
                // current bar will be pushed in next few steps
            }
        }

        return result;
    }
}