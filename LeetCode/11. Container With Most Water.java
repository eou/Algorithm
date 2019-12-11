// 11. Container With Most Water
class Solution {
    // 暴力解法，时间复杂度为 O(n^2)
    public int maxArea(int[] height) {
        int max = 0;
        for(int i = 0; i < height.length; i++) {
            for(int j = i + 1; j < height.length; j++) {
                max = Math.max(max, (j - i) * Math.min(height[i], height[j]));
            }
        }
        
        return max;
    }
}

class Solution {
    // 时间复杂度为 O(n)
    // start from the longest width, if we want get bigger container, we should shorten the width and find the next possible longer height
    public int maxArea(int[] height) {
        int left = 0;
        int right = height.length - 1;
        int max = 0;
        while(left < right) {
            max = Math.max(max, (right - left) * Math.min(height[left], height[right]));
            if(height[left] < height[right]) {
                left++;
            } else {
                right--;
            }
        }
        
        return max;
    }
}