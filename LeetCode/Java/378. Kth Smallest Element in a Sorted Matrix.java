// 378. Kth Smallest Element in a Sorted Matrix
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
    public int kthSmallest(int[][] matrix, int k) {
        PriorityQueue<int[]> pq = new PriorityQueue<>(k, (a, b) -> a[2] - b[2]);
        for (int j = 0; j < Math.min(k, matrix[0].length); j++) {
            pq.offer(new int[] { 0, j, matrix[0][j] });
        }

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
    // 二分查找，时间复杂度为 O(nlog(max - min))
    public int kthSmallest(int[][] matrix, int k) {
        int left = matrix[0][0];
        int right = matrix[matrix.length - 1][matrix[0].length - 1] + 1;
        while(left < right) {
            int mid = left + (right - left) / 2;
            int count = 0;
            for(int i = 0; i < matrix.length ; i++) {
                int j = matrix[0].length - 1;
                for(; j >= 0 && matrix[i][j] > mid; j--);
                count += (j + 1);
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