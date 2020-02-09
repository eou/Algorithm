// 973. K Closest Points to Origin
// LintCode 612. K Closest Points
// O(NlogK)
class Solution {
    public int[][] kClosest(int[][] points, int k) {
        PriorityQueue<int[]> pq = new PriorityQueue<>(new Comparator<int[]>() {
            public int compare(int[] a, int[] b) {
                return (int)((Math.pow(a[0], 2) + Math.pow(a[1], 2)) - (Math.pow(b[0], 2) + Math.pow(b[1], 2)));    // use Math.sqrt will cause a bug
            }
        });
        
        for (int i = 0; i < points.length; i++) {
            pq.add(points[i]);
        }
        
        int[][] res = new int[k][2];
        for (int i = 0; i < k; i++) {
            res[i] = pq.poll();
        }
        
        return res;
    }
}

// O(NlogN)
class Solution {
    public int[][] kClosest(int[][] points, int K) {
        Arrays.sort(points, (p1, p2) -> p1[0] * p1[0] + p1[1] * p1[1] - p2[0] * p2[0] - p2[1] * p2[1]);
        return Arrays.copyOfRange(points, 0, K);
    }
}

// quick-sort, O(N^2)
class Solution {
    public int[][] kClosest(int[][] points, int K) {
        int len =  points.length, l = 0, r = len - 1;
        while (l <= r) {
            int mid = helper(points, l, r);
            if (mid == K) break;
            if (mid < K) {
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        return Arrays.copyOfRange(points, 0, K);
    }
    
    private int helper(int[][] A, int l, int r) {
        int[] pivot = A[l];
        while (l < r) {
            while (l < r && compare(A[r], pivot) >= 0) r--;
            A[l] = A[r];
            while (l < r && compare(A[l], pivot) <= 0) l++;
            A[r] = A[l];
        }
        A[l] = pivot;
        return l;
    }
    
    private int compare(int[] p1, int[] p2) {
        return p1[0] * p1[0] + p1[1] * p1[1] - p2[0] * p2[0] - p2[1] * p2[1];
    }
}

// quick-select, TLE, O(k * N) ~ O(k * N^2)
// https://stackoverflow.com/questions/5945193/average-runtime-of-quickselect/25796762#25796762
class Solution {
    public int[][] kClosest(int[][] points, int k) {
        int[][] result = new int[k][2];
        for (int i = 0; i < k; i++) {
            int index = quickSelect(points, 0, points.length - 1, i);
            result[i] = points[index];
        }

        return result;
    }

    private int quickSelect(int[][] points, int start, int end, int k) {
        if (start >= end) {
            return k;
        }

        int left = start;
        int right = end;
        int mid = left + (right - left) / 2;

        while (left <= right) {
            // points[left] < points[mid]
            while (left <= right && comparePoint(points[left], points[mid]) == 1) {
                left++;
            }

            // points[right] > points[mid]
            while (left <= right && comparePoint(points[right], points[mid]) == -1) {
                right--;
            }
            if (left <= right) {
                swap(points, left, right);
                left++;
                right--;
            }
        }

        if (k <= right) {
            return quickSelect(points, start, right, k);
        }
        if (k >= left) {
            return quickSelect(points, left, end, k);
        }
        return k;
    }

    private void swap(int[][] points, int i, int j) {
        int[] tmp = points[i];
        points[i] = points[j];
        points[j] = tmp;
    }

    private int comparePoint(int[] a, int[] b) {
        int d1 = getDistance(a, new int[] { 0, 0 });
        int d2 = getDistance(b, new int[] { 0, 0 });
        if (d1 == d2) {
            if (a[0] == b[0]) {
                if (a[1] == b[1]) {
                    return 0;
                }
                return b[1] - a[1] > 0 ? 1 : -1;
            } else {
                return b[0] - a[0] > 0 ? 1 : -1;
            }
        } else {
            return d2 - d1 > 0 ? 1 : -1;
        }
    }

    private int getDistance(int[] a, int[] b) {
        return (a[0] - b[0]) * (a[0] - b[0]) + (a[1] - b[1]) * (a[1] - b[1]);
    }
}
