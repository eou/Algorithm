// 378. Kth Smallest Element in a Sorted Matrix
// 两个条件：每一行是升序；每一列是升序
class Solution {
    // 两个有序的条件都没用的版本，时间复杂度为 O(m * n * logk)
    public int kthSmallest(int[][] matrix, int k) {
        // PriorityQueue<Integer> pq = new PriorityQueue<>(new Comparator<Integer>(){
        //     public int compare(Integer a, Integer b) {
        //         return b - a;
        //     }
        // });
        PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> b - a);   // max-heap
        for(int i = 0; i < matrix.length; i++) {
            for(int j = 0; j < matrix[0].length; j++) {
                pq.add(matrix[i][j]);
                if(pq.size() > k) {
                    pq.poll();  // drop bigger number, only keep k smaller number
                }
            }
        }
        
        return pq.peek();
    }
}

class Solution {
    // 与 373. Find K Pairs with Smallest Sums 类似
    // 上面算法的改进版本，时间复杂度为 O(klog(m * n))
    public int kthSmallest(int[][] matrix, int k) {
        PriorityQueue<int[]> pq = new PriorityQueue<>(k, (a, b) -> a[2] - b[2]);
        // 把第 1 行的元素放入堆中
        for (int i = 0; i < Math.min(k, matrix[0].length); i++) {
            pq.offer(new int[] { 0, i, matrix[0][i] }); // 行数，列数，元素
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
    // 也可以先把第 1 列放入堆中
    public int kthSmallest(int[][] matrix, int k) {
        // [row, col, element]
        PriorityQueue<int[]> minHeap = new PriorityQueue<>((a, b) -> (a[2] - b[2]));
        for (int i = 0; i < matrix.length; i++) {
            minHeap.add(new int[] { i, 0, matrix[i][0] });
        }
        k--;
        while (k > 0) {
            int[] cur = minHeap.poll();
            k--;
            if (cur[1] == matrix[0].length - 1) {
                continue;
            }
            minHeap.add(new int[] { cur[0], cur[1] + 1, matrix[cur[0]][cur[1] + 1] });
        }

        return minHeap.poll()[2];
    }
}

class Solution {
    // 另一个利用堆的版本，大同小异，也是将第 1 行的元素放入堆中，时间复杂度为 O(klog(m * n))
    public int kthSmallest(final int[][] matrix, int k) {
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> matrix[a[0]][a[1]] - matrix[b[0]][b[1]]);
        pq.offer(new int[] { 0, 0 });
        k--;
        while (k > 0) {
            int[] cur = pq.poll();
            k--;
            // 只有在第 1 行才放入同行不同列的元素
            if (cur[0] == 0 && cur[1] + 1 < matrix[0].length) {
                pq.offer(new int[] { 0, cur[1] + 1 });
            }
            if (cur[0] + 1 < matrix.length) {
                pq.offer(new int[] { cur[0] + 1, cur[1] });
            }
        }

        return matrix[pq.peek()[0]][pq.peek()[1]];
    }
}

class Solution {
    // 二分查找，时间复杂度为 O(n^2* log(max - min))
    public int kthSmallest(int[][] matrix, int k) {
        int left = matrix[0][0];
        int right = matrix[matrix.length - 1][matrix[0].length - 1] + 1;
        while(left < right) {
            int mid = left + (right - left) / 2;
            // 每一行都查找一次 mid，这是 O（n^2）的复杂度，可以改进
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
        int right = matrix[matrix.length - 1][matrix[0].length - 1];
        while (left < right) {
            int mid = left + (right - left) / 2;
            int cnt = lessEqualMid(matrix, mid);
            if (cnt < k) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }

        return left;
    }
    
    // O(logn)
    private int lessEqualMid(int[][] matrix, int val) {
        int cnt = 0;
        // 每行都二分查找一次 val
        for (int i = 0; i < matrix.length; i++) {
            int left = 0;
            int right = matrix[0].length - 1;
            while (left + 1 < right) { // 这里不适用 left < right
                int mid = left + (right - left) / 2;
                if (matrix[i][mid] <= val) {
                    left = mid;
                } else {
                    right = mid;
                }
            }
            if (matrix[i][left] > val) {
                cnt += left;
            } else if (matrix[i][right] > val) {
                cnt += right;
            } else {
                cnt += matrix[i].length;
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
        int right = matrix[matrix.length - 1][matrix[0].length - 1] + 1; // + 1 与否不影响 AC！！！
        while (left < right) {
            int mid = left + (right - left) / 2;
            int count = lessEqualMid(matrix, mid);
            if (count < k) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }

        return left;
    }

    // 从右上角为起点，一些行可以直接跳过，平均时间复杂度为 O(1)
    private int lessEqualMid(int[][] matrix, int val) {
        int cnt = 0;
        int i = 0, j = matrix.length - 1;
        while (i < matrix.length && j >= 0) {
            if (matrix[i][j] <= val) { // 如果端点就已经 <= val 这一行就直接加上不用扫描
                cnt += j + 1;
                i++;
            } else {
                j--;
            }
        }

        return cnt;
    }
}

// binary search double optimization
class Solution {
    public int kthSmallest(int[][] matrix, int k) {
        int left = matrix[0][0];
        int right = matrix[matrix.length - 1][matrix[0].length - 1] + 1;    // + 1 与否不影响 AC！！！
        while (left < right) {
            int mid = left + (right - left) / 2;
            int cnt = count(matrix, mid);
            if (cnt < k) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        
        return left;
    }
    
    public int count(int[][] matrix, int num) {
        int cnt = 0;
        for (int i = 0; i < matrix.length; i++) {
            // start from end of each row
            if (matrix[i][matrix[0].length - 1] <= num) {
                // skip this row, go to next row
                // note that if == num, should also skip
                cnt += (matrix[0].length);
                continue;
            } if (matrix[i][0] > num) {
                // no need to check rest rows
                break;
            } else {
                int left = 0;
                int right = matrix[0].length;
                while (left < right) {
                    int mid = left + (right - left) / 2;
                    if (matrix[i][mid] <= num) {    // reach first num > matrix[i][mid]
                        left = mid + 1;
                    } else {
                        right = mid;
                    }
                }
                cnt += (left);
            }
        }
        return cnt;
    }
}