/**
 * Box Stacking Problem
 * https://www.geeksforgeeks.org/box-stacking-problem-dp-22/
 * 三维的 300. Longest Increasing Subsequence
 */
import java.io.*;
import java.util.*;

class Solution {
    class Box {
        int w;
        int d;
        int h;
        int area;
        public Box(int width, int depth, int height) {
            w = width;
            d = depth;
            h = height;
            area = width * depth;
        }
    }

    public int boxStack(int[][] boxes) {
        Box[] rotation = new Box[boxes.length * 3];

        // 如不能翻转盒子，就直接按底面积排序，不用考虑三种情况
        // considering all 3 possible rotations with width always greater than equal to depth
        for (int i = 0; i < boxes.length; i++) {
            int w = boxes[i][0];
            int d = boxes[i][1];
            int h = boxes[i][2];

            // width, depth, height
            rotation[3 * i] = new Box(Math.max(h, d), Math.min(h, d), w);
            rotation[3 * i + 1] = new Box(Math.max(w, h), Math.min(w, h), d);
            rotation[3 * i + 2] = new Box(Math.max(w, d), Math.min(w, d), h);
        }

        // descending order by base areas
        Arrays.sort(rotation, new Comparator<Box>(){
            @Override
            public int compare(Box b1, Box b2) {
                return b2.area - b1.area;
            }
        });

        int[] maxBox = new int[rotation.length];
        int max = 1;    
        for(int i = 0; i < rotation.length; i++) {
            maxBox[i] = 1;
            for(int j = 0; j < i; j++) {
                if(rotation[j].w > rotation[i].w && rotation[j].d > rotation[i].d) {
                    maxBox[i] = Math.max(maxBox[i], maxBox[j] + 1);
                }
            }
            max = Math.max(max, maxBox[i]);
        }

        // 也可以算盒子堆叠的最大高度
        int[] maxHeight = new int[rotation.length];
        int maxH = 0;
        for(int i = 0; i < maxHeight.length; i++) {
            maxHeight[i] = rotation[i].h;
        }
        for (int i = 0; i < rotation.length; i++) {
            maxHeight[i] = rotation[i].h;
            for (int j = 0; j < i; j++) {
                if (rotation[j].w > rotation[i].w && rotation[j].d > rotation[i].d) {
                    maxHeight[i] = Math.max(maxHeight[i], maxHeight[j] + rotation[i].h);
                }
            }
            maxH = Math.max(maxH, maxHeight[i]);
        }

        return max;
    }

    public static void main(String[] args) {
        int[][] boxes = {{2, 2, 3}, {2, 1, 4}, {3, 5, 1}, {1, 1, 1}};
        System.out.println(new Solution().boxStack(boxes));
    }
}