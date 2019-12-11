// 84. Largest Rectangle in Histogram
class Solution {
    // Brute force 有几种，从左往右，从右往左，从中间往两边，时间复杂度为 O(n^2)，可以 AC
    public int largestRectangleArea(int[] heights) {
        int maxArea = 0;
        for(int i = 0; i < heights.length; i++) {
            int minHeight = heights[i];
            // 从左往右遍历
            for(int j = i; j < heights.length; j++) {
                minHeight = Math.min(minHeight, heights[j]);
                maxArea = Math.max(maxArea, minHeight * (j - i + 1));
            }
        }
        return maxArea;
    }
}

class Solution {
    // Brute force，从右往左
    public int largestRectangleArea(int[] heights) {
        int maxArea = 0;
        for (int i = 0; i < heights.length; i++) {
            // 剪枝
            if (i < heights.length - 1 && heights[i] <= heights[i + 1]) {
                continue;
            }
            int minHeight = heights[i];
            // 从右往左遍历
            for (int j = i; j >= 0; j--) {
                minHeight = Math.min(minHeight, heights[j]);
                maxArea = Math.max(maxArea, minHeight * (i - j + 1));
            }
        }
        return maxArea;
    }
}

class Solution {
    // divide and conquer，时间复杂度为 O(nlogn)
    public int largestRectangleArea(int[] heights) {
        return helper(heights, 0, heights.length - 1);
    }

    private int helper(int[] heights, int start, int end) {
        if(start > end) {
            return 0;
        }
        
        int minIndex = start;
        for(int i = start; i <= end; i++) {
            if(heights[minIndex] > heights[i]) {
                minIndex = i;
            }
        }
        
        return Math.max(heights[minIndex] * (end - start + 1),
                Math.max(helper(heights, start, minIndex - 1),
                helper(heights, minIndex + 1, end)));
    }
}

class Solution {
    // ***单调增栈版本，时间复杂度为 O(n)
    // 一个很明显的能用 O(n) 解法求直方图面积的特例就是单调增直方图，于是将此作为出发点
    // 单调增直方图每个矩形面积都能用 高 * 其到最后矩形的距离（宽）来计算
    // 递增直方图中的每个矩形对其之后的矩形面积都有影响，但是如果多了一个递减的短矩形在中间，其面积计算只能在这个短矩形之前，被隔断了
    // 对于这个短矩形后面的矩形就没用了，所以直接出栈
    public int largestRectangleArea(int[] heights) {
        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(-1); // 给 heights 尾部添加 0 也行，但这个操作 Java 没 C++ 方便
        int maxArea = 0;
        for (int i = 0; i < heights.length; i++) {
            // 维护一个递增栈
            if (stack.peek() == -1 || heights[i] > heights[stack.peek()]) {
                stack.push(i);
            } else {
                // height 是栈顶元素，但是 width 是根据第二个栈顶元素的位置，因此需要栈中最底有一个 -1 保证最左边的矩形进行运算
                maxArea = Math.max(maxArea, heights[stack.pop()] * (i - stack.peek() - 1));
                i--; // 外层 for 循环会 i++; 保持指针位置不变
            }
        }
        while (stack.peek() != -1) {
            maxArea = Math.max(maxArea, heights[stack.pop()] * (heights.length - stack.peek() - 1));
        }

        return maxArea;
    }
}

class Solution {
    // 另一种简洁的写法
    public int largestRectangleArea(int[] heights) {
        if (heights == null || heights.length == 0) {
            return 0;
        }

        Deque<Integer> stack = new ArrayDeque<>();
        int maxArea = 0;
        for (int i = 0; i <= heights.length; i++) {
            int cur = (i == heights.length) ? -1 : heights[i];
            while (!stack.isEmpty() && cur <= heights[stack.peek()]) {
                int h = heights[stack.pop()];
                int w = stack.isEmpty() ? i : i - stack.peek() - 1;
                maxArea = Math.max(maxArea, h * w);
            }
            stack.push(i);
        }

        return maxArea;
    }
}