import java.util.*;

/**
 * 给⼀个 2d array。其中 array[j] = (i+1)*(j+1)
 * 然后给⼀堆 query，有三种不同的 query: 
 * 第⼀种是返回当前 array 中的最⼩值 
 * 第⼆种是把某⼀⾏ disable 
 * 第三种是把某⼀列 disable
 */
class Solution {
    public List<Integer> findMin1(int m, int n, int[][] queries) {
        List<Integer> res = new ArrayList<>();
        PriorityQueue<Integer> pq = new PriorityQueue<>(new Comparator<Integer>() {
            public int compare(Integer a, Integer b) {
                int x = (a / m + 1) * (a % m + 1);
                int y = (b / m + 1) * (b % m + 1);
                // compare element value, not their index
                return x - y;
            }
        });2
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                pq.add((i * m) + j); // add index
            }
        }

        Set<Integer> removeRow = new HashSet<>();
        Set<Integer> removeCol = new HashSet<>();
        for (int[] query : queries) {
            if (query.length == 1) {
                // return minimum element
                int elt = pq.peek();
                while (removeRow.contains(elt / m) || removeCol.contains(elt % m)) {
                    pq.poll();
                    elt = pq.peek();
                }
                res.add((elt / m + 1) * (elt % m + 1));
            } else {
                if (query[0] == 1) {
                    removeRow.add(query[1]);
                } else {
                    removeCol.add(query[1]);
                }
            }
        }

        return res;
    }

    public List<Integer> findMin2(int m, int n, int[][] queries) {
        // ordered map
        TreeMap<Integer, Integer> map = new TreeMap<>();
        int[][] matrix = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = (i + 1) * (j + 1);
                map.put(matrix[i][j], map.getOrDefault(matrix[i][j], 0) + 1);
            }
        }

        List<Integer> res = new ArrayList<>();
        for (int[] query : queries) {
            if (query.length == 1) {
                for (int key : map.keySet()) {
                    if (map.get(key) != 0) {
                        res.add(key);
                        break;
                    }
                }
            } else if (query.length == 2) { 
                if(query[0] == 1) { 
                    // consider the row
                    int row = query[1]; 
                    for(int j = 0; j< n; j++) { 
                        if(matrix[row][j] != -1 && map.containsKey(matrix[row][j])) {
                            map.put(matrix[row][j], map.get(matrix[row][j]) - 1); 
                            matrix[row][j] = -1; 
                        } 
                    } 
                } else {
                    // consider the column
                    int col = query[1]; 
                    for(int i = 0; i < m; i++) {
                        if(matrix[i][col] != -1 && map.containsKey(matrix[i][col])) {
                            map.put(matrix[i][col], map.get(matrix[i][col]) - 1); 
                            matrix[i][col] = -1; 
                        } 
                    }
                }
            }
        }
        return res;
    }

    public static void print2D(int m, int n) {
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print((i + 1) * (j + 1) + ", ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        int[][] queries = new int[][]{{0}, {1, 0}, {0}, {2, 1}, {0}, {2, 0}, {0}};
        print2D(3, 4);
        for (Integer i : s.findMin1(3, 4, queries)) {
            System.out.println(i);
        }
    }
}