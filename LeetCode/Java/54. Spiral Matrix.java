// 54. Spiral Matrix
class Solution {
    public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> res = new ArrayList<>();
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return res;
        }
        
        int rStart = 0;
        int rEnd = matrix.length - 1;
        int cStart = 0;
        int cEnd = matrix[0].length - 1;
        // spiral traverse start from matrix[0][0]
        while (rStart <= rEnd && cStart <= cEnd) {
            // Traverse right cStart => cEnd
            for (int i = cStart; i <= cEnd; i++) {
                res.add(matrix[rStart][i]);
            }
            rStart++;
            
            // Traverse Down rStart => rEnd
            for (int i = rStart; i <= rEnd; i++) {
                res.add(matrix[i][cEnd]);
            }
            cEnd--;
            
            if (rStart <= rEnd) {
                // Traverse Left
                for (int i = cEnd; i >= cStart; i--) {
                    res.add(matrix[rEnd][i]);
                }
            }
            rEnd--;
            
            if (cStart <= cEnd) {
                // Traverse Up
                for (int i = rEnd; i >= rStart; i--) {
                    res.add(matrix[i][cStart]);
                }
            }
            cStart++;
        }
        
        return res;
    }
}