// 302. Smallest Rectangle Enclosing Black Pixels
// 600. Smallest Rectangle Enclosing Black Pixels
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
            if (containsBlackPixels(image, mid, false)) {
                end = mid;
            } else {
                start = mid;
            }
        }
        if (containsBlackPixels(image, start, false)) {
            left = Math.min(left, start);
        } else {
            left = Math.min(left, end);
        }

        // the right most, O(mlogn)
        start = y;
        end = image[0].length - 1;
        while (start + 1 < end) {
            int mid = start + (end - start) / 2;
            // check if column mid has black pixels
            if (containsBlackPixels(image, mid, false)) {
                start = mid;
            } else {
                end = mid;
            }
        }
        if (containsBlackPixels(image, end, false)) {
            right = Math.max(right, end);
        } else {
            right = Math.max(right, start);
        }

        // the top most, O(nlogm)
        start = 0;
        end = x;
        while (start + 1 < end) {
            int mid = start + (end - start) / 2;
            // check if row mid has black pixels
            if (containsBlackPixels(image, mid, true)) {
                end = mid;
            } else {
                start = mid;
            }
        }
        if (containsBlackPixels(image, start, true)) {
            top = Math.min(top, start);
        } else {
            top = Math.min(top, end);
        }

        // the bottom most, O(nlogm)
        start = x;
        end = image.length - 1;
        while (start + 1 < end) {
            int mid = start + (end - start) / 2;
            // check if row mid has black pixels
            if (containsBlackPixels(image, mid, true)) {
                start = mid;
            } else {
                end = mid;
            }
        }
        if (containsBlackPixels(image, end, true)) {
            bottom = Math.max(bottom, end);
        } else {
            bottom = Math.max(bottom, start);
        }

        return (bottom - top + 1) * (right - left + 1);
    }

    private boolean containsBlackPixels(char[][] image, int index, boolean checkRow) {
        if (checkRow) {
            for (int i = 0; i < image[0].length; i++) {
                if (image[index][i] == '1') {
                    return true;
                }
            }
        } else {
            for (int i = 0; i < image.length; i++) {
                if (image[i][index] == '1') {
                    return true;
                }
            }
        }

        return false;
    }
}

// DFS or BFS still O(n^2)