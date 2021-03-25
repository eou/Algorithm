// 302. Smallest Rectangle Enclosing Black Pixels
// Lintcode 600. Smallest Rectangle Enclosing Black Pixels
// brute-force, O(n^2)
class Solution {
    public int minArea(char[][] image, int x, int y) {
        int top = image.length - 1, bottom = 0, left = image[0].length - 1, right = 0;

        for (int i = 0; i < image.length; i++) {
            for (int j = 0; j < image[i].length; j++) {
                if (image[i][j] == '1') {
                    top = Math.min(top, i);
                    bottom = Math.max(bottom, i);
                    left = Math.min(left, j);
                    right = Math.max(right, j);
                }
            }
        }

        return (bottom - top + 1) * (right - left + 1);
    }
}

// Why do we have (x, y) input?
// Since every pixel is connected, we can use binary search to find the area based on the input pixel
class Solution {
    public int minArea(char[][] image, int x, int y) {
        int top = image.length - 1, bottom = 0, left = image[0].length - 1, right = 0;

        // the left most, O(mlogn)
        int start = 0, end = y;
        while (start + 1 < end) {
            int mid = start + (end - start) / 2;
            // check if column mid has black pixels
            if (columnContainsBlackPixels(image, mid)) {
                end = mid;
            } else {
                start = mid;
            }
        }
        if (columnContainsBlackPixels(image, start)) {
            left = start;
        } else {
            left = end;
        }

        // the right most, O(mlogn)
        start = y;
        end = image[0].length - 1;
        while (start + 1 < end) {
            int mid = start + (end - start) / 2;
            // check if column mid has black pixels
            if (columnContainsBlackPixels(image, mid)) {
                start = mid;
            } else {
                end = mid;
            }
        }
        if (columnContainsBlackPixels(image, end)) {
            right = end;
        } else {
            right = start;
        }

        // the top most, O(nlogm)
        start = 0;
        end = x;
        while (start + 1 < end) {
            int mid = start + (end - start) / 2;
            // check if row mid has black pixels
            if (rowContainsBlackPixels(image, mid)) {
                end = mid;
            } else {
                start = mid;
            }
        }
        if (rowContainsBlackPixels(image, start)) {
            top = start;
        } else {
            top = end;
        }

        // the bottom most, O(nlogm)
        start = x;
        end = image.length - 1;
        while (start + 1 < end) {
            int mid = start + (end - start) / 2;
            // check if row mid has black pixels
            if (rowContainsBlackPixels(image, mid)) {
                start = mid;
            } else {
                end = mid;
            }
        }
        if (rowContainsBlackPixels(image, end)) {
            bottom = end;
        } else {
            bottom = start;
        }

        return (bottom - top + 1) * (right - left + 1);
    }

    private boolean columnContainsBlackPixels(char[][] image, int index) {
        for (int i = 0; i < image.length; i++) {
            if (image[i][index] == '1') {
                return true;
            }
        }

        return false;
    }

    private boolean rowContainsBlackPixels(char[][] image, int index) {
        for (int i = 0; i < image[0].length; i++) {
            if (image[index][i] == '1') {
                return true;
            }
        }

        return false;
    }
}

// DFS or BFS still O(n^2)