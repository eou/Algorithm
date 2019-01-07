// 378. Kth Smallest Element in a Sorted Matrix
// 两个条件：每一行是升序；每一列是升序
class Solution {
    // 时间复杂度为 O(m * n * logk)
    public int kthSmallest(int[][] matrix, int k) {
        PriorityQueue<Integer> pq = new PriorityQueue<>(new Comparator<Integer>(){
            public int compare(Integer a, Integer b) {
                return b - a;
            }
        });
        for(int i = 0; i < matrix.length; i++) {
            for(int j = 0; j < matrix[0].length; j++) {
                pq.add(matrix[i][j]);
                if(pq.size() > k) {
                    pq.poll();
                }
            }
        }
        
        return pq.peek();
    }
}

class Solution {
    // 上面算法的改进版本，时间复杂度为 O(klog(m * n))
    public int kthSmallest(int[][] matrix, int k) {
        PriorityQueue<int[]> pq = new PriorityQueue<>(k, (a, b) -> a[2] - b[2]);
        // 把第 1 行的元素放入堆中
        for (int i = 0; i < Math.min(k, matrix[0].length); i++) {
            // 行数，列数，元素
            pq.offer(new int[] { 0, i, matrix[0][i] });
        }

        // 每次丢弃最小元素，将其下一行同列元素放入堆中
        for (int i = 0; i < k - 1; i++) {
            int[] cur = pq.poll();
            if (cur[0] + 1 < matrix.length) {
                pq.offer(new int[] { cur[0] + 1, cur[1], matrix[cur[0] + 1][cur[1]] });
            }
        }

        return pq.poll()[2];
    }
}

class Solution {
    // 二分查找，时间复杂度为 O(n^2* log(max - min))
    public int kthSmallest(int[][] matrix, int k) {
        int left = matrix[0][0];
        int right = matrix[matrix.length - 1][matrix[0].length - 1] + 1;
        while(left < right) {
            int mid = left + (right - left) / 2;
            // 每一行都查找一次 mid，这是 O（n^2）的复杂度
            int count = 0;
            for(int i = 0; i < matrix.length ; i++) {
                int j = matrix[0].length - 1;
                for(; j >= 0 && matrix[i][j] > mid; j--); // 找到包含 mid 元素所在范围的那一行
                count += (j + 1); // 算出 mid 在矩阵是第几小的元素
            }
            if(count < k) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }

        return left;
    }
}

class Solution {
    // 二分查找，时间复杂度为 O(nlogn* log(max - min))
    // 由于 max - min 最大不过是 2^31 - 1 - (-2^31) => log(max - min) 最大为32，可以视为常数，所以总体时间复杂度为 O(nlogn)
    public int kthSmallest(int[][] matrix, int k) {
        int left = matrix[0][0];
        int right = matrix[matrix.length - 1][matrix[0].length - 1] + 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            // 每一行都查找一次 mid
            int count = countLessEqual(matrix, mid);
            if (count < k) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }

        return left;
    }

    // O(logn)
    private int countLessEqual(int[][] matrix, int value) {
        int cnt = 0;
        for (int[] row : matrix) {
            // binary search, find the first element that larger than value
            int start = 0, end = row.length - 1;
            while (start + 1 < end) {
                int mid = start + (end - start) / 2;
                if (row[mid] > value) {
                    end = mid;
                } else {
                    start = mid;
                }
            }

            if (row[start] > value) {
                cnt += start;
            } else if (row[end] > value) {
                cnt += end;
            } else {
                cnt += row.length;
            }
        }

        return cnt;
    }
}

class Solution {
    // 上面版本的改进版本，时间复杂度为 O(n* log(max - min))
    // 由于 max - min 最大不过是 2^31 - 1 - (-2^31) => log(max - min) 最大为32，可以视为常数，所以总体时间复杂度为 O(n)
    public int kthSmallest(int[][] matrix, int k) {
        int left = matrix[0][0];
        int right = matrix[matrix.length - 1][matrix[0].length - 1] + 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            // 每一行都查找一次 mid
            int count = countLessEqual(matrix, mid);
            if (count < k) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }

        return left;
    }

    // 从右上角为起点，平均时间复杂度为 O(1)
    private int countLessEqual(int[][] matrix, int value) {
        int cnt = 0;
        int i = 0, j = matrix.length - 1;
        while (i < matrix.length && j >= 0) {
            if (matrix[i][j] <= value) {
                cnt += j + 1;
                i++;
            } else {
                j--;
            }
        }

        return cnt;
    }
}
